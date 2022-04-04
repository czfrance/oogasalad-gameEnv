package oogasalad.engine.model.engine;

import oogasalad.engine.model.Game;
import oogasalad.engine.model.OutOfBoardException;
import oogasalad.engine.model.action.Action;
import oogasalad.engine.model.action.Place;
import oogasalad.engine.model.board.Board;
import oogasalad.engine.model.conditions.Condition;
import oogasalad.engine.model.conditions.IsEmpty;
import oogasalad.engine.model.move.Rule;

public class NoSelectionEngine extends Engine {

  public NoSelectionEngine(Game game) {
    super(game);
    createTicTacToeMove();
  }

  public Board onCellSelect(int x, int y) throws OutOfBoardException {
    Board board = getGame().getBoard();

    for (Rule move: getMoves()) {
      if (move.isValid(board, x, y)) {
        board = move.doMovement(board, x, y);
        getGame().setBoard(board);
        return board;
      }
    }
    return board;
  }

  private void createTicTacToeMove() {
    // should Conditions and Actions have the relative relationships build into them?
    Condition[] conditions = new Condition[]{new IsEmpty(new int[]{0, 0})};
    Action[] actions = new Action[]{new Place(new int[]{0, 0, 0, 0})};

    getMoves().add(new Rule(conditions, actions, 0, 0));
  }
}
