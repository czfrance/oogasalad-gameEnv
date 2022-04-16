package oogasalad.engine.model.move;

import oogasalad.engine.model.board.OutOfBoardException;
import oogasalad.engine.model.actions.Action;
import oogasalad.engine.model.board.Board;
import oogasalad.engine.model.board.Position;
import oogasalad.engine.model.conditions.piece_conditions.PieceCondition;
import oogasalad.engine.model.engine.PieceSelectionEngine;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Defines Movements, which contain conditions and actions
 *
 * @author Jake Heller
 */
public class Move {

  private static final Logger LOG = LogManager.getLogger(Move.class);

  private Board myNextState;

  private String myName;
  private PieceCondition[] myConditions;
  private Action[] myActions;
  private int myRepI; // i value for the "representative cell" for this action
  private int myRepJ; // j value for the "representative cell" for this action

  /**
   *
   * @param conditions
   * @param actions
   * @param repI
   * @param repJ
   */
  public Move(String name, PieceCondition[] conditions, Action[] actions, int repI, int repJ) {
    myName = name;
    myConditions = conditions;
    myActions = actions;
    myRepI = repI;
    myRepJ = repJ;
  }

  public boolean isValid(Board board, int refI, int refJ) {
    try {
      for (PieceCondition condition : myConditions) {
        if (!condition.isTrue(board, refI, refJ)) {
          return false;
        }
      }
      return true;
    } catch (OutOfBoardException e) {
      return false;
    }
  }

  /**
   * Returns the name given to this rule
   * @return
   */
  public String getName() {
    return myName;
  }
  /**
   *
   * @param i location of selected piece
   * @param j location of selected piece
   * @return
   */
  public Position getRepresentativeCell(int i, int j) {
    return new Position(myRepI + i, myRepJ + j);
  }

  public Board doMovement(Board board, int refI, int refJ)
      throws OutOfBoardException {
    if (isValid(board, refI, refJ)) {

      LOG.info("{} has {} conditions and {} actions", myName, myConditions.length, myActions.length);
      Board boardCopy = board;

      for (Action action: myActions) {
        boardCopy = action.execute(board, refI, refJ);
      }
      boardCopy = boardCopy.setPlayer((board.getPlayer() + 1) % 2); //Make less magical
      return boardCopy;
    }
    return null;
  }
}
