package oogasalad.engine.model.engine;

import java.util.Set;
import oogasalad.engine.model.board.misc.OutOfBoardException;
import oogasalad.engine.model.board.boards.Board;
import oogasalad.engine.model.driver.Game;
import oogasalad.engine.model.move.Move;

/**
 * @author Haris Adnan
 * Class that saves a game to a log and creates a JSON File that is saved.
 *
 */
public class SaveGameEngine extends Engine{

  public SaveGameEngine(Game game) {
    super(game, null, null, null, null, null);
  }

  @Override
  public void onCellSelect(int x, int y) throws OutOfBoardException {
  }

  @Override
  public Set<Move> getValidMoves(Board board, int i, int j) {
    return null;
  }

  @Override
  public Board getGameStateBoard() {
    return null;
  }

  public void saveGame(){
    Board board = getGame().getBoard();
  }


}
