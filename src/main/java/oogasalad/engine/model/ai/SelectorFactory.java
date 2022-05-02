package oogasalad.engine.model.ai;

import java.util.Collection;
import oogasalad.engine.model.ai.enums.Difficulty;
import oogasalad.engine.model.ai.enums.WinType;
import oogasalad.engine.model.ai.evaluation.StateEvaluator;
import oogasalad.engine.model.ai.evaluation.memoize.CaffeineMemoizer;
import oogasalad.engine.model.ai.evaluation.meta.SeekEquality;
import oogasalad.engine.model.ai.evaluation.patterns.Pattern;
import oogasalad.engine.model.ai.evaluation.patterns.PatternEvaluator;
import oogasalad.engine.model.ai.evaluation.totals.TotalPieces;
import oogasalad.engine.model.ai.moveSelection.CachingTreeSearcher;
import oogasalad.engine.model.ai.moveSelection.Selects;
import oogasalad.engine.model.board.cells.Piece;
import oogasalad.engine.model.engine.StreamOracle;

/**
 * @author Alex Bildner
 */
public class SelectorFactory {

  /**
   * Creates a Selector
   *
   * @param difficulty   the difficulty
   * @param winType      the win type
   * @param playerNumber the player number
   * @param aiOracle     the AI Oracle
   * @param patterns     the patterns for pattern based win games
   * @return the selector
   */
  public static Selects makeSelector(Difficulty difficulty, WinType winType, int playerNumber, StreamOracle aiOracle, Collection<Pattern> patterns) {
    StateEvaluator stateEvaluator = getStateEvaluator(winType, playerNumber, patterns, difficulty==Difficulty.ADAPTIVE);
    return new CachingTreeSearcher(difficulty, stateEvaluator, aiOracle, CaffeineMemoizer::new);
  }

  private static StateEvaluator getStateEvaluator(WinType winType, int playerNumber, Collection<Pattern> patterns, boolean isAdaptive) {
    StateEvaluator stateEvaluator = winType==WinType.PATTERN? new PatternEvaluator(patterns) : new TotalPieces();
    if(isAdaptive) { stateEvaluator = new SeekEquality(stateEvaluator, playerNumber==Piece.PLAYER_ONE, playerNumber==Piece.PLAYER_TWO); }
    return stateEvaluator;
  }


}