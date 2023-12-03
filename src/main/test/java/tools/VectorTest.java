package tools;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class VectorTest {
  @Test
  public void testZero() {
    assertTrue(new Vector(0, 0).isZero());
    assertFalse(new Vector(1, 0).isZero());
    assertFalse(new Vector(0, 1).isZero());
    assertFalse(new Vector(-2, 2).isZero());
  }

  @Test
  public void testScaling() {
    Vector zero = new Vector(0, 0);
    Vector scaled = zero.scaleToOneUnit();
    assertEquals(scaled, zero);

    assertEquals(new Vector(1, 0), new Vector(3, 0).scaleToOneUnit());
    assertEquals(new Vector(1, 0), new Vector(20, 0).scaleToOneUnit());
    assertEquals(new Vector(0, 1), new Vector(0, 2).scaleToOneUnit());
    assertEquals(new Vector(-1, 0), new Vector(-3, 0).scaleToOneUnit());
    assertEquals(new Vector(0, -1), new Vector(0, -5).scaleToOneUnit());
    assertEquals(new Vector(1, 1), new Vector(3, 5).scaleToOneUnit());
    assertEquals(new Vector(-1, 1), new Vector(-3, 5).scaleToOneUnit());
  }

  @Test
  public void testAdding() {
    assertEquals(new Vector(5, 3), new Vector(2, 1).plus(new Vector(3, 2)));
    assertEquals(new Vector(5, 3), new Vector(5, 3).plus(new Vector(0, 0)));
    assertEquals(new Vector(-5, -3), new Vector(2, 4).plus(new Vector(-7, -7)));
  }

  @Test
  public void testAbsoluteValues() {
    assertEquals(3, new Vector(-3, -2).getAbsoluteX());
    assertEquals(2, new Vector(-3, -2).getAbsoluteY());
    assertEquals(3, new Vector(3, -2).getAbsoluteX());
    assertEquals(2, new Vector(-3, 2).getAbsoluteY());
  }
}
