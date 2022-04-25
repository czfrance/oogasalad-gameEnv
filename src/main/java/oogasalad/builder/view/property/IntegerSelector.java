package oogasalad.builder.view.property;

import javafx.beans.value.ChangeListener;
import javafx.scene.Node;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.util.converter.IntegerStringConverter;
import oogasalad.builder.model.property.Property;
import oogasalad.builder.view.callback.CallbackDispatcher;

/**
 * Field that takes in integer input, returning an integer property.
 *
 * @author Shaan Gondalia, Mike Keohane
 */
public class IntegerSelector implements PropertySelector {

  private Property property;
  private Spinner<Integer> numberPicker;

  /**
   * Creates a new Selector that takes in integer input, returning an integer property.
   *
   * @param property the property that will be "filled in" by the Field
   */
  public IntegerSelector(Property property, CallbackDispatcher dispatcher) {
    this.property = property;
    numberPicker = new Spinner<>(Integer.MIN_VALUE, Integer.MAX_VALUE, Integer.parseInt(property.defaultValueAsString()), 1);
    numberPicker.setEditable(true);
    numberPicker.setId("integerSelector-" + this.property.shortName());
  }

  /**
   * Returns a populated property with the correct value, name, and form
   *
   * @return a populated property with the correct value, name, and form
   */
  @Override
  public Property getProperty() {
    return property.with(property.shortName(), numberPicker.getValue().toString());
  }

  @Override
  public Node display() {
    return numberPicker;
  }

  public void addListener(ChangeListener updateFields) {
  }
}
