package oogasalad.engine.model.engine;

import java.util.Set;
import oogasalad.engine.model.board.exceptions.OutOfBoardException;
import oogasalad.engine.model.board.Board;
import oogasalad.engine.model.board.cells.Position;
import oogasalad.engine.model.rule.SingleMove;
import oogasalad.engine.model.utilities.Pair;

/**
 * @author Haris Adnan
 * Class that saves a game to a log and creates a JSON File that is saved.
 *
 */
// TODO: I don't see why this would be a subclass of engine
public class SaveGameEngine {

  public void onCellSelect(int x, int y) throws OutOfBoardException {
  }

  public Pair<Position, Set<SingleMove>> getValidMoves(Board board, int i, int j) {
    return null;
  }

  public Board getGameStateBoard() {
    return null;
  }

  public void saveGame(){
  }


}
