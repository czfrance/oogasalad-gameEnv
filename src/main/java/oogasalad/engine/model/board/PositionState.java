package oogasalad.engine.model.board;


public record PositionState(Position position, Piece piece) {

  //Just a convenience function
  public int x(){
    return position.x();
  }

  //Just a convenience function
  public int y(){
    return position.y();
  }


  //Just a convenience function
  public int type() {
    return piece.type();
  }

  //Just a convenience function
  public int player() {
    return piece.player();
  }

  @Deprecated
  public Position getKey() {
    return null;
  }

  @Deprecated
  public OldPiece getValue() {
    return null;
  }
}
