package oogasalad.engine.model.board.misc;

public class OutOfBoardException extends IndexOutOfBoundsException {
  //TODO: make sure this message is informative
  public OutOfBoardException(String message) {
    super(message);
  }
}
