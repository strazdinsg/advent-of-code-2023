package tools;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Unit tests for IntegerRange.
 */
class IntegerRangeTest {
  @Test
  void testContainment() {
    checkContains(1, 10, 2, 8, true);
    checkContains(2, 8, 1, 10, false);
    checkContains(1, 10, 1, 10, true);
    checkContains(1, 10, 1, 2, true);
    checkContains(1, 10, 9, 10, true);
    checkContains(1, 10, 1, 1, true);
    checkContains(1, 10, 10, 10, true);
    checkContains(1, 10, 8, 11, false);
    checkContains(1, 10, -2, 4, false);
    checkContains(-10, 10, -2, 4, true);
    checkContains(1, 10, 12, 14, false);
  }

  private void checkContains(int firstRangeStart, int firstRangeEnd,
                             int secondRangeStart, int secondRangeEnd, boolean shouldContain) {
    IntegerRange first = new IntegerRange(firstRangeStart, firstRangeEnd);
    IntegerRange second = new IntegerRange(secondRangeStart, secondRangeEnd);
    assertEquals(shouldContain, first.containsFully(second));
  }

  @Test
  void testOverlap() {
    checkOverlap(1, 3, 5, 7, false);
    checkOverlap(1, 5, 5, 7, true);
    checkOverlap(1, 5, 4, 7, true);
    checkOverlap(1, 7, 5, 7, true);
    checkOverlap(1, 9, 5, 7, true);
    checkOverlap(1, 9, -2, 7, true);
    checkOverlap(1, 9, -2, 12, true);
    checkOverlap(1, 9, -2, 0, false);
  }

  private void checkOverlap(int firstRangeStart, int firstRangeEnd,
                            int secondRangeStart, int secondRangeEnd, boolean shouldOverlap) {
    IntegerRange first = new IntegerRange(firstRangeStart, firstRangeEnd);
    IntegerRange second = new IntegerRange(secondRangeStart, secondRangeEnd);
    assertEquals(shouldOverlap, first.overlapsWith(second));
  }

  @Test
  void testMerge() {
    assertThrows(IllegalArgumentException.class, () -> {
      checkMerge(1, 3, 5, 7, 0, 0);
    });
    assertThrows(IllegalArgumentException.class, () -> {
      checkMerge(1, 9, -2, 0, 0, 0);
    });

    checkMerge(1, 5, 5, 7, 1, 7);
    checkMerge(1, 5, 4, 7, 1, 7);
    checkMerge(1, 7, 5, 7, 1, 7);
    checkMerge(1, 9, 5, 7, 1, 9);
    checkMerge(1, 9, -2, 7, -2, 9);
    checkMerge(1, 9, -2, 12, -2, 12);
  }

  private void checkMerge(int firstRangeStart, int firstRangeEnd,
                          int secondRangeStart, int secondRangeEnd,
                          int expectedResultStart, int expectedResultEnd) {
    IntegerRange first = new IntegerRange(firstRangeStart, firstRangeEnd);
    IntegerRange second = new IntegerRange(secondRangeStart, secondRangeEnd);
    IntegerRange result = first.mergeWith(second);
    assertEquals(new IntegerRange(expectedResultStart, expectedResultEnd), result);
  }
}

