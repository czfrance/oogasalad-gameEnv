package oogasalad.engine.model.conditions.board_conditions;
import oogasalad.engine.model.board.boards.Board;

/**
 * Super Class for a condition that has to check the state of the whole board or multiple pieces on it.
 * @author Robert Cranston
 */
public abstract class BoardCondition {
  protected int[] myParameters;

  protected BoardCondition(int[] parameters){
    myParameters = parameters;
  }

  /**
   * evaluates if the condition is true
   * @param board current board state
   */
  public abstract boolean isTrue(Board board);
}
