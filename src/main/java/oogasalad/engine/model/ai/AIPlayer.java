package oogasalad.engine.model.ai;

import oogasalad.engine.model.ai.moveSelection.Selects;
import oogasalad.engine.model.board.Board;
import oogasalad.engine.model.engine.Choice;
import oogasalad.engine.model.player.AbstractPlayer;


/**
 * AI Player
 * @author Alex Bildner
 */
public class AIPlayer extends AbstractPlayer {

  private final int playerNumber;
  private final Selects selector;

  /**
   * Instantiates an AI player.
   *
   * @param playerNumberForAI the AI player number
   * @param selects           the selector
   */
  public AIPlayer(int playerNumberForAI, Selects selects) {
    super(null, null); // should be engine
    this.playerNumber = playerNumberForAI;
    this.selector = selects;
  }

  /**
   * The method which has the AI Agent choose an action
   *
   * @param board the board from which the AI Agent must choose an action
   * @return the AI's Choice
   */
  public AIChoice chooseAction(Board board) {
    return selector.selectChoice(board, this.playerNumber);
  }


  /**
   *
   *
   * @param activeBoard the active board
   */
  @Override
  public void chooseMove(Board activeBoard) {
    setGameBoard(activeBoard);
    Board board = getGameBoard();
    AIChoice AIChoice = this.chooseAction(board);
    executeMove(this, (Choice) AIChoice); //Fix unsafe cast
  }

  /**
   *
   *
   * @param i the
   * @param j the j
   */
  @Override
  public void onCellSelect(int i, int j) {
    throw new NoSuchMethodError("no");
  }
}
