package oogasalad.engine.model.ai;

import static org.junit.jupiter.api.Assertions.*;

import oogasalad.engine.model.ai.enums.Difficulty;
import oogasalad.engine.model.ai.enums.WinType;
import oogasalad.engine.model.board.Piece;
import oogasalad.engine.model.engine.Oracle;
import org.junit.jupiter.api.Test;

class SelectorFactoryTest {

  @Test
  void makeSelectorEasy() {
    assertDoesNotThrow(() -> SelectorFactory.makeSelector(Difficulty.EASY, WinType.TOTAL, Piece.PLAYER_ONE, null, null) );
    assertDoesNotThrow(() -> SelectorFactory.makeSelector(Difficulty.EASY, WinType.TOTAL, Piece.PLAYER_TWO, null, null) );
    assertDoesNotThrow(() -> SelectorFactory.makeSelector(Difficulty.EASY, WinType.TOTAL, Piece.PLAYER_ONE, new Oracle(null, null, null, 2), null) );
  }

  @Test
  void makeSelectorMedium() {
    assertDoesNotThrow(() -> SelectorFactory.makeSelector(Difficulty.MEDIUM, WinType.TOTAL, Piece.PLAYER_ONE, null, null) );
    assertDoesNotThrow(() -> SelectorFactory.makeSelector(Difficulty.MEDIUM, WinType.TOTAL, Piece.PLAYER_TWO, null, null) );
    assertDoesNotThrow(() -> SelectorFactory.makeSelector(Difficulty.MEDIUM, WinType.TOTAL, Piece.PLAYER_ONE, new Oracle(null, null, null, 2), null) );
  }

  @Test
  void makeSelectorHard() {
    assertDoesNotThrow(() -> SelectorFactory.makeSelector(Difficulty.HARD, WinType.TOTAL, Piece.PLAYER_ONE, null, null) );
    assertDoesNotThrow(() -> SelectorFactory.makeSelector(Difficulty.HARD, WinType.TOTAL, Piece.PLAYER_TWO, null, null) );
    assertDoesNotThrow(() -> SelectorFactory.makeSelector(Difficulty.HARD, WinType.TOTAL, Piece.PLAYER_ONE, new Oracle(null, null, null, 2), null) );
  }

  @Test
  void makeSelectorExpert() {
    assertDoesNotThrow(() -> SelectorFactory.makeSelector(Difficulty.EXPERT, WinType.TOTAL, Piece.PLAYER_ONE, null, null) );
    assertDoesNotThrow(() -> SelectorFactory.makeSelector(Difficulty.EXPERT, WinType.TOTAL, Piece.PLAYER_TWO, null, null) );
    assertDoesNotThrow(() -> SelectorFactory.makeSelector(Difficulty.EXPERT, WinType.TOTAL, Piece.PLAYER_ONE, new Oracle(null, null, null, 2), null) );
  }

  @Test
  void makeSelectorRandom() {
    assertDoesNotThrow(() -> SelectorFactory.makeSelector(Difficulty.RANDOM, WinType.TOTAL, Piece.PLAYER_ONE, new Oracle(null, null, null, 2), null) );
    assertDoesNotThrow(() -> SelectorFactory.makeSelector(Difficulty.RANDOM, WinType.TOTAL, Piece.PLAYER_ONE, new Oracle(null, null, null, 1), null) );
    assertDoesNotThrow(() -> SelectorFactory.makeSelector(Difficulty.RANDOM, WinType.PATTERN, Piece.PLAYER_ONE, new Oracle(null, null, null, 2), null) );
    assertDoesNotThrow(() -> SelectorFactory.makeSelector(Difficulty.RANDOM, WinType.PATTERN, Piece.PLAYER_TWO, new Oracle(null, null, null, 2), null) );
    assertDoesNotThrow(() -> SelectorFactory.makeSelector(Difficulty.RANDOM, WinType.TOTAL, Piece.PLAYER_ONE, new Oracle(null, null, null, 1), null) );
    assertDoesNotThrow(() -> SelectorFactory.makeSelector(Difficulty.RANDOM, WinType.PATTERN, Piece.PLAYER_TWO, new Oracle(null, null, null, 2), null) );
  }

}