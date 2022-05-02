package oogasalad.engine.model.ai;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import oogasalad.engine.model.board.Board;
import oogasalad.engine.model.engine.Choice;
import oogasalad.engine.model.engine.Oracle;
import oogasalad.engine.model.player.AbstractPlayer;
import oogasalad.engine.model.player.Player;

/**
 * Implements random player which selects a move at
 * random from all available choices
 *
 * @author Jake Heller
 */
public class RandomPlayer extends AbstractPlayer {

  private Oracle myOracle;

  public RandomPlayer(Oracle oracle,
      BiConsumer<Player, Choice> executeMove) {
    super(executeMove);
    setOracle(oracle);
  }

  @Override
  public void chooseMove(Board board) {
    setGameBoard(board);
    Stream<Choice> choices = getOracle().getChoices(getGameBoard());
    List<Choice> choicesList = choices.toList();
    Random rand = new Random();
    int index = rand.nextInt(choicesList.size());
    executeMove(this, choicesList.get(index));
  }

  @Override
  public void onCellSelect(int i, int j) {

  }

  protected Oracle getOracle() {
    return myOracle;
  }


  private void setOracle(Oracle oracle) {
    this.myOracle = oracle;
  }

  /**
   * Returns valid choices as a set
   * empty set if oracle is null
   * @return
   */
  protected Set<Choice> getMoves() {
    if (myOracle != null) {
      return myOracle.getChoices(getGameBoard()).collect(Collectors.toSet());
    }
    else {
      return new HashSet<>();
    }
  }
}
