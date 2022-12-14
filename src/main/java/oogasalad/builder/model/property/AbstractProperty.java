package oogasalad.builder.model.property;

import java.util.Objects;

/**
 * Abstract class that implements base methods of the Property Interface. This class is immutable,
 * as there is no way to modify its state once it has been created. We opted to create a class
 * instead of a record here in order to implement an inheritance hierarchy for properties (records
 * cannot extend other records/abstract classes).
 *
 * @param <T> The type of the property. Concrete classes should not use generic typing.
 * @author Shaan Gondalia
 * @author Ricky Weerts
 */
public abstract class AbstractProperty<T> implements Property<T> {

  private final String name;
  private final String form;
  private final T value;
  private final T defaultValue;

  /**
   * Creates a new Abstract Property with a name and generic value
   * Sets the set value to be the default value
   *
   * @param name  the name of the property
   * @param value the value of the property
   * @param form  the form of the property
   */
  public AbstractProperty(String name, T value, String form) {
    this(name, value, value, form);
  }

  /**
   * Creates a new Abstract Property with a name and generic value
   *
   * @param name  the name of the property
   * @param value the value of the property
   * @param defaultValue the default value of the property
   * @param form  the form of the property
   */
  public AbstractProperty(String name, T value, T defaultValue, String form) {
    this.name = name;
    this.form = form;
    this.value = value;
    this.defaultValue = defaultValue;
  }

  /**
   * Returns the name of the property
   *
   * @return the name of the property
   */
  public String name() {
    return name;
  }

  /**
   * Returns the form of the property
   *
   * @return the form of the property
   */
  public String form() {
    return form;
  }

  /**
   * Returns the value of the property
   *
   * @return the value of the property
   */
  public T value() {
    return value;
  }

  /**
   * Returns the default value of the property
   *
   * @return the default value of the property
   */
  public T defaultValue() {
    return defaultValue;
  }

  /**
   * Checks whether a property has the same name as another
   *
   * @param o The object to check equality against.
   * @return true if the objects are equal, false if not
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Property property = (Property) o;
    return name.equals(property.name());
  }

  /**
   * Checks whether a property has the same name and valueas another
   *
   * @param o The object to check equality against.
   * @return true if the objects are equal, false if not
   */
  public boolean fullEquals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Property property = (Property) o;
    return name.equals(property.name()) && value.equals(property.value());
  }

  /**
   * Hashes a property
   *
   * @return the hashcode of the property
   */
  @Override
  public int hashCode() {
    return Objects.hash(name, value);
  }

  /**
   * Returns a property identical to this one, except with a different value
   * In contrast to `with()`, the value is provided as an actual type rather than a string
   *
   * @param newValue the value to give the new property
   * @return this new property with a different value
   */
  public Property<T> withValue(T newValue) {
    return with(valueToString(newValue));
  }

  /**
   * Returns the string representation of the properties value
   *
   * @return the string representation of the properties value
   */
  public String valueAsString() {
    return valueToString(value());
  }
  /**
   * Returns the string representation of the property's default value
   *
   * @return the string representation of the property's default value
   */
  public String defaultValueAsString() {
    return valueToString(defaultValue());
  }

  protected abstract String valueToString(T value);

  protected abstract T stringToValue(String string);

}
