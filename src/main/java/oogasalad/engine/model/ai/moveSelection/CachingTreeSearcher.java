package oogasalad.engine.model.ai.moveSelection;

import oogasalad.engine.model.engine.StreamOracle;
import oogasalad.engine.model.ai.enums.Difficulty;
import oogasalad.engine.model.ai.evaluation.Evaluation;
import oogasalad.engine.model.ai.evaluation.StateEvaluator;
import oogasalad.engine.model.ai.evaluation.memoize.MemoizeMaker;
import oogasalad.engine.model.ai.evaluation.memoize.Memoizer;
import oogasalad.engine.model.board.Board;

/**
 * @author Alex Bildner
 */
public class CachingTreeSearcher extends TreeSearcher {
  private Memoizer memoizer;

  public CachingTreeSearcher(Difficulty difficulty, StateEvaluator stateEvaluator, StreamOracle aiOracle, MemoizeMaker memoizeMaker) {
    super(difficulty, stateEvaluator, aiOracle);
    this.memoizer = memoizeMaker.getMemoizer(stateEvaluator);
  }

  @Override
  protected Evaluation getEvaluation(Board board) {
    return memoizer.get(board);
  }
}
