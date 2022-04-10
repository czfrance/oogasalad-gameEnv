package oogasalad.engine.model.board;

public record Piece (int type, int player) {
  public static int PLAYER_ONE = 0;
  public static int PLAYER_TWO = 1;

  public static int NO_PLAYER = -1;

  public static int BLANK_TYPE = -1;

  public static Piece EMPTY = newEmptyPiece();

  public static Piece newEmptyPiece() {
    return new Piece(BLANK_TYPE, NO_PLAYER);
  }

  public boolean equals(Object o) {
    if (o.getClass() != Piece.class) {
      return false;
    }
    Piece piece = (Piece) o;
    if (piece.player == player() && piece.type == type()) {
      return true;
    }
    return false;
  }
}
