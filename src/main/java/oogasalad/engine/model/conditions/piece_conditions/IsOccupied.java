package oogasalad.engine.model.conditions.piece_conditions;

import oogasalad.engine.model.board.misc.OutOfBoardException;
import oogasalad.engine.model.board.boards.Board;

public class IsOccupied extends PieceCondition {

  /**
   *
   * @param parameters
   */
  public IsOccupied(int[] parameters) {
    super(parameters);
  }

  @Override
  public boolean isTrue(Board board, int refI, int refJ) throws OutOfBoardException {
    int i = myParameters[0]+refI; //TODO: why are i & j assigned values but never used, is there a mistake here?
    int j = myParameters[1]+refJ;
    if (!board.isValidPosition(i,j)) {
      return false;
    }
    return !board.isEmpty(i, j);
  }
}
