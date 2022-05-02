package oogasalad.engine.model.engine;

import oogasalad.engine.model.ai.AIChoice;
import oogasalad.engine.model.board.Board;
import oogasalad.engine.model.board.cells.Position;
import oogasalad.engine.model.rule.SingleMove;

public record Choice(Position position, SingleMove move, Board oldBoard) implements AIChoice {

  public boolean isValidChoice(Board board) {
    return move.isValid(board, position);
  }

  @Override
  public Board getResultingBoard(Board board) {
    move().doMove(board, position());
    return move().doMove(board, position());
  }

  @Override
  public Board getResultingBoard() {
    return getResultingBoard(oldBoard);
  }
}
