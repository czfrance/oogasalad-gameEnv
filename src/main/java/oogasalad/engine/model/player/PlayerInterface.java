package oogasalad.engine.model.player;

import oogasalad.engine.model.Oracle;
import oogasalad.engine.model.board.Board;
import oogasalad.engine.model.board.Position;
import oogasalad.engine.model.engine.Engine;
import oogasalad.engine.model.move.Move;
import oogasalad.engine.model.utilities.Pair;

/**
 * 
 */
public interface PlayerInterface {

    /**
     * Given a board state and the oracle for generating
     * moves, the player returns a decision on a move
     *
     * Right now the engine is the input, but this should be
     * replaced by an 'Oracle' object which has the role of
     * using rules to generate possible moves
     */
     Pair<Position, Move> chooseMove();

  void onCellSelect(int i, int j);
}