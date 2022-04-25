package oogasalad.engine.model.engine;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.stream.Stream;
import oogasalad.engine.model.board.Board;
import oogasalad.engine.model.parser.GameParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class OracleTest {

  private final String CHECKERS_CONFIG = "data/games/checkers/config.json";
  GameParser parser = new GameParser(new File(CHECKERS_CONFIG));

  private Oracle myOracle;
  private Board myBoard;

  @BeforeEach
  void setup() throws FileNotFoundException {
    myOracle = new Oracle(parser.readRules(), 2);
    myBoard = parser.parseBoard();
  }

  @Test
  void testGetChoice() {
    Stream<Choice> choices = myOracle.getValidChoices(myBoard);
    assertEquals(choices.count(), 7);
  }
}
