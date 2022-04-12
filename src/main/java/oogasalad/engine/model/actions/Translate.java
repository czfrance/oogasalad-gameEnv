package oogasalad.engine.model.actions;

import oogasalad.engine.model.board.OutOfBoardException;
import oogasalad.engine.model.board.Board;

public class Translate extends Action {

  /**
   *
   * @param parameters should be size 4 array where
   * [i1, j1, i2, j2]
   */
  public Translate(int[] parameters) {
    super(parameters);
  }

  @Override
  public Board execute(Board board, int refI, int refJ)
      throws OutOfBoardException {
    return board.move(myParameters[0]+refI, myParameters[1]+refJ,
        myParameters[2]+refI, myParameters[3]+refJ);
  }

}