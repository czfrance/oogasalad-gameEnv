package oogasalad.engine.model.rule;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import oogasalad.engine.model.board.Board;
import oogasalad.engine.model.board.exceptions.OutOfBoardException;
import oogasalad.engine.model.board.cells.Position;
import oogasalad.engine.model.logicelement.actions.Action;
import oogasalad.engine.model.logicelement.actions.Remove;
import oogasalad.engine.model.logicelement.actions.Translate;
import oogasalad.engine.model.logicelement.conditions.Condition;
import oogasalad.engine.model.logicelement.conditions.position_dependent_conditions.IsEmpty;
import oogasalad.engine.model.logicelement.conditions.position_dependent_conditions.IsOccupied;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MoveTest {

  Board myBoard;
  Move myMove;

  @BeforeEach
  void setup() {
    myBoard = new Board(3, 3);
    Condition[] conditions = new Condition[2];
    conditions[0] = new IsOccupied(new int[]{0,0,0,0});
    conditions[1] = new IsOccupied(new int[]{1,1,0,0});

    Action[] actions = new Action[2];
    actions[0] = new Translate(new int[]{0,0,2,2,0});
    actions[1] = new Remove(new int[]{1,1,0});
    Position repPoint = new Position(2,2);

    myMove = new Move("Jump", conditions, actions, repPoint);
  }

  void createJumpMove() {
    Condition[] conditions = new Condition[2];
    conditions[0] = new IsOccupied(new int[]{0,0,0,0});
    conditions[1] = new IsOccupied(new int[]{1,1,0,0});

    Action[] actions = new Action[2];
    actions[0] = new Translate(new int[]{0,0,2,2,0});
    actions[1] = new Remove(new int[]{1,1,0});
    Position repPoint = new Position(2,2);

    myMove = new Move("Jump", conditions, actions, repPoint);
  }

  void createSingleMove() {
    Condition[] conditions = new Condition[2];
    conditions[0] = new IsOccupied(new int[]{0,0,0,0});
    conditions[1] = new IsEmpty(new int[]{1,1,0,0});

    Action[] actions = new Action[1];
    actions[0] = new Translate(new int[]{0,0,1,1,0});
    Position repPoint = new Position(1,1);

    myMove = new Move("Jump", conditions, actions, repPoint);

  }

  @Test
  void basicSingleTest() {
    createSingleMove();

    Position origin = new Position(0,0);

    assertFalse(myMove.isValid(myBoard, origin));

    myBoard = myBoard.placeNewPiece(0,0,0,0);

    assertTrue(myMove.isValid(myBoard, origin));

    assertTrue(myBoard.isOccupied(0,0));
    assertTrue(myBoard.isEmpty(1,1));

    myBoard = myMove.doMove(myBoard, origin);

    assertTrue(myBoard.isEmpty(0,0));
    assertTrue(myBoard.isOccupied(1,1));

    assertFalse(myMove.isValid(myBoard, origin));
    assertTrue(myMove.isValid(myBoard, new Position(1,1)));
  }

  @Test
  void basicJumpTest() throws OutOfBoardException {

    Position origin = new Position(0,0);

    createJumpMove();
    assertFalse(myMove.isValid(myBoard, origin));

    myBoard = myBoard.placeNewPiece(1, 1, 0, 0);
    myBoard = myBoard.placeNewPiece(0,0,0,0);

    assertFalse(myBoard.isEmpty(0,0));
    assertFalse(myBoard.isEmpty(1,1));
    assertTrue(myBoard.isEmpty(2,2));

    assertTrue(myMove.isValid(myBoard, origin));

    myBoard = myMove.doMove(myBoard, origin);

    assertTrue(myBoard.isEmpty(0,0));
    assertTrue(myBoard.isEmpty(1,1));
    assertFalse(myBoard.isEmpty(2,2));

    assertFalse(myMove.isValid(myBoard, origin));
  }
}
