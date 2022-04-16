package oogasalad.engine.controller;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import oogasalad.engine.model.board.OutOfBoardException;
import oogasalad.engine.model.board.Position;
import oogasalad.engine.model.conditions.terminal_conditions.WinCondition;
import oogasalad.engine.model.driver.Game;
import oogasalad.engine.model.engine.Engine;
import oogasalad.engine.model.board.Board;
import oogasalad.engine.model.engine.PieceSelectionEngine;

import oogasalad.engine.model.move.Move;
import oogasalad.engine.model.setup.Constants;
import org.jooq.lambda.function.Consumer0;
import oogasalad.engine.model.parser.GameParser;

public class Controller {

  private Board myBoard;
  private Engine myEngine;
  private Game myGame;
  private Collection<Move> moves;
  private Collection<WinCondition> winConditions;
  private Consumer<Board> updateView;
  private Consumer<Set<Position>> setViewValidMarks;
  private Consumer0 clearViewMarkers;


  public Controller(Board board) {
    try {
      // TODO: Replace this with some way to pick the configuration directory
      GameParser parser = new GameParser(new File("data/checkers/config.json"));
      myBoard = board;
      myGame = new Game(myBoard, null);

      moves = parser.readRules();
      winConditions = parser.readWinConditions();

      // TODO: figure out better way to pass in view lambdas
      myEngine = new PieceSelectionEngine(myGame, moves, winConditions, null, null, null);

    } catch (Exception e){
      e.printStackTrace();
    }
  }

  /**
   * resets the board model to the initial game state
   */
  public Board resetGame() {
    myGame = new Game(myBoard, updateView);

    myEngine = new PieceSelectionEngine(myGame, moves, winConditions, updateView, setViewValidMarks, clearViewMarkers);

    return myBoard;
  }

  public void click(int i, int j ) throws OutOfBoardException {
    myEngine.onCellSelect(i, j);
  }

  public Board setCallbackUpdates(Consumer<Board> update, Consumer<Set<Position>> setValidMarks, Consumer0 clearMarkers){
    updateView = update;
    setViewValidMarks = setValidMarks;
    clearViewMarkers = clearMarkers;

    myGame = new Game(myBoard, updateView);
    myEngine = new PieceSelectionEngine(myGame, moves, winConditions, updateView, setViewValidMarks, clearViewMarkers);

    return myBoard;
  }


  public void saveGame(){

  }
  //TODO: Add functionality to have turns and have the program run.

}
