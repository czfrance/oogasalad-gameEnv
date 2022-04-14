package oogasalad.engine.model.setup.parsing;

import java.io.IOException;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import oogasalad.engine.model.board.misc.OutOfBoardException;
import oogasalad.engine.model.actions.Action;
import oogasalad.engine.model.actions.winner.Winner;
import oogasalad.engine.model.board.boards.Board;
import oogasalad.engine.model.board.components.Position;
import oogasalad.engine.model.conditions.terminal_conditions.WinCondition;
import oogasalad.engine.model.conditions.board_conditions.BoardCondition;
import oogasalad.engine.model.conditions.piece_conditions.PieceCondition;
import oogasalad.engine.model.move.Move;
import oogasalad.engine.model.setup.Constants;
import org.json.JSONArray;
import org.json.JSONObject;

public class GameParser {


  // https://kodejava.org/how-do-i-read-json-file-using-json-java-org-json-library/
  public static Board readInitialBoard(String filePath)
      throws IOException, OutOfBoardException {
    JSONObject root = getRootObject(filePath);

    JSONObject boardState = root.getJSONObject(Constants.BOARD);
    int width = boardState.getInt(Constants.WIDTH);
    int height = boardState.getInt(Constants.HEIGHT);

    Board board = new Board(height, width);

    int[][] pieceConfiguration = new int[height][width];
    int[][] playerConfiguration = new int[height][width];

    JSONArray pieceConfigJSON = boardState.getJSONArray(Constants.PIECE_CONFIGURATION);
    JSONArray playerConfigJSON = boardState.getJSONArray(Constants.PLAYER_CONFIGURATION);

    readJSONArrayToIntArray(pieceConfigJSON, pieceConfiguration);
    readJSONArrayToIntArray(playerConfigJSON, playerConfiguration);

    for (int i = 0; i < pieceConfiguration.length; i++) {
      for (int j = 0; j < pieceConfiguration[0].length; j++) {
        if (pieceConfiguration[i][j] != -1) {
          board = board.placeNewPiece(i,j,pieceConfiguration[i][j], playerConfiguration[i][j]);
        }
      }
    }

    return board;
  }

  public static Move[] readRules(String filePath)
      throws IOException {
    JSONObject root = getRootObject(filePath);

    JSONArray rulesJSON = root.getJSONArray("rules");
    int numRules = rulesJSON.length();
    Move[] moves = new Move[numRules];
    for (int index = 0; index < numRules; index++) {
      JSONObject ruleJSON = rulesJSON.getJSONObject(index);
      Position repPoint = getRepresentativePoint(ruleJSON.getJSONObject("representativePoint"));
      PieceCondition[] conditions = getConditions(ruleJSON.getJSONArray("conditions"));
      Action[] actions = getActions(ruleJSON.getJSONArray("actions"));
      moves[index] = new Move(conditions, actions, repPoint.i(), repPoint.j());
    }

    return moves;
  }
  public static WinCondition[] readWinConditions(String filePath)
      throws IOException {
    JSONObject root = getRootObject(filePath);

    JSONArray winConditionsJSON = root.getJSONArray("winConditions");
    int numConditions = winConditionsJSON.length();
    WinCondition[] winConditions = new WinCondition[numConditions];
    for (int index = 0; index < numConditions; index++) {
      JSONObject winConditionJSON = winConditionsJSON.getJSONObject(index);
      BoardCondition[] conditions = getWinConditions(winConditionJSON.getJSONArray("conditions"));
      Winner winDecision = getwinDecision(winConditionJSON.getJSONArray("winDecision"));
      winConditions[index] = new WinCondition(conditions, winDecision);
    }

    return winConditions;
  }

  private static Position getRepresentativePoint(JSONObject representativePoint) {
    int i = representativePoint.getInt("i");
    int j = representativePoint.getInt("j");
    return new Position(i, j);
  }


  private static PieceCondition[] getConditions(JSONArray conditionsJSON) {

    int numConditions = conditionsJSON.length();
    PieceCondition[] conditions = new PieceCondition[numConditions];
    for (int index = 0; index < numConditions; index++) {
      JSONObject conditionJSON = conditionsJSON.getJSONObject(index);
      String name = conditionJSON.getString("name");
      JSONArray paramsJSON = conditionJSON.getJSONArray("parameters");
      int[] parameters = getParameters(paramsJSON);
      try {
        PieceCondition c = (PieceCondition)getActionOrCondition(name, parameters);
        conditions[index] = c;
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return conditions;
  }
  private static BoardCondition[] getWinConditions(JSONArray conditionsJSON) {

    int numConditions = conditionsJSON.length();
    BoardCondition[] conditions = new BoardCondition[numConditions];
    for (int index = 0; index < numConditions; index++) {
      JSONObject conditionJSON = conditionsJSON.getJSONObject(index);
      String name = conditionJSON.getString("name");
      JSONArray paramsJSON = conditionJSON.getJSONArray("parameters");
      int[] parameters = getParameters(paramsJSON);
      try {
        BoardCondition c = (BoardCondition)getActionOrCondition(name, parameters);
        conditions[index] = c;
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return conditions;
  }

  /**
   * takes in JSONArray and returns int array
   * @param parametersJSON
   * @return
   */
  private static int[] getParameters(JSONArray parametersJSON) {
    int paramsSize = parametersJSON.length();
    int[] parameters = new int[paramsSize];
    for (int j = 0; j < paramsSize; j++) {
      parameters[j] = parametersJSON.getInt(j);
    }
    return parameters;
  }

  private static Object getActionOrCondition(String name, int[] parameters)
      throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
    String className = Constants.CONDITION_RESOURCES.getString(name);
    Class clazz = Class.forName(className);
    Constructor ctor = clazz.getConstructor(int[].class);
    Object obj = ctor.newInstance(parameters);
    return obj;
  }

  private static Object getWinDecisionOrCondition(String name)
      throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
    String className = Constants.CONDITION_RESOURCES.getString(name);
    Class clazz = Class.forName(className);
    Constructor ctor = clazz.getConstructor();
    Object obj = ctor.newInstance();
    return obj;
  }



  /**
   * refactor to merge with getConditions
   * @param actionsJSON
   * @return
   */
  private static Action[] getActions(JSONArray actionsJSON) {
    int numActions = actionsJSON.length();
    Action[] actions = new Action[numActions];
    for (int index = 0; index < numActions; index++) {
      JSONObject conditionJSON = actionsJSON.getJSONObject(index);
      String name = conditionJSON.getString("name");
      JSONArray paramsJSON = conditionJSON.getJSONArray("parameters");
      int[] parameters = getParameters(paramsJSON);
      try {
        Action c = (Action)getActionOrCondition(name, parameters);
        System.out.println(c.getClass());
        actions[index] = c;
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return actions;
  }

  private static Winner getwinDecision(JSONArray winDecisionsJSON) {
    JSONObject conditionJSON = winDecisionsJSON.getJSONObject(0);
    String name = conditionJSON.getString("name");
    Winner decision = null;

    try {
      decision = (Winner)getWinDecisionOrCondition(name);
      System.out.println(decision.getClass());
    } catch (Exception e) {
      e.printStackTrace();
    }

    return decision;
  }

  public static JSONObject getRootObject(String filePath) throws IOException {
    String jsonString = Files.readString(Path.of(filePath));
    JSONObject object = new JSONObject(jsonString);
    return object;
  }

  private static void readJSONArrayToIntArray(JSONArray jsonArray, int[][] array) {
    for (int i = 0; i < array.length; i++) {
      JSONArray row = jsonArray.getJSONArray(i);
      for (int j = 0; j < array[0].length; j++) {
        int element = row.getInt(j);
        System.out.printf("%d ", element);
        array[i][j] = element;
      }
      System.out.printf("\n");
    }
  }

  public static void main(String[] args)
      throws IOException, OutOfBoardException {
    Board board = readInitialBoard(Constants.CHECKERS_FILE);
    Move[] moves = readRules(Constants.CHECKERS_FILE);
  }

  public static Board getCheckersBoard()
      throws OutOfBoardException {
    try {
      return readInitialBoard(Constants.CHECKERS_FILE);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  public static List<Move> getCheckersRules()
      throws IOException {
    return Arrays.asList(readRules(Constants.CHECKERS_FILE));
  }

  public static List<WinCondition> getCheckersWinConditions()
      throws IOException {
    return Arrays.asList(readWinConditions(Constants.CHECKERS_FILE));
  }

}
