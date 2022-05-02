package oogasalad.engine.model.parser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import oogasalad.engine.model.board.Board;
import oogasalad.engine.model.rule.Rule;
import oogasalad.engine.model.rule.terminal_conditions.EndRule;
import oogasalad.engine.model.rule.SingleMove;

/**
 * Parser that reads an existing game configuration from a directory. Responsible for creating a
 * board, reading rules, and creating win conditions.
 * <p>
 * TODO: Maybe make this class implement the Parser Interface. This would require there to be a
 * TODO: single data structure that encapsulates the board, rules, winconditions, etc. Need to
 * TODO: discuss this more with entire team.
 *
 * @author Shaan Gondalia
 */
public class GameParser {

  private final File configFile;
  private final BoardParser boardParser;
  private final RuleParser moveParser;
  private final WinConditionParser winConditionParser;
  private final MetadataParser metadataParser;
  private final PlayerParser playerParser;


  /**
   * Creates a new Parser from a configuration file
   *
   * @param configFile the configuration file to parse from
   */
  public GameParser(File configFile) {
    this.configFile = configFile;
    boardParser = new BoardParser();
    moveParser = new RuleParser();
    winConditionParser = new WinConditionParser();
    metadataParser = new MetadataParser();
    playerParser = new PlayerParser();
  }

  /**
   * Reads a board from the configuration file attached to the parser
   *
   * @return a board with the correct starting configuration
   */
  public Board parseBoard() throws FileNotFoundException {
    return boardParser.parse(configFile);
  }

  /**
   * Reads an array of rules from the configuration file attached to the parser
   *
   * @return an array of rules
   */
  public Collection<SingleMove> readMoves() throws FileNotFoundException {
    return moveParser.parse(configFile);
  }

  public Collection<Rule> readRules() throws FileNotFoundException {
    Collection<Rule> rules = new ArrayList<>();
    rules.addAll(readMoves());
    rules.addAll(readWinConditions());
    return rules;
  }

  /**
   * Reads an array of rules from the configuration file attached to the parser
   *
   * @return an array of rules
   */
  public Map<String, String> readMetadata() throws FileNotFoundException {
    return metadataParser.parse(configFile);
  }

  /**
   * Reads a collection of win conditions from the configuration file attached to the parser
   *
   * @return a collection of win conditions
   */
  public Collection<EndRule> readWinConditions() throws FileNotFoundException {
    return winConditionParser.parse(configFile);
  }

  public Integer readNumberOfPlayers() throws FileNotFoundException {
    return playerParser.parse(configFile);
  }
}
