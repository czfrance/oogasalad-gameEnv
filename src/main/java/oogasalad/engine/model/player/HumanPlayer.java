package oogasalad.engine.model.player;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import oogasalad.engine.model.board.Board;
import oogasalad.engine.model.board.cells.Position;
import oogasalad.engine.model.engine.Choice;
import oogasalad.engine.model.engine.Oracle;
import oogasalad.engine.model.rule.Move;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class HumanPlayer extends AbstractPlayer {
  private static final Logger LOG = LogManager.getLogger(HumanPlayer.class);

  private Position mySelectedCell = null;
  private Set<Position> myValidMoves = null;

  private Move mySelectedMove = null;
  private Choice myChoice;

  private Consumer<Set<Position>> mySetValidMarks;
  private Oracle myOracle;

  public HumanPlayer(Oracle oracle, BiConsumer<Player, Choice> executeMove, Consumer<Set<Position>> setValidMarks) {
    super(executeMove);
    mySetValidMarks = setValidMarks;
    myOracle = oracle;
  }

  @Override
  public void chooseMove(Board board) {
    setGameBoard(board);
  }

  public void onCellSelect(int i, int j) {
    Position cellClicked = new Position(i, j);
    if (getGameBoard() != null) {
      if (mySelectedCell == null) {
        makePieceSelected(i, j);
      } else {
        Oracle oracle = getOracle();
        Board board = getGameBoard();
        Optional<Move> move = oracle.getMoveSatisfying(board, mySelectedCell, cellClicked);
        if (move.isPresent()) {
          mySelectedMove = move.get();
          myChoice = new Choice(mySelectedCell, mySelectedMove, board);
          LOG.info("Move {} selected", mySelectedMove.getName());
          resetSelected();
          executeMove(this, myChoice);
        }
        resetSelected();
      }
    }
  }

  private void makePieceSelected(int x, int y) {
    Board board = getGameBoard();
    if (!board.isEmpty(x, y) && board.getPositionStateAt(x, y).player() == board.getPlayer() || board.isEmpty(x, y)) {
      mySelectedCell = new Position(x, y);
      myValidMoves = new HashSet<>(getOracle().getRepresentativePoints(getOracle().getValidMovesForPosition(board, mySelectedCell), mySelectedCell));
      setMarkers(myValidMoves);
      LOG.info("{} valid moves for this piece\n", myValidMoves.size());
    }
  }

  private void setMarkers(Set<Position> positions) {
    if (mySetValidMarks != null) {
      mySetValidMarks.accept(positions);
    }
  }

  private void clearMarkers() {
    setMarkers(new HashSet<>());
  }

  private void resetSelected() {
    mySelectedCell = null;
    mySelectedMove = null;
    myValidMoves = null;
    clearMarkers();
  }

  @Override
  public void addDependencies(Oracle oracle, BiConsumer<Player, Choice> executeMove,
      Consumer<Set<Position>> setValidMarks){
    setOracle(oracle);
    super.setExecuteMove(executeMove);
    mySetValidMarks = setValidMarks;
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
      return myOracle.getValidChoices(getGameBoard()).collect(Collectors.toSet());
    }
    else {
      return new HashSet<>();
    }
  }
}
