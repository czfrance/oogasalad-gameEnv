package oogasalad.engine.model.ai.moveSelection;

import static oogasalad.engine.model.board.cells.Piece.PLAYER_ONE;
import static oogasalad.engine.model.board.cells.Piece.PLAYER_TWO;
import static org.jooq.lambda.Seq.seq;

import java.util.Optional;
import oogasalad.engine.model.ai.AIChoice;
import oogasalad.engine.model.ai.AIOracle;
import oogasalad.engine.model.ai.enums.Difficulty;
import oogasalad.engine.model.ai.evaluation.Evaluation;
import oogasalad.engine.model.ai.evaluation.StateEvaluator;
import oogasalad.engine.model.ai.timeLimiting.TimeLimit;
import oogasalad.engine.model.board.Board;
import org.jooq.lambda.Seq;

/**
 * @author Alex Bildner
 * This class is an implementation of the Selects interface which uses a modifed version of the
 * canonical AI game playing search algorithm minimax. The version used allows it more flexibility
 * in the stateEvaluator by loosening the typical minimax search requirement that the evaluations are
 * zero-sum, allowing for extensions like "adaptive" mode where the algorithm seeks to select a move
 * which keeps the game as close as possibe.
 */
public class TreeSearcher implements Selects {
  private final int maxDepth; // The maximum allowed depth of the search, in place to limit strength
  private final StateEvaluator stateEvaluator;
  private final AIOracle aiOracle;
  private final TimeLimit timeLimit;


  /**
   * Instantiates a new TreeSearcher with the given parameters. These parameters provide for
   * a vast configuration of possible TreeSearcher exact behavior at runtime, because the use
   * of dependency injection/inversion means that the same TreeSearcher class can be used for any
   * difficulty/depth, any state evaluation function, and any game oracle, making the codebase
   * open to extension while maintaing this class as closed to modification.
   *
   * @param difficulty     the difficulty
   * @param stateEvaluator the state evaluator used to estimate the favor-ability of an arbitrary
   *                       game state for a player, being given by dependency injection allows for flexibility
   * @param aiOracle       an object which implements the AIOracle interface and can be used to query
   *                       for available moves and whether certain boards are winning states.
   */
  public TreeSearcher(Difficulty difficulty, StateEvaluator stateEvaluator, AIOracle aiOracle) {
    this.maxDepth = difficulty.depth();
    this.timeLimit = difficulty.timeLimit();
    this.stateEvaluator = stateEvaluator;
    this.aiOracle = aiOracle;
  }

  /**
   * Selects a choice using the modified Minimax search algorithm described above.
   *
   * @param board    the board from which to choose a move from
   * @param forPlayer the player for whom to choose a move for
   * @return the choice selected
   */
  @Override
  public AIChoice selectChoice(Board board, int forPlayer) {
    Optional<AIChoice> aIChoice = getChoices(board, forPlayer).
        maxBy(choice -> runSearch(choice.getResultingBoard(), forPlayer, getDepthLimit()));
    return aIChoice.get(); // Optional is always present because return value of getChoices is non-null for non-winning states
  }

  // starts the timer for search and returns all choices available for a provided player at the provided board
  protected final Seq<AIChoice> getChoices(Board board, int forPlayer) {
    timeLimit.start();
    return seq(aiOracle.getChoices(board, forPlayer));
  }

  // Runs search using modifed version of minimax that allows for non-zero sum evaluation functions
  protected int runSearch(Board board, int player, int depth) {
    // Check for base case of recursion, in which case evaluate board and propagate results up call stack
    if(limitReached(board, depth)) { return getEvaluationForPlayer(board, player); }

    var boards = getNextBoards(board, player);
    int nextPlayer = getNextPlayer(player);
    // Make recursive call to continue searching and base return value on evaluation of later reached boards
    return boards.mapToInt(currBoard -> runSearch(currBoard, nextPlayer, depth-1)).max().orElse(0);
  }

  // Returns all possible boards available for a given player, so that the algorithm can evaluate them
  protected Seq<Board> getNextBoards(Board board, int player) {
    return getChoices(board, player).map(AIChoice::getResultingBoard);
  }

  // Gets the next player such that player one and two alternate moves
  protected int getNextPlayer(int player) {
    return player == PLAYER_ONE ? PLAYER_TWO : PLAYER_ONE;
  }

  // Gets the evaluation (quality/favorability of the board) for a given board and player
  protected int getEvaluationForPlayer(Board board, int player) {
    return getEvaluation(board).forPlayer(player);
  }

  // Returns the Evaluation of a given board
  protected Evaluation getEvaluation(Board board) {
    return getStateEvaluator().evaluate(board);
  }

  // Returns the State Evaluator for this instance
  protected StateEvaluator getStateEvaluator() {
    return this.stateEvaluator;
  }

  // Returns true if search needs to stop due to maximum depth reached, time limit up, or winning board
  protected boolean limitReached(Board board, int depth) {
    return (depth == 0 || timeLimit.isTimeUp()) || aiOracle.isWinningState(board);
  }

  // Returns the search depth limit for this instance
  protected int getDepthLimit() {
    return maxDepth;
  }
}
