package oogasalad.engine.model.ai.moveSelection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import oogasalad.engine.model.engine.StreamOracle;
import oogasalad.engine.model.ai.enums.Difficulty;
import oogasalad.engine.model.ai.evaluation.StateEvaluator;
import oogasalad.engine.model.ai.evaluation.memoize.CaffeineMemoizer;
import oogasalad.engine.model.ai.evaluation.patterns.Pattern;
import oogasalad.engine.model.ai.evaluation.patterns.PatternEvaluator;
import oogasalad.engine.model.ai.evaluation.totals.TotalPieces;
import oogasalad.engine.model.board.Board;
import oogasalad.engine.model.board.cells.Piece;
import oogasalad.engine.model.board.cells.Position;
import oogasalad.engine.model.board.cells.PositionState;
import org.jooq.lambda.Seq;
import org.jooq.lambda.tuple.Tuple2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CachingTreeSearcherTest {
  private static List<CachingTreeSearcher> cachingTreeSearchers;
  private static List<TreeSearcher> treeSearchers;
  private static Seq<Tuple2<CachingTreeSearcher, TreeSearcher>> zipped;

  @BeforeEach
  void setUp() {
    PositionState posState = new PositionState(new Position(0,0), Piece.EMPTY);
    Collection<PositionState> positionStates = new ArrayList<>();
    positionStates.add(posState);
    Pattern p = new Pattern(positionStates);
    Collection<Pattern> patterns  = new ArrayList<>();
    patterns.add(p);
    var difficulties = Difficulty.values();
    var stateEvaluators = new StateEvaluator[] {
        new TotalPieces(),
        new PatternEvaluator(patterns),
    };

    StreamOracle aiOracle = null;

    cachingTreeSearchers = new ArrayList<>();
    treeSearchers = new ArrayList<>();

    for(Difficulty difficulty: difficulties) {
      for(StateEvaluator stateEvaluator: stateEvaluators) {
        cachingTreeSearchers.add(new CachingTreeSearcher(difficulty, stateEvaluator, aiOracle, CaffeineMemoizer::new));
        treeSearchers.add(new TreeSearcher(difficulty, stateEvaluator, aiOracle));
      }
    }

    zipped = zipped();

  }

  private Seq<Tuple2<CachingTreeSearcher, TreeSearcher>> zipped() {
    return Seq.zip(cachingTreeSearchers, treeSearchers);
  }

  @Test
  void selectChoice() {
    Board[] boards = new Board[] {

    };
    int[] players = new int[] {

    };
    for(Board board: boards) {
      for(int player: players) {
        for(var curr: zipped) {
          CachingTreeSearcher cachingTreeSearcher = curr.v1();
          TreeSearcher treeSearcher = curr.v2();
          assertEquals(cachingTreeSearcher.selectChoice(board, player), treeSearcher.selectChoice(board, player));
        }
      }
    }
  }

  @Test
  void runMinimax() {
  }

  @Test
  void getNextBoards() {
  }

  @Test
  void getNextPlayer() {
  }

  @Test
  void getEvaluationForPlayer() {
  }

  @Test
  void getEvaluation() {
  }

  @Test
  void getStateEvaluator() {
  }

  @Test
  void limitReached() {
  }

  @Test
  void getDepthLimit() {
  }

}