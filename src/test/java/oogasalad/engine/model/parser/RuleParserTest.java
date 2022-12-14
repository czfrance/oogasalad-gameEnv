package oogasalad.engine.model.parser;

import java.io.File;
import java.io.FileNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for RuleParser class
 *
 * @author Shaan Gondalia
 */
public class RuleParserTest {

  private static final String TEST_PARSE_FILENAME = "data/tests/parser/test.json";

  private RuleParser parser;

  @BeforeEach
  public void setUp() {
    parser = new RuleParser();
  }

  @Test
  void testParseRules() throws FileNotFoundException {
    File file = new File(TEST_PARSE_FILENAME);
    parser.parse(file);
  }
}
