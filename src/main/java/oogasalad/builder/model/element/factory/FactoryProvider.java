package oogasalad.builder.model.element.factory;

/**
 * Class that provides a specific GameElementFactory based on the type of the desired game element.
 *
 * @author Shaan Gondalia
 */
public class FactoryProvider {

  /**
   * Creates a new factory provider
   */
  public FactoryProvider() {
  }

  public GameElementFactory getFactory(String type) {
    // TODO: Replace this with reflection
    return switch (type) {
      case "piece" -> new PieceFactory();
      case "rule" -> new RuleFactory();
      case "win condition" -> new WinConditionFactory();
      case "action" -> new ActionFactory();
      case "condition" -> new ConditionFactory();
      default -> null; // TODO: Throw an exception if type is unknown
    };
  }

}
