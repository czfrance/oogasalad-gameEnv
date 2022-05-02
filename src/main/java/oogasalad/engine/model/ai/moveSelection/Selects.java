package oogasalad.engine.model.ai.moveSelection;

import oogasalad.engine.model.ai.AIChoice;
import oogasalad.engine.model.board.Board;

/**
 * @author Alex Bildner
 */
@FunctionalInterface
public interface Selects {

  /**
   * This method returns the choice of move that has been chosen
   * by the algorithm used in the implementing class. Typically,
   * it is the choice returned by a search algorithm used by the AI
   * but it does not necessarily have to be.
   *
   * @param board     The current board from which a choice needs to be made
   * @param forPlayer The number of the player for whom a choice needs to be made
   * @return the choice selected, which is a selection which implements the AIChoice interface
   */
  AIChoice selectChoice(Board board, int forPlayer);

}
