package oogasalad.builder.model.element.factory;

import java.util.Collection;
import oogasalad.builder.model.property.Property;
import oogasalad.builder.model.element.Action;
import oogasalad.builder.model.exception.MissingRequiredPropertyException;

/**
 * Factory responsible for creating Action objects based on name and properties. Performs
 * validation on input to make sure all required properties are present.
 *
 * @author Shaan Gondalia
 */
public class ActionFactory extends GameElementFactory<Action> {

  /**
   * Creates a new ActionFactory. See elements.Action properties file for the required
   * properties of an Action.
   */
  public ActionFactory() {
    super("elements.Action");
  }

  /**
   * Returns an Action object based on the given properties
   *
   * @param name       the name of the action
   * @param properties the properties of the action
   * @return an Action object created from the given parameters
   * @throws MissingRequiredPropertyException if the required parameters are not found
   */
  @Override
  public Action createElement(String name, Collection<Property> properties)
      throws MissingRequiredPropertyException {
    validate(properties);
    return new Action(name, properties);
  }
}