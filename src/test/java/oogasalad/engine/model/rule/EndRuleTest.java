package oogasalad.engine.model.rule;

import oogasalad.engine.model.board.Board;
import oogasalad.engine.model.engine.Oracle;
import oogasalad.engine.model.logicelement.conditions.Condition;
import oogasalad.engine.model.logicelement.conditions.position_independent_conditions.Constant;
import oogasalad.engine.model.logicelement.winner.PlayerWins;
import oogasalad.engine.model.logicelement.winner.WinDecision;
import oogasalad.engine.model.rule.terminal_conditions.EndRule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EndRuleTest {

  Oracle myOracle;
  EndRule rule;

  @BeforeEach
  void setup() {
    WinDecision playerWins = new PlayerWins(new int[]{1});
    Condition truee = new Constant(new int[]{1});
    rule = new EndRule("Player 1 Wins", new Condition[]{truee}, playerWins);
    myOracle = new Oracle(Arrays.stream((new Rule[]{rule})).toList(), 2);
  }

  @Test
  void testTrue() {
    Board board = new Board(3,3);
    assertTrue(myOracle.isTerminalState(board));
    assertEquals(1, myOracle.getWinner(board));
  }

}
