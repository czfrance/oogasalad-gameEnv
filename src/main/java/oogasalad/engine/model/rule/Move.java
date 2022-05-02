package oogasalad.engine.model.rule;

import oogasalad.engine.model.board.Board;
import oogasalad.engine.model.board.cells.Position;

public interface Move extends Rule {
  /**
   *
   * @param board resultant board
   * @param referencePoint
   * @return
   */
  Board doMove(Board board, Position referencePoint);
}
