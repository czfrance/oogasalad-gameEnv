package oogasalad.engine.model.rule;

import oogasalad.engine.model.board.Board;
import oogasalad.engine.model.board.cells.Position;

public class PersistentMove implements Move {

  @Override
  public Board doMove(Board board, Position referencePoint) {
    return null;
  }

  @Override
  public boolean isValid(Board board, Position referencePoint) {
    return false;
  }

  @Override
  public String getName() {
    return null;
  }
}
