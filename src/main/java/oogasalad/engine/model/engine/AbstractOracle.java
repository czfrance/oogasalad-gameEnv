package oogasalad.engine.model.engine;

import oogasalad.engine.model.board.Board;
import oogasalad.engine.model.rule.terminal_conditions.EndRule;

public interface AbstractOracle<E> {

  /**
   * Gets choices for a specific player
   * Switches player to specified player if not active player
   * @param board
   * @param player
   * @return
   */
  public E getChoices(Board board, int player);

  /**
   * Returns all valid moves for this board state as a stream
   * @param board
   * @return
   */
  E getChoices(Board board);

  /**
   * Returns next state for this board, including incrementing player
   * @param board
   * @param choice
   * @return
   */
  Board getNextState(Board board, Choice choice);

  /**
   * Returns player id if that player is a winner given board state
   * if no winner for board state, returns -1
   * @param board
   * @return
   */
  int getWinner(Board board);

  /**
   * Returns true if board is in terminal state
   * @param board
   * @return
   */
  boolean isTerminalState(Board board);
}
