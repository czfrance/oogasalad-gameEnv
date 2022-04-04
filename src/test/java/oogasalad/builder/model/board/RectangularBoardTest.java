package oogasalad.builder.model.board;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import oogasalad.builder.model.exception.ElementNotFoundException;
import oogasalad.builder.model.exception.OccupiedCellException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;

/**
 * Tests for RectangularBoard
 *
 * @author Shaan Gondalia
 */
public class RectangularBoardTest {

  private static final int HEIGHT = 8;
  private static final int WIDTH = 10;
  private static final String PIECE_NAME = "PIECE";
  private static final String EMPTY = "empty";
  private static final int X = 5;
  private static final int Y = 7;
  private Board board;

  @BeforeEach
  void setUp(){
      board = new RectangularBoard(WIDTH, HEIGHT);
  }

  @Test
  void testOutOfBounds(){
    assertThrows(IndexOutOfBoundsException.class, () -> board.placePiece(HEIGHT+1, WIDTH + 1, PIECE_NAME));
    assertThrows(IndexOutOfBoundsException.class, () -> board.clearCell(HEIGHT+1, WIDTH + 1));
    assertThrows(IndexOutOfBoundsException.class, () -> board.findPieceAt(HEIGHT+1, WIDTH + 1));
    assertThrows(IndexOutOfBoundsException.class, () -> board.placePiece(-1, WIDTH + 1, PIECE_NAME));
    assertThrows(IndexOutOfBoundsException.class, () -> board.clearCell(-1, WIDTH + 1));
    assertThrows(IndexOutOfBoundsException.class, () -> board.findPieceAt(-1, WIDTH + 1));
  }

  @Test
  void testPiecePlacement() throws OccupiedCellException {
    board.placePiece(X, Y, PIECE_NAME);
    assertEquals(PIECE_NAME, board.findPieceAt(X, Y));
  }

  @Test
  void testEmpty() throws OccupiedCellException {
    for (int i = 0; i < WIDTH; i++) {
      for (int j = 0; j < HEIGHT; j++) {
        assertEquals(EMPTY, board.findPieceAt(i, j));
      }
    }
    board.placePiece(X, Y, PIECE_NAME);
    board.clearCell(X, Y);
    assertEquals(EMPTY, board.findPieceAt(X, Y));
  }

  @Test
  void testOccupiedCell() throws OccupiedCellException {
    board.placePiece(X, Y, PIECE_NAME);
    assertThrows(OccupiedCellException.class, () -> board.placePiece(X, Y, PIECE_NAME));
  }

  @Test
  void testSerialization() throws OccupiedCellException {
    String json = board.toJSON();
    assertEquals(WIDTH * HEIGHT, countMatches(json, EMPTY));

    board.placePiece(X, Y, PIECE_NAME);
    json = board.toJSON();
    assertEquals(WIDTH * HEIGHT - 1, countMatches(json, EMPTY));
    assertEquals(1, countMatches(json, PIECE_NAME));
  }

  @Test
  void testLoad() throws OccupiedCellException {
    // TODO: Change test when loading is implemented
    Board b = board.fromJSON(EMPTY);
  }

  private int countMatches(String str, String target) {
    int lastIndex = 0;
    int count = 0;

    while (lastIndex != -1) {
      lastIndex = str.indexOf(target, lastIndex);
      if (lastIndex != -1) {
        count++;
        lastIndex += target.length();
      }
    }
    return count;
  }

}
