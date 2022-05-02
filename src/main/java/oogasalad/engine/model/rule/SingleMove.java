package oogasalad.engine.model.rule;

import oogasalad.engine.model.board.exceptions.OutOfBoardException;
import oogasalad.engine.model.logicelement.actions.Action;
import oogasalad.engine.model.board.Board;
import oogasalad.engine.model.board.cells.Position;
import oogasalad.engine.model.logicelement.conditions.Condition;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Defines Movements, which contain conditions and actions
 *
 * @author Jake Heller
 */
public class SingleMove implements Move {

  private static final Logger LOG = LogManager.getLogger(SingleMove.class);

  private String myName;
  private Condition[] myConditions;
  private Action[] myActions;
  private int myRepI;
  private int myRepJ;
  private Position myRepresentativePoint;
  private boolean myIsPersistent;

  /**
   *
   * @param name name of rule
   * @param conditions conditions which all must be true for rule to be valid
   * @param actions actions which get executed when rule is chosen
   * @param representativePoint relative point which might be shown to a user to represent this move
   */
  public SingleMove(String name, Condition[] conditions, Action[] actions, Position representativePoint) {
    this(name, conditions, actions, representativePoint, false);
  }

  /**
   *
   * @param name name of rule
   * @param conditions conditions which all must be true for rule to be valid
   * @param actions actions which get executed when rule is chosen
   * @param representativePoint relative point which might be shown to a user to represent this move
   * @param isPersistent if the rule is persistent (executed after every move)
   */
  public SingleMove(String name, Condition[] conditions, Action[] actions, Position representativePoint, boolean isPersistent) {
    myName = name;
    myConditions = conditions;
    myActions = actions;
    myRepI = representativePoint.row();
    myRepJ = representativePoint.column();
    myRepresentativePoint = representativePoint;
    myIsPersistent = isPersistent;
  }

  /**
   * Checks to see if rule is valid
   * @param board
   * @param referencePoint
   * @return
   */
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

  /**
   * Returns true if this move is persistent
   * @return
   */
  public boolean isPersistent() {
    return myIsPersistent;
  }

  @Override
  public String getName() {
    return myName;
  }

  private Position getRepresentativeCell(int row, int column) {
    return getRepresentativeCell(new Position(row,column));
  }

  /**
   *
   * @param referencePoint reference point for move
   * @return
   */
  public Position getRepresentativeCell(Position referencePoint) {
    return myRepresentativePoint.add(referencePoint);
  }

  /**
   *
   * @param board resultant board
   * @param referencePoint
   * @return
   */
  public Board doMove(Board board, Position referencePoint) {
    if (isValid(board, referencePoint)) {

      for (Action action: myActions) {
        board = action.execute(board, referencePoint);
      }
      return board;
    }
    return board;
  }
}
