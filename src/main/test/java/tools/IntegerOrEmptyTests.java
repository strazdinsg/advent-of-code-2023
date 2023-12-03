package tools;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class IntegerOrEmptyTests {
  @Test
  void testFromString() {
    expectEmpty(null);
    expectEmpty("");
    expectEmpty(" ");
    expectEmpty("abc");
    expectEmpty("abc 12");
    expectNumber(13, "13");
    expectNumber(0, "0");
    expectNumber(-1, "-1");
    expectNumber(-668, "-668");
    expectNumber(123456789012345678L, "123456789012345678");
    expectNumber(-123456789012345678L, "-123456789012345678");
  }

  private void expectNumber(long expectedValue, String value) {
    IntegerOrEmpty i = IntegerOrEmpty.fromString(value);
    assertNotNull(i);
    assertTrue(i.isNumber());
    assertEquals(expectedValue, i.getValue());
  }

  private void expectEmpty(String value) {
    IntegerOrEmpty i = IntegerOrEmpty.fromString(value);
    assertNotNull(i);
    assertNull(i.getValue());
    assertFalse(i.isNumber());
  }
}
