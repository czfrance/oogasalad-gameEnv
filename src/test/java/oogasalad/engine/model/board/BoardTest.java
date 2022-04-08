package oogasalad.engine.model.board;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BoardTest {
  Board myBoard = new Board(3, 3);

  @BeforeEach
  void setup() {
    myBoard = new Board(3, 3);
  }

  @Test
  void testPlacement() throws CloneNotSupportedException {
    Board b = myBoard.placePiece(new PositionState(new Position(0,1),0));
    assertTrue(b.isPieceAtCoordinate(0,1).get());
    assertFalse(b.isPieceAtCoordinate(0,1).get());
  }

//
//  @Test
//  void getMyBoard() {
//    //assertEquals(TestBoard, TestBoard.getMyBoard());
//  }
//
//  @Test
//  void selectCell() {
//  }
//
//  @Test
//  void placeNewPiece() throws OutOfBoardException {
//    myBoard.placeNewPiece(1,1, 0, 0);
//    assertTrue(myBoard.getPieceRecord(1,1).isPresent());
//  }
//
//  @Test
//  void remove() throws OutOfBoardException {
//    myBoard.placeNewPiece(1,1, 0, 0);
//    assertTrue(myBoard.getPieceRecord(1,1).isPresent());
//    myBoard.remove(1,1);
//    assertTrue(myBoard.getPieceRecord(1,1).isEmpty());
//  }
//
//  @Test
//  void getPieceRecord() throws OutOfBoardException {
//    myBoard.placeNewPiece(1,1, 0, 0);
//    Optional<PieceRecord> testPiece = myBoard.getPieceRecord(1,1);
//    assertNotNull(testPiece);
//  }
//
//  @Test
//  void move() throws OutOfBoardException {
//    OldPiece TestPiece = new OldPiece(0, 1, 0, 0 );
//
//    myBoard.move(0, 0, 2,2);
//    assertNotNull(myBoard.getPieceRecord(2,2));
//
//  }
//
//  @Test
//  void deepCopyTest() throws OutOfBoardException {
//    myBoard.placeNewPiece(1, 1, 0, 0);
//    Board copyBoard = myBoard.deepCopy();
//    assertTrue(copyBoard.getPieceRecord(1, 1).isPresent());
//    assertFalse(copyBoard.getPieceRecord(0, 1).isPresent());
//  }
//
//  @Test
//  void copyConstructorTest() throws OutOfBoardException {
//    myBoard.placeNewPiece(1, 1, 0, 0);
//
//    Board board = new Board(myBoard);
//    assertTrue(board.getPieceRecord(1, 1).isPresent());
//    assertFalse(board.getPieceRecord(0, 1).isPresent());
//  }
//
//  @Test
//  void isValid() {
//  }
//
//  @Test
//  void deepCopy() {
//  }
//
//  @Test
//  void iterator() {
//
//  }
}