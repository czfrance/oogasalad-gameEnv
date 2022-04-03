package oogasalad.builder.model.property;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

/**
 * Test for String Property Class
 *
 * @author Shaan Gondalia
 */
public class PropertyTest {

  private static final String PROPERTY_NAME = "propName";
  private static final String PROPERTY_VALUE = "value";

  private static final String EXPECTED_JSON = "{\"propName\":\"value\"}";


  @Test
  void testCreateProperty() {
    Property property = new Property(PROPERTY_NAME, PROPERTY_VALUE);
    assertEquals(PROPERTY_NAME, property.name());
    assertEquals(PROPERTY_VALUE, property.value());
    assertEquals(PROPERTY_VALUE, property.toString());
  }

  @Test
  void testEquality() {
    Property property1 = new Property(PROPERTY_NAME, PROPERTY_VALUE);
    Property property2 = new Property(PROPERTY_NAME, PROPERTY_VALUE);
    assertEquals(property1, property2);
  }

  @Test
  void testHashCode() {
    Property property = new Property(PROPERTY_NAME, PROPERTY_VALUE);
    int code = property.hashCode();
  }
}
