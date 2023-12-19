package tools;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class NoOverlappingRangesTest {
  @Test
  void testMergingTwo() {
    NonOverlappingRanges ranges = new NonOverlappingRanges();

    ranges.add(new IntegerRange(-2, 14));
    assertEquals(1, ranges.getRangeCount());
    assertEquals(new IntegerRange(-2, 14), ranges.getRange(0));

    ranges.add(new IntegerRange(16, 24));
    assertEquals(2, ranges.getRangeCount());
    assertEquals(new IntegerRange(-2, 14), ranges.getRange(0));
    assertEquals(new IntegerRange(16, 24), ranges.getRange(1));

    ranges.add(new IntegerRange(10, 18));
    assertEquals(1, ranges.getRangeCount());
    assertEquals(new IntegerRange(-2, 24), ranges.getRange(0));
  }

  @Test
  void testMergingAll() {
    NonOverlappingRanges ranges = createFourNonOverlappingRanges();
    ranges.add(new IntegerRange(10, 51));
    assertEquals(1, ranges.getRangeCount());
    assertEquals(new IntegerRange(-2, 80), ranges.getRange(0));
  }

  @Test
  void testOverlappingAll() {
    NonOverlappingRanges ranges = createFourNonOverlappingRanges();
    ranges.add(new IntegerRange(-10, 150));
    assertEquals(1, ranges.getRangeCount());
    assertEquals(new IntegerRange(-10, 150), ranges.getRange(0));
  }

  @Test
  void testOverlappingOneMergingWithOne() {
    NonOverlappingRanges ranges = createFourNonOverlappingRanges();
    ranges.add(new IntegerRange(10, 30));

    assertEquals(3, ranges.getRangeCount());
    assertEquals(new IntegerRange(-2, 30), ranges.getRange(0));
    assertEquals(new IntegerRange(34, 40), ranges.getRange(1));
    assertEquals(new IntegerRange(50, 80), ranges.getRange(2));

    ranges = createFourNonOverlappingRanges();
    ranges.add(new IntegerRange(16, 35));

    assertEquals(3, ranges.getRangeCount());
    assertEquals(new IntegerRange(-2, 14), ranges.getRange(0));
    assertEquals(new IntegerRange(16, 40), ranges.getRange(1));
    assertEquals(new IntegerRange(50, 80), ranges.getRange(2));
  }

  @Test
  void testNoOverlap() {
    NonOverlappingRanges ranges = createFourNonOverlappingRanges();
    ranges.add(new IntegerRange(100, 130));

    assertEquals(5, ranges.getRangeCount());
    assertEquals(new IntegerRange(-2, 14), ranges.getRange(0));
    assertEquals(new IntegerRange(18, 24), ranges.getRange(1));
    assertEquals(new IntegerRange(34, 40), ranges.getRange(2));
    assertEquals(new IntegerRange(50, 80), ranges.getRange(3));
    assertEquals(new IntegerRange(100, 130), ranges.getRange(4));
  }

  @Test
  void testGetSwallowed() {
    NonOverlappingRanges ranges = new NonOverlappingRanges();
    ranges.add(new IntegerRange(2, 14));
    ranges.add(new IntegerRange(2, 2));
    assertEquals(1, ranges.getRangeCount());
    assertEquals(new IntegerRange(2, 14), ranges.getRange(0));
  }


  private static NonOverlappingRanges createFourNonOverlappingRanges() {
    NonOverlappingRanges ranges = new NonOverlappingRanges();

    ranges.add(new IntegerRange(-2, 14));
    ranges.add(new IntegerRange(18, 24));
    ranges.add(new IntegerRange(34, 40));
    ranges.add(new IntegerRange(50, 80));
    assertEquals(4, ranges.getRangeCount());
    assertEquals(new IntegerRange(-2, 14), ranges.getRange(0));
    assertEquals(new IntegerRange(18, 24), ranges.getRange(1));
    assertEquals(new IntegerRange(34, 40), ranges.getRange(2));
    assertEquals(new IntegerRange(50, 80), ranges.getRange(3));
    return ranges;
  }

  @Test
  void testRemoveValue() {
    NonOverlappingRanges ranges = new NonOverlappingRanges();
    ranges.add(new IntegerRange(1, 10));
    ranges.removeSingleValue(6);
    assertEquals(2, ranges.getRangeCount());
    assertEquals(new IntegerRange(1, 5), ranges.getRange(0));
    assertEquals(new IntegerRange(7, 10), ranges.getRange(1));
  }
}
