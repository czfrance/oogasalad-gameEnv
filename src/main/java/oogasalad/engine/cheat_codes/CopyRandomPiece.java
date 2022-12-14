package oogasalad.engine.cheat_codes;

import java.util.List;
import java.util.Random;
import oogasalad.engine.controller.Controller;
import oogasalad.engine.model.board.Board;
import oogasalad.engine.model.board.cells.PositionState;

/**
 * Places a random piece to the board from pieces already on the board
 * @author Robert Cranston
 */
public class CopyRandomPiece implements CheatCode{
  private final Random r = new Random();

  /**
   * adds a random piece to the given board. Updates the board through the controller.
   * @param board current game board
   * @param controller controller for the
   * @return  board with the update after the cheat code
   */
  @Override
  public Board accept(Board board, Controller controller) {
    List<PositionState> empty = (board.getPositionStatesStream().filter(PositionState::isEmpty).toList());
    List<PositionState> taken = (board.getPositionStatesStream().filter(PositionState::isPresent).toList());
    PositionState copy = taken.get(generateRandom(taken.size()));
    if(empty!=null) {
      return board.placePiece(new PositionState(empty.get(generateRandom(empty.size())).position(), copy.piece()));
    }
    return board;

  }
  private int generateRandom(int maxPlusOne){
    return r.nextInt(maxPlusOne);
  }
}
