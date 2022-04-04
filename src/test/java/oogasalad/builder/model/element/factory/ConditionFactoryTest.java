package oogasalad.builder.model.element.factory;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collection;
import java.util.HashSet;
import oogasalad.builder.controller.Property;
import oogasalad.builder.model.element.Condition;
import oogasalad.builder.model.element.ElementRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for ConditionFactory Class
 *
 * @author Shaan Gondalia
 */
public class ConditionFactoryTest {

  private ConditionFactory conditionFactory;
  private static final String CONDITION_NAME = "emptyTopRight";
  private static final String PROPERTY_NAME_TYPE = "type";
  private static final String CONDITION_TYPE = "emptyAt";
  private static final String PROPERTY_NAME_ONE = "x";
  private static final String PROPERTY_VALUE_ONE = "1";
  private static final String PROPERTY_NAME_TWO = "y";
  private static final String PROPERTY_VALUE_TWO = "1";

  @BeforeEach
  void setUp(){
    conditionFactory = new ConditionFactory();
  }

  @Test
  void testConditionCreated() {
    Collection<Property> properties = new HashSet<>();
    properties.add(new Property(Integer.class, PROPERTY_NAME_TYPE, CONDITION_TYPE));
    properties.add(new Property(Integer.class, PROPERTY_NAME_ONE, PROPERTY_VALUE_ONE));
    properties.add(new Property(Integer.class, PROPERTY_NAME_TWO, PROPERTY_VALUE_TWO));
    Condition condition = conditionFactory.createElement(CONDITION_NAME, properties);

    ElementRecord record = condition.toRecord();
    assertEquals(properties, record.properties());
    assertEquals(CONDITION_NAME, record.name());
  }

}