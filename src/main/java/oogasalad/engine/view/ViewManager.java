package oogasalad.engine.view;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import javafx.scene.Scene;
import javafx.stage.Stage;
import oogasalad.engine.controller.Controller;
import oogasalad.engine.model.board.Board;


import oogasalad.engine.view.game.BoardView;
import oogasalad.engine.view.game.GameView;
import oogasalad.engine.view.setup.AISelectView;
import oogasalad.engine.view.setup.PlayerModeView;
import oogasalad.engine.view.setup.dashboard.Dashboard;
import oogasalad.engine.view.setup.dashboard.GameIcon;

import oogasalad.engine.model.parser.GameParser;
import oogasalad.engine.view.setup.OpeningView;


public class ViewManager {

  public static double WIDTH = 600;
  public static double HEIGHT = 400;
  public static double GAME_SELECTION_WIDTH = 1000;
  public static double GAME_SELECTION_HEIGHT = 600;

  private FileInputStream fis;

  public static double BOARDX;
  public static double BOARDY ;
  public static String CSS_RESOURCE = "/css/";
  public static String DEFAULT_CSS = "light";
  public static String CSS_EXTENSION = ".css";
  public static String DEFAULT_LANGUAGE = "English";


  private OpeningView openingView;
  private Scene currScene;
  private Stage stage;
  private String cssFilepath;
  private File currGame;
  private List<Stage> gameStages = new ArrayList<>();
  private List<Scene> allScenes = new ArrayList<>();
  private String language;


  public ViewManager(Stage s) throws IOException {
    language = DEFAULT_LANGUAGE;
    stage = s;
    cssFilepath = CSS_RESOURCE + DEFAULT_CSS + CSS_EXTENSION;
    fis = new FileInputStream("data/Properties/ViewManagerProperties.properties");
    Properties prop = new Properties();
    prop.load(fis);

    WIDTH = Double.parseDouble(prop.getProperty("WIDTH"));
    HEIGHT = Double.parseDouble(prop.getProperty("HEIGHT"));
    BOARDX = Double.parseDouble(prop.getProperty("BOARDX"));
    BOARDY = Double.parseDouble(prop.getProperty("BOARDY"));
    currScene = createOpeningView().makeScene();
    allScenes.add(currScene);
  }

  public Scene getCurrScene() {
    return currScene;
  }

  public OpeningView createOpeningView() {
    openingView = new OpeningView(WIDTH, HEIGHT, cssFilepath);
    currGame = openingView.getFileChoice();
    openingView.getLanguageSelect().setOnAction(e -> setLanguage(openingView.getLanguageSelect().getLanguage()));
    openingView.getContSel().setOnAction(e -> selectMode(openingView.getFileChoice()));
    openingView.getDashboard().setOnAction(e -> showGames());
    return openingView;
  }

  public GameView createGameView(BoardView board, Controller controller) {
    GameView gameView = new GameView(board, controller, WIDTH, HEIGHT, cssFilepath);
    gameView.getHome().setOnAction(e -> goHome(gameView.getScene()));
    return gameView;
  }

  private void setLanguage(String currLanguage) {
    language = currLanguage;
    System.out.println(language);
  }

  private void showGames() {
    currScene = new Scene(new Dashboard(this::selectMode), GAME_SELECTION_WIDTH, GAME_SELECTION_HEIGHT);
    currScene.getStylesheets().add(getClass().getResource(cssFilepath).toExternalForm());
    updateStage();
  }

  private void selectMode(File game) {
    Stage newStage = new Stage();
    newStage.setTitle(game.getName());
    PlayerModeView pmv = new PlayerModeView(WIDTH, HEIGHT, cssFilepath, game);
    Scene newScene = pmv.makeScene();
    newStage.setScene(newScene);
    newScene.getStylesheets().add(getClass().getResource(cssFilepath).toExternalForm());
    allScenes.add(newScene);
    pmv.getOnePlayer().setOnAction(e -> selectAI(newStage));
    pmv.getTwoPlayer().setOnAction(e -> startGame(game, newStage));
    newStage.show();
  }

  private void selectAI(Stage newStage) {
    AISelectView aiView = new AISelectView(WIDTH, HEIGHT, cssFilepath);
    newStage.setScene(aiView.makeScene());
  }

  private void startGame(File game, Stage newStage) {
    try {
      GameParser parser;
      try {
        parser = new GameParser(
            Objects.requireNonNull(game.listFiles(GameIcon.getConfigFile))[0]);
      }
      catch (NullPointerException e) {
        parser = new GameParser(game);
      }
      Board board = parser.parseBoard();
      BoardView boardView = new BoardView(game, board.getHeight(), board.getWidth(), BOARDX, BOARDY, cssFilepath);
      Controller controller = new Controller(board, parser);
      boardView.addController(controller);
      newStage.setScene(createGameView(boardView, controller).makeScene());
      gameStages.add(newStage);
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void goHome(Scene scene) {
    currScene = createOpeningView().makeScene();
    closeStage(findClosedStage(scene));
    updateStage();
  }

  private void closeStage(Stage stage) {
    gameStages.remove(stage);
    stage.close();
  }

  private Stage findClosedStage(Scene scene) {
    for (Stage stage : gameStages) {
      System.out.println("stage");
      if (stage.getScene().equals(scene)) {
        return stage;
      }
    }
    return new Stage();
  }

  private void updateStage() {
    stage.setScene(currScene);
    stage.show();
  }

  private void updateSceneCSS(String style) {
    cssFilepath = CSS_RESOURCE + style + CSS_EXTENSION;
    currScene.getStylesheets().add(cssFilepath);
    for (Scene s : allScenes) {
      s.getStylesheets().add(cssFilepath);
    }
  }

}
