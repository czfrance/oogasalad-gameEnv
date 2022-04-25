package oogasalad.engine.model.logicelement.winner;
import static oogasalad.engine.model.board.utilities.BoardUtilities.getNotSatisfyingPositionStatesSeq;

import java.util.Optional;
import oogasalad.engine.model.board.Board;
import oogasalad.engine.model.board.cells.Piece;
import oogasalad.engine.model.board.cells.PositionState;
import org.jooq.lambda.Seq;
/**
 * Class that decides winner based on which player has more pieces currently on the board.
 * @author Alex Bildner, Robert Cranston
 */
public class MostPieces extends AbstractWinDecision {

  /**
   *
   * @param parameters unused
   */
  public MostPieces(int[] parameters) {
    super(parameters);
  }

  /**
   * Counts how many pieces both players currently have on the board and returns the one with the highest amount
   * @param board current board state
   * @return int representation of player with the most pieces
   */
  @Override
  public int decideWinner(Board board) {
    var nonEmptyPositionStates = getNotSatisfyingPositionStatesSeq(board, positionState -> positionState.piece()== Piece.EMPTY);
    return getMostFrequentPlayer(nonEmptyPositionStates);
  }

  private Integer getMostFrequentPlayer(Seq<PositionState> nonEmptyPositionStates) {
    Optional<Integer> mode = nonEmptyPositionStates.map(PositionState::player).mode();
    return mode.orElse(-1);
  }
}
