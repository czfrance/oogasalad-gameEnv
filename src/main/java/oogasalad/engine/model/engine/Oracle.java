package oogasalad.engine.model.engine;
import static org.jooq.lambda.Seq.seq;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Stream;
import oogasalad.engine.model.board.Board;
import oogasalad.engine.model.board.cells.Position;
import oogasalad.engine.model.board.cells.PositionState;
import oogasalad.engine.model.logicelement.winner.Draw;
import oogasalad.engine.model.rule.Rule;
import oogasalad.engine.model.rule.terminal_conditions.DrawRule;
import oogasalad.engine.model.rule.terminal_conditions.EndRule;
import oogasalad.engine.model.rule.SingleMove;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * This class controls game logic, such as generation of available moves, checking rules, etc
 *
 * @author Jake Heller
 */
public class Oracle implements StreamOracle {

  private static final Logger LOG = LogManager.getLogger(Oracle.class);

  private static final EndRule DEFAULT_DRAW_RULE = new DrawRule("No winner", new Draw(new int[]{}));

  private Collection<SingleMove> myMoves;
  private Collection<EndRule> myEndRules;
  private Collection<SingleMove> myPersistentRules;
  private EndRule myDrawRule;

  private int myNumPlayers;

  public Oracle(Collection<Rule> rules, int numPlayers) {

    Collection<DrawRule> drawRules = filterByClass(rules, DrawRule.class);
    myDrawRule = getDrawRule(drawRules.stream().findFirst());

    Collection<SingleMove> moves = filterByClass(rules, SingleMove.class);
    Collection<EndRule> endRules = filterByClass(rules, EndRule.class);
    for (EndRule rule : endRules) {
      //System.out.println(rule.getName());
    }

    myMoves = moves.stream().filter(move -> !move.isPersistent()).toList();
    myPersistentRules = moves.stream().filter(move -> move.isPersistent()).toList();
    myEndRules = endRules;
    myNumPlayers = numPlayers;

    LOG.info("New: Size of moves, persistent, end rules: {}, {}, {}\n", myMoves.size(),
        myPersistentRules.size(), myEndRules.size());
    //moves = filterByClass(rules, Move.class);
  }

  // generic method to filter collection by class type
  private <T> Collection<T> filterByClass(Collection<Rule> collection, Class<T> type) {
    if (collection == null) {
      return new ArrayList<>();
    }
    return collection.stream().filter(object -> object.getClass().equals(type)).map(object -> (T) object).toList();
  }

  private EndRule getDrawRule(Optional<DrawRule> optionalDrawRule) {
    if (optionalDrawRule.isPresent()) {
      LOG.info("Given draw rule");
      return optionalDrawRule.get();
    }
    else {
      LOG.info("Using default draw rule");
      return DEFAULT_DRAW_RULE;
    }
  }

  /**
   * Returns valid moves for given position and board
   * If you want to use the game's current board, you can
   * use the gameGameStateBoard() function
   *
   * Note: this returns all available moves, not specific to
   * a player
   *
   * @param board
   * @param referencePoint
   * @return
   */
  private Stream<SingleMove> getValidMovesForPosition(Board board, Position referencePoint) {
    return myMoves.stream().filter((move) -> move.isValid(board, referencePoint));
  }

  public Optional<SingleMove> getMoveByName(String name) {
    return myMoves.stream().filter(rule -> rule.getName().equals(name)).findFirst();
  }

  public Stream<Choice> getValidChoicesForPosition(Board board, Position referencePoint) {
    return getValidMovesForPosition(board, referencePoint).map(move -> new Choice(referencePoint, move, board));
  }

  /**
   * Returns all valid moves for this board state as a stream
   * @param board
   * @return
   */
  public Stream<Choice> getChoices(Board board) {
    return board.getPositionStatesStream().flatMap(positionState -> getValidChoicesForPosition(board, positionState.position()));
  }

  /**
   * Applies persistent rules to a board
   * Should be called after a player move gets executed and before
   * the end check
   *
   * @param board
   * @return
   */
  public Board applyPersistentRules(Board board) {
    for (SingleMove rule: myPersistentRules) {
      for (PositionState cell: board) {
        if (rule.isValid(board, cell.position())) {
          board = rule.doMove(board, cell.position());
        }
      }
    }
    return board;
  }

  /**
   * Returns next state for this board, including incrementing player
   * @param board
   * @param choice
   * @return
   */
  public Board getNextState(Board board, Choice choice) {
    if (!choice.isValidChoice(board)) {
      throw new RuntimeException("Invalid Choice");
    }
    else {
      board = choice.move().doMove(board, choice.position());
      board = applyPersistentRules(board);
      board = incrementPlayer(board);
    }
    return board;
  }

  /**
   *
   * @param p1 relative point
   * @param p2 representative point
   * @return
   */
  public Optional<SingleMove> getMoveSatisfying(Board board, Position p1, Position p2) {
    Optional<SingleMove> choice = myMoves.stream().filter(move -> move.isValid(board, p1)).filter(move -> move.getRepresentativeCell(
        p1).equals(p2)).findFirst();
    return choice;
  }

  /**
   * Increments player
   * If game is at a draw, the function should go back to original player
   * @param board
   * @return
   */
  public Board incrementPlayer(Board board) {
    int counter = 1;
    int nextPlayer = (board.getPlayer() + counter) % myNumPlayers;
    while (!getChoices(board, nextPlayer).findAny().isPresent() && counter < 2*myNumPlayers) {
      counter++;
      nextPlayer = (board.getPlayer() + counter) % myNumPlayers;
    }
    return board.setPlayer(nextPlayer);
  }

  /**
   * Gets choices for a specific player
   * Switches player to specified player if not active player
   * @param board
   * @param player
   * @return
   */
  @Override
  public Stream<Choice> getChoices(Board board, int player) {
    board = board.setPlayer(player);
    return getChoices(board);
  }

  @Override
  public boolean isTerminalState(Board board) {
    Optional<EndRule> satisfyingEndRule = getValidEndRules(board).findFirst();
    return satisfyingEndRule.isPresent();
  }

  /**
   * Returns true if there is a draw
   * @param board
   * @return
   */
  public boolean isDraw(Board board) {
    for (int i = 0; i < myNumPlayers; i++) {
      if (getChoices(board, i).findAny().isPresent()) {
        return false;
      }
    }
    return true;
  }

  /**
   * Returns player id if that player is a winner given board state
   * if no winner for board state, returns -1
   * @param board
   * @return
   */
  public int getWinner(Board board) {
    Optional<EndRule> validEndRule = getValidEndRules(board).findFirst();
    return validEndRule.get().getWinner(board);
  }

  private Stream<EndRule> getValidEndRules(Board board) {
    // if there are no valid end rules but game is at a draw
    if (getValidEndRulesWithoutDraw(board).findAny().isEmpty() && isDraw(board)) {
      return Stream.of(myDrawRule);
    }
    else {
      return getValidEndRulesWithoutDraw(board);
    }
  }

  // returns all valid end rules not including the draw end rule
  // if not at winning state, returns empty stream
  private Stream<EndRule> getValidEndRulesWithoutDraw(Board board) {
    return board.getPositionStatesStream().flatMap(positionState ->
        myEndRules.stream().filter(endRule ->
            endRule.isValid(board, positionState.position())));
  }
}
