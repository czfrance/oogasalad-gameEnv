package oogasalad.engine.model.logicelement.conditions.position_independent_conditions;

import oogasalad.engine.model.board.Board;
import oogasalad.engine.model.board.cells.Position;

/**
 *
 * @author Jake Heller
 */
public class IsPlayer extends BoardCondition {

  private int player;
  /**
   *
   * @param parameters size 1 array where parameters[0] is the player's number
   */
  public IsPlayer(int[] parameters) {
    super(parameters);
    player = myParameters[0];
  }

  @Override
  public boolean isTrue(Board board, Position referencePoint) {
    return board.getPlayer() == player;
  }
}
