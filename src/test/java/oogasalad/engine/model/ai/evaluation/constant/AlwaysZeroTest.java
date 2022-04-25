package oogasalad.engine.model.ai.evaluation.constant;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;
import oogasalad.engine.model.ai.evaluation.Evaluation;
import oogasalad.engine.model.board.Board;
import oogasalad.engine.model.board.cells.Piece;
import org.assertj.core.util.Arrays;
import org.jooq.lambda.Seq;
import org.junit.jupiter.api.Test;

class AlwaysZeroTest {

  private static AlwaysZero alwaysZero = new AlwaysZero();
  private static Constant constant = new AlwaysZero();

  @Test
  void evaluate() {
    for(int player: Arrays.array(Piece.PLAYER_ONE, Piece.PLAYER_TWO)) {
      Stream<Board> randomBoards = getRandomBoards(2);
      randomBoards.forEach(board -> assertEquals(new Evaluation(0,0), alwaysZero.evaluate(board)));

      randomBoards = getRandomBoards(2);
      randomBoards.forEach(board -> assertEquals(new Evaluation(0,0), constant.evaluate(board)));
    }
  }


  @Test
  void noErrors() {
    for(int player: Arrays.array(Piece.PLAYER_ONE, Piece.PLAYER_TWO)) {
      Stream<Board> randomBoards = getRandomBoards(2);
      randomBoards.forEach(board -> assertDoesNotThrow(() -> alwaysZero.evaluate(board)));

      randomBoards = getRandomBoards(2);
      randomBoards.forEach(board -> assertDoesNotThrow(() -> constant.evaluate(board)));

    }
  }

  private Stream<Board> getRandomBoards(int i) {
    return Seq.range(0,i).map(integer -> new Board(integer+2, integer+2));
  }
}