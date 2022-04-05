package oogasalad.engine.model.move;

import oogasalad.engine.model.OutOfBoardException;
import oogasalad.engine.model.action.Action;
import oogasalad.engine.model.board.Board;
import oogasalad.engine.model.board.Position;
import oogasalad.engine.model.conditions.Condition;

/**
 * Defines Movements, which contain conditions and actions
 *
 * @author Jake Heller
 */
public class Rule {

  private Board myNextState;

  private Condition[] myConditions;
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
  public Rule(Condition[] conditions, Action[] actions, int repI, int repJ) {
    myConditions = conditions;
    myActions = actions;
    myRepI = repI;
    myRepJ = repJ;
  }

  public boolean isValid(Board board, int refI, int refJ) {
    try {
      for (Condition condition : myConditions) {
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
      Board boardCopy = board.deepCopy();
      for (Action action: myActions) {
        action.execute(boardCopy, refI, refJ);
      }
      boardCopy.setPlayer((board.getPlayer() + 1) % 2);
      return boardCopy;
    }
    return null;
  }
}