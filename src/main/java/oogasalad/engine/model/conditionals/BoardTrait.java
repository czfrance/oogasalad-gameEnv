package oogasalad.engine.model.conditionals;

import oogasalad.engine.model.board.Board;

@FunctionalInterface
public interface BoardTrait {

    public Boolean AvailableMovesForPiece(Board board);

}