package oogasalad.engine.model.rule;

import oogasalad.engine.model.board.Board;
import oogasalad.engine.model.board.cells.Position;
import oogasalad.engine.model.board.exceptions.OutOfBoardException;
import oogasalad.engine.model.logicelement.actions.Action;
import oogasalad.engine.model.logicelement.conditions.Condition;

public class PersistentMove implements AbstractMove {

  private String myName;
  private Condition[] myConditions;
  private Action[] myActions;

  public PersistentMove(String name, Condition[] conditions, Action[] actions) {
    myName = name;
    myConditions = conditions;
    myActions = actions;
  }

  @Override
  public Board doMove(Board board, Position referencePoint) {
    if (isValid(board, referencePoint)) {
      for (Action action: myActions) {
        board = action.execute(board, referencePoint);
      }
      return board;
    }
    return board;
  }

  @Override
  public boolean isValid(Board board, Position referencePoint) {
    try {
      for (Condition condition : myConditions) {
        if (!condition.isTrue(board, referencePoint)) {
          return false;
        }
      }
      return true;
    } catch (OutOfBoardException e) {
      return false;
    }
  }

  @Override
  public String getName() {
    return myName;
  }
}
