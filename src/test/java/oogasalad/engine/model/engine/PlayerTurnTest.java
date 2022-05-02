package oogasalad.engine.model.engine;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.stream.Stream;
import oogasalad.engine.model.board.Board;
import oogasalad.engine.model.driver.Game;
import oogasalad.engine.model.parser.GameParser;
import oogasalad.engine.model.player.HumanPlayer;
import oogasalad.engine.model.player.PlayerManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PlayerTurnTest {
  private final String CHECKERS_CONFIG = "src/test/resources/games/AI-Bug-Checkers/config.json";

  GameParser parser = new GameParser(new File(CHECKERS_CONFIG));
  private Oracle myOracle;
  private Board myStartBoard;
  private PlayerManager myPlayerManager;
  private Game myGame;
  private Engine myEngine;

  @BeforeEach
  void setup() throws FileNotFoundException {
    myOracle = new Oracle(parser.readRules(), parser.readNumberOfPlayers());
    myStartBoard = parser.parseBoard();
    myGame = new Game(myStartBoard);
    myPlayerManager = new PlayerManager();
    myPlayerManager.addPlayer(0, new HumanPlayer(myOracle, null, null));
    myPlayerManager.addPlayer(1, new HumanPlayer(myOracle, null, null));
    myEngine = new Engine(myGame, myPlayerManager, myOracle, null);
  }

  @Test
  void testTurn() throws FileNotFoundException {
    int playerTurn = myGame.getBoard().getPlayer();
    assertEquals(0, playerTurn);

    for (int i = 0; i < 2; i++) {
      playRandomMove();

      playerTurn = myGame.getBoard().getPlayer();

      assertEquals((i+1)%2, playerTurn);
    }

    // at this point it should be player 0's turn, but once player 0 moves
    // player 1 should have no moves, so the next player should be player 0 again

    playRandomMove();

    Board board = myGame.getBoard();

    // the turn should go back to the next player with moves,
    // which in this case is 0
    assertEquals(0, board.getPlayer());
  }

  void playRandomMove() {
    Board board = myGame.getBoard();
    Stream<Choice> choices = myOracle.getChoices(board);
    Choice choice = choices.findAny().get();

    myEngine.playTurn(myPlayerManager.getPlayer(board.getPlayer()), choice);

  }
}
