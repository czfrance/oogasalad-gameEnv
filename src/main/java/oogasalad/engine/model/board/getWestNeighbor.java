package oogasalad.engine.model.board;

import java.util.Optional;

public class getWestNeighbor extends getDirectionNeighbor {
  @Override
  public Optional<Position> getNeighbor(Position pos, Board board) {
    Position west = new Position(pos.getI() - 1, pos.getJ());
    return checkIfValid(board, west);
  }


}