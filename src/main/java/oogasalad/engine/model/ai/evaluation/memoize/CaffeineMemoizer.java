package oogasalad.engine.model.ai.evaluation.memoize;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import oogasalad.engine.model.ai.evaluation.Evaluation;
import oogasalad.engine.model.ai.evaluation.StateEvaluator;
import oogasalad.engine.model.board.Board;

/**
 * @author Alex Bildner
 */
public class CaffeineMemoizer implements Memoizer {
  private final LoadingCache<Board, Evaluation> memoizer;

  public CaffeineMemoizer(StateEvaluator stateEvaluator) {
    // Resource: https://github.com/ben-manes/caffeine/wiki/Population#loading
    memoizer = Caffeine.newBuilder()
        .maximumSize(1_000)
        .expireAfterWrite(20, TimeUnit.MINUTES)
        .build(stateEvaluator::evaluate);
  }

  @Override
  public Evaluation get(Board board) {
    // Resource: https://github.com/ben-manes/caffeine/wiki/Population#loading
    // Lookup and compute an entry if absent, or null if not computable
    return memoizer.get(board);
  }

  public Map<Board, Evaluation> getAll(List<Board> arguments) {
    // Resource: https://github.com/ben-manes/caffeine/wiki/Population#loading
    // Lookup and compute entries that are absent
    return memoizer.getAll(arguments);
  }

}
