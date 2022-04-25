package oogasalad.engine.model;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

import oogasalad.engine.model.ai.timeLimiting.TimeLimit;
import oogasalad.engine.model.ai.timeLimiting.UnlimitedTime;
import org.junit.jupiter.api.Test;

class UnlimitedTimeTest {
  TimeLimit timeLimit = new UnlimitedTime();

  @Test
  void start() {
    assertDoesNotThrow(() -> timeLimit.start());
  }

  @Test
  void isTimeUp() { // Can't explain why this test fails, JUnit says it returned <java.lang.Boolean> but the method signature is boolean so idek
    assertDoesNotThrow(() -> timeLimit.isTimeUp());
    assertInstanceOf(Boolean.class, timeLimit.isTimeUp());
    assertFalse(timeLimit.isTimeUp());
  }
}