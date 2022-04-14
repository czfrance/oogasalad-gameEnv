package oogasalad.engine.model.board.misc;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Stream;
import java.util.stream.Stream.Builder;
import oogasalad.engine.model.board.boards.Board;
import oogasalad.engine.model.board.components.Position;
import oogasalad.engine.model.board.components.PositionState;
import oogasalad.engine.model.setup.Constants;
import oogasalad.engine.model.setup.Delta;
import org.jooq.lambda.Seq;

public class Ray {

  private Ray() {
    throw new IllegalStateException("This class should not be instantiated");
  }

  public static Stream<PositionState> getDirectionalRay(Board board, Position position, Direction direction) {
    return Ray.getDirectionalRayWhileCondition(board, position, direction, positionState -> true);
  }

  public static Map<Direction, Stream<PositionState>> getDirectionalRays(Board board, Position position, Collection<Direction> directions) {
    return Seq.seq(directions)
              .toMap(direction -> direction,
                     direction -> Ray.getDirectionalRayWhileCondition(board, position, direction, positionState -> true));
  }


  public static Stream<PositionState> getDirectionalRayWhileCondition(Board board, Position position, Direction direction, Predicate<PositionState> positionStatePredicate) {
    List<Predicate<PositionState>> positionStatePredicates = List.of(positionStatePredicate, positionState -> true);
    return getDirectionalRayWhileConditions(board, position, direction, positionStatePredicates);
  }


  public static Stream<PositionState> getDirectionalRayWhileConditions(Board board, Position position, Direction direction, List<Predicate<PositionState>> positionStatePredicates) {
    Builder<PositionState> ray = Stream.builder();
    int i = position.i();
    int j = position.j();
    Delta delta = Constants.DIRECTIONDELTAS.get(direction);
    Predicate<PositionState> positionStatePredicate = reducePredicates(positionStatePredicates); // Combines list of predicates into one predicate which is when they are all true
    while(isValid(board, i, j) && passesCondition(board, positionStatePredicate, i, j)){
      ray.accept(board.getPositionStateAt(i,j));
      i += delta.idelta();
      j += delta.jdelta();
    }

    return ray.build();

  }

  private static Predicate<PositionState> reducePredicates(
      List<Predicate<PositionState>> positionStatePredicates) {
    return positionStatePredicates.stream().reduce(positionState -> true,
        Predicate::and);
  }

  public static Stream<PositionState> getDirectionalRayUntilCondition(Board board, Position position, Direction direction, Predicate<PositionState> positionStatePredicate) {
    return getDirectionalRayWhileCondition(board, position,direction, positionStatePredicate.negate());
  }

  public static Stream<PositionState> getDirectionalRayUntilAnyOfConditions(Board board, Position position, Direction direction, List<Predicate<PositionState>> positionStatePredicates) {
    Predicate<PositionState> positionStatePredicate = positionStatePredicates.stream().reduce(positionState -> false,
        Predicate::or);
    return getDirectionalRayWhileCondition(board, position,direction, positionStatePredicate.negate());
  }

  private static boolean passesCondition(Board board, Predicate<PositionState> positionStatePredicate, int i,
      int j) {
    return positionStatePredicate.test(board.getPositionStateAt(i, j));
  }

  private static boolean isValid(Board board, int i, int j) {
    return board.isValidPosition(i, j);
  }

}
