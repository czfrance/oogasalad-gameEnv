package oogasalad.engine.model.logicelement.winner;
import static oogasalad.engine.model.board.utilities.BoardUtilities.*;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import oogasalad.engine.model.board.Board;
import oogasalad.engine.model.board.utilities.BoardUtilities;
import oogasalad.engine.model.board.cells.PositionState;

/**
 * Class that decides winner based on which player still has moves left
 * @author Haris Adnan
 */
public class NoMovesForOnePlayer extends AbstractWinDecision {

  /**
   *
   * @param parameters unused field
   */
  public NoMovesForOnePlayer(int[] parameters) {
    super(parameters);
  }

  /**
   * Checks which player has moves left and returns the player with moves still left
   * @param board current board state
   * @return int representation of player with moves still left
   */
  @Override
  public int decideWinner(Board board) {
    // TODO: implement
    return -1;
  }
}
