package oogasalad.engine.model.engine;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.stream.Stream;
import oogasalad.engine.model.board.Board;
import oogasalad.engine.model.parser.GameParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests Oracle class
 *
 * @author Jake Heller
 */
public class OracleTest {

  private final String CHECKERS_CONFIG = "data/games/checkers/config.json";
  private final String TTT_CONFIG = "data/games/tictactoe/config.json";
  GameParser parser = new GameParser(new File(CHECKERS_CONFIG));

  private Oracle myOracle;
  private Board myBoard;

  @BeforeEach
  void setup() throws FileNotFoundException {
    myOracle = new Oracle(parser.readRules(), parser.readNumberOfPlayers());
    myBoard = parser.parseBoard();
  }

  @Test
  void testGetChoice() {
    Stream<Choice> choices = myOracle.getChoices(myBoard);
    assertEquals(choices.count(), 7);
  }

  @Test
  void testDraw() throws FileNotFoundException {
    myBoard = new Board(3,3);
    parser = new GameParser(new File(TTT_CONFIG));
    myOracle = new Oracle(parser.readRules(), parser.readNumberOfPlayers());
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        myBoard = myBoard.placeNewPiece(i,j,0,0);
      }
    }
    assertTrue(myOracle.isDraw(myBoard));
    assertEquals(0, myOracle.getWinner(myBoard));
  }
}
