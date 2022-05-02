package oogasalad.engine.model.ai.moveSelection;

import oogasalad.engine.model.ai.AIOracle;
import oogasalad.engine.model.ai.enums.Difficulty;
import oogasalad.engine.model.ai.evaluation.Evaluation;
import oogasalad.engine.model.ai.evaluation.StateEvaluator;
import oogasalad.engine.model.ai.evaluation.memoize.MemoizeMaker;
import oogasalad.engine.model.ai.evaluation.memoize.Memoizer;
import oogasalad.engine.model.board.Board;

/**
 * @author Alex Bildner
 * A move selection class which caches/memoizes the evaluations it makes, trading some space for faster execution
 */
public class CachingTreeSearcher extends TreeSearcher {
  private final Memoizer memoizer;

  /**
   * Instantiates a new Caching Tree Searcher with provided configuration/dependencies.
   *
   * @param difficulty     AI Difficulty
   * @param stateEvaluator State Evaluator to evaluate Board states
   * @param aiOracle       Oracle to use to query for available moves and check for winning states
   * @param memoizeMaker   Memoizer constructor injected through implementation functional interface,
   *                       allows for any Memoizer to be used without the need for this class to
   *                       know the concrete class implementing the interface
   */
  public CachingTreeSearcher(Difficulty difficulty, StateEvaluator stateEvaluator, AIOracle aiOracle, MemoizeMaker memoizeMaker) {
    super(difficulty, stateEvaluator, aiOracle);
    this.memoizer = memoizeMaker.getMemoizer(stateEvaluator);
  }

  /**
   * Returns the evaluation for the give board, using memoizer to save time if evaluation is in cache
   * @param board the Board to evaluate
   * @return the Evaluation of the board using the evaluation function provided during construction of the memoizer
   */
  @Override
  protected Evaluation getEvaluation(Board board) {
    return memoizer.get(board);
  }
}
