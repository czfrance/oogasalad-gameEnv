package oogasalad.engine.cheat_codes;

import java.util.Optional;
import oogasalad.engine.model.board.Board;
import oogasalad.engine.model.board.cells.Position;
import oogasalad.engine.model.board.cells.PositionState;
import oogasalad.engine.model.engine.Engine;

public class RemoveRandomPlayer0Piece implements CheatCode{
  @Override
  public Board accept(Board board, Engine engine) {
    Optional<Position> pos = (board.getPositionStatesStream()
        .filter(e -> e.player() == 0)
        .findAny()
        .map(PositionState::position));
    if(pos.isPresent()){
      return board.removePiece(pos.get());
    }
    return board;
  }
}
