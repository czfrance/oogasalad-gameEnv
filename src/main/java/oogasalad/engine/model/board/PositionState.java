package oogasalad.engine.model.board;


public record PositionState(Position position, Piece piece) {

  //Just a convenience function
  public int i(){
    return position.i();
  }

  //Just a convenience function
  public int j(){
    return position.j();
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
