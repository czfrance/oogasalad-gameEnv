package oogasalad.engine.model.actions;

import oogasalad.engine.model.board.misc.OutOfBoardException;
import oogasalad.engine.model.board.boards.Board;

/**
 * Every action subclass constructor receives all the parameters
 * it needs to run from a relative position
 *
 * @author Jake Heller
 */
public abstract class Action {

  protected int[] myParameters;
  protected Action(int[] parameters) {
    myParameters = parameters;
  }

  /**
   *
   * @param board
   * @param refI reference i
   * @param refJ reference j
   * @throws OutOfBoardException
   */
  public abstract Board execute(Board board, int refI, int refJ)
      throws OutOfBoardException;
}
