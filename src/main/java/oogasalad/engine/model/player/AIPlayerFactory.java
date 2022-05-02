package oogasalad.engine.model.player;

import java.util.ArrayList;
import java.util.Collection;
import oogasalad.engine.model.engine.StreamOracle;
import oogasalad.engine.model.ai.AIPlayer;
import oogasalad.engine.model.ai.SelectorFactory;
import oogasalad.engine.model.ai.enums.Difficulty;
import oogasalad.engine.model.ai.enums.WinType;
import oogasalad.engine.model.ai.evaluation.patterns.Pattern;
import oogasalad.engine.model.ai.moveSelection.Selects;

/**
 * An AI Player Factory which can be used to create AI players to play against humans. This factory
 * is the only necessary interface between the AI and the rest of the program needed to directly
 * create a player
 * @author Alex
 */
public class AIPlayerFactory {

  /**
   * Makes an AI Player
   *
   * @param difficulty   the chosen difficulty
   * @param winType      the win type of the game - total pieces or pattern
   * @param playerNumber the player number of the AI
   * @param aiOracle     the injected oracle which tells the AI what moves are available and their results
   * @param patterns     the patterns which can win a game for games with pattern based winning
   * @return the AI Player
   */
  public AbstractPlayer makeAIPlayer(Difficulty difficulty, WinType winType, int playerNumber, StreamOracle aiOracle, Collection<Pattern> patterns) {
    Selects selects = SelectorFactory.makeSelector(difficulty, winType, playerNumber, aiOracle, patterns);
    return new AIPlayer(playerNumber, selects);
  }

  public static void makePlayer() {
    AIPlayerFactory factory = new AIPlayerFactory();
    Player player = factory.makeAIPlayer(Difficulty.EASY, WinType.TOTAL, 0, null, new ArrayList<>());
  }
}
