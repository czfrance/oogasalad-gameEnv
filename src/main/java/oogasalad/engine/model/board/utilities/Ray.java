package oogasalad.engine.model.board.utilities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Stream;
import java.util.stream.Stream.Builder;
import oogasalad.engine.model.board.Board;
import oogasalad.engine.model.board.cells.Position;
import oogasalad.engine.model.board.cells.PositionState;
import org.jooq.lambda.Seq;

/**
 * @author Alex Bildner
 */
public class Ray {

  private Ray() {
    throw new IllegalStateException("This class should not be instantiated");
  }

  public static Stream<PositionState> getDirectionalRay(Board board, Position position,
      Direction direction) {
    return getDirectionalRayWhileCondition(board, position, direction, positionState -> true);
  }

  public static Map<Direction, Stream<PositionState>> getDirectionalRays(Board board,
      Position position, Collection<Direction> directions) {
    return Seq.seq(directions)
        .toMap(direction -> direction,
            direction -> getDirectionalRayWhileCondition(board, position, direction,
                positionState -> true));
  }


  public static Stream<PositionState> getDirectionalRayWhileCondition(Board board,
      Position position, Direction direction, Predicate<PositionState> positionStatePredicate) {
    var positionStatePredicates = List.of(positionStatePredicate, positionState -> true);
    return getDirectionalRayWhileConditions(board, position, direction, positionStatePredicates);
  }

  /**
   * Returns a ray of given length
   * If reaches invalid board state, ray cuts off early
   * @param board
   * @param startPosition
   * @param direction
   * @param length
   * @return
   */
  public static Collection<PositionState> getRayOfMaxLength(Board board, Position startPosition, Position direction, int length) {
    Collection<PositionState> ray = new ArrayList<>();

    Position currentPosition = startPosition;
    int currentLength = 0;
    while(board.isValidPosition(currentPosition) && currentLength < length){
      ray.add(board.getPositionStateAt(currentPosition));
      currentPosition = currentPosition.add(direction);
      currentLength++;
    }
    return ray;
  }


  public static Stream<PositionState> getDirectionalRayWhileConditions(Board board,
      Position position, Direction direction,
      List<Predicate<PositionState>> positionStatePredicates) {
    Builder<PositionState> ray = Stream.builder();
    int i = position.row();
    int j = position.column();

    Delta delta = getDirectionDelta(direction);
    var positionStatePredicate = reducePredicates(
        positionStatePredicates); // Combines list of predicates into one predicate which is when they are all true

    while (isValid(board, i, j) && passesCondition(board, positionStatePredicate, i, j)) {
      ray.accept(board.getPositionStateAt(i, j));
      i += delta.idelta();
      j += delta.jdelta();
    }

    return ray.build();

  }

  private static Delta getDirectionDelta(Direction direction) {
    return new Delta(direction.deltaRow(), direction.deltaColumn());
  }

  private static Predicate<PositionState> reducePredicates(
      List<Predicate<PositionState>> positionStatePredicates) {
    return positionStatePredicates.stream().reduce(positionState -> true,
        Predicate::and);
  }

  public static Stream<PositionState> getDirectionalRayUntilCondition(Board board,
      Position position, Direction direction, Predicate<PositionState> positionStatePredicate) {
    return getDirectionalRayWhileCondition(board, position, direction,
        positionStatePredicate.negate());
  }

  public static Stream<PositionState> getDirectionalRayUntilAnyOfConditions(Board board,
      Position position, Direction direction,
      List<Predicate<PositionState>> positionStatePredicates) {
    Predicate<PositionState> positionStatePredicate = positionStatePredicates.stream()
        .reduce(positionState -> false,
            Predicate::or);
    return getDirectionalRayWhileCondition(board, position, direction,
        positionStatePredicate.negate());
  }

  private static boolean passesCondition(Board board,
      Predicate<PositionState> positionStatePredicate, int i,
      int j) {
    return positionStatePredicate.test(board.getPositionStateAt(i, j));
  }

  private static boolean isValid(Board board, int i, int j) {
    return board.isValidPosition(i, j);
  }

}
