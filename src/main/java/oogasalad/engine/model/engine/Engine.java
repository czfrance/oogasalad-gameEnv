package oogasalad.engine.model.engine;

import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import oogasalad.engine.model.board.OutOfBoardException;
import oogasalad.engine.model.board.Board;
import oogasalad.engine.model.board.Position;
import oogasalad.engine.model.conditions.terminal_conditions.WinCondition;
import oogasalad.engine.model.driver.Game;
import oogasalad.engine.model.move.Rule;

import oogasalad.engine.model.player.Player;
import org.jooq.lambda.function.Consumer0;


public abstract class Engine {

  private Game myGame;

  private List<Rule> myMoves;
  private List<WinCondition> myWinConditions;
  private Consumer<Board> updateView;
  private Consumer<Set<Position>> setViewValidMarks;
  private Consumer0 clearViewMarkers;

  private List<Player> players;

  public Engine(Game game, List<Rule> rules,
      List<WinCondition> winConditions, Consumer<Board> update, Consumer<Set<Position>> setValidMarks, Consumer0 clearMarkers) {
    myGame = game;
    myWinConditions = winConditions;
    myMoves = rules;
    updateView = update;
    setViewValidMarks = setValidMarks;
    clearViewMarkers = clearMarkers;
  }

  public Engine(Game game){
    myGame = game;
  }

  protected List<Rule> getMoves() {
    return myMoves;
  }
  protected List<WinCondition> getWinConditions() {
    return myWinConditions;
  }

  /**
   * @param player player requesting possible actions
   * @return
   */
  public Rule[] getPossibleActions(int player) {

    return null;
  }
  protected void updateView(Board board){
    updateView.accept(board);
  }
  protected void setMarkers(Set<Position> validMoves){
    if(validMoves != null) {
      setViewValidMarks.accept(validMoves);
    }
  }
  protected void clearMarkers(){
    clearViewMarkers.accept();
  }

  protected Game getGame() {
    return myGame;
  }

  public abstract void onCellSelect(int x, int y)
      throws OutOfBoardException;

  public abstract Set<Rule> getValidMoves(Board board, int i, int j);

  public abstract Board getGameStateBoard();
}
