package problem.day19;

import tools.IntegerRange;
import tools.NonOverlappingRanges;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Combinations {
  private static final long MIN_VALUE = 1;
  private static final long MAX_VALUE = 4000;
  private final Map<Character, NonOverlappingRanges> propertyRanges = new HashMap<>();

  /**
   * Don't allow direct instantiation
   */
  private Combinations() {

  }

  /**
   * Create all the possible combinations.
   *
   * @return list of all allowed combinations, for all properties - the starting point of Part 2
   */
  public static Combinations all() {
    Combinations combinations = new Combinations();
    combinations.propertyRanges.put('x', createWholeRange());
    combinations.propertyRanges.put('m', createWholeRange());
    combinations.propertyRanges.put('a', createWholeRange());
    combinations.propertyRanges.put('s', createWholeRange());
    return combinations;
  }

  private static NonOverlappingRanges createWholeRange() {
    NonOverlappingRanges ranges = new NonOverlappingRanges();
    ranges.add(new IntegerRange(MIN_VALUE, MAX_VALUE));
    return ranges;
  }

  public static Combinations empty() {
    Combinations combinations = new Combinations();
    combinations.propertyRanges.put('x', createEmptyRange());
    combinations.propertyRanges.put('m', createEmptyRange());
    combinations.propertyRanges.put('a', createEmptyRange());
    combinations.propertyRanges.put('s', createEmptyRange());
    return combinations;
  }

  private static NonOverlappingRanges createEmptyRange() {
    return new NonOverlappingRanges();
  }

  public long count() {
    long count = 1;
    for (Character property : propertyRanges.keySet()) {
      count *= getCountFor(property);
    }
    return count;
  }

  private long getCountFor(Character property) {
    NonOverlappingRanges ranges = propertyRanges.get(property);
    return ranges.getTotalLength();
  }

  /**
   * Apply the given operation and consequentially shrink the combination ranges.
   *
   * @param condition The condition to apply
   * @return A copy of this combination with the corresponding property ranges shrank
   */
  public Combinations apply(Condition condition) {
    Combinations c = this.createCopy();
    if (condition != null) {
      for (var entry : c.propertyRanges.entrySet()) {
        char property = entry.getKey();
        NonOverlappingRanges ranges = entry.getValue();
        condition.applyTo(property, ranges);
      }
    }
    return c;
  }

  public Combinations applyReverse(Condition condition) {
    return apply(condition != null ? condition.reverse() : null);
  }

  private Combinations createCopy() {
    Combinations copy = new Combinations();
    for (var entry : propertyRanges.entrySet()) {
      copy.propertyRanges.put(entry.getKey(), entry.getValue().createCopy());
    }
    return copy;
  }

  /**
   * Checks whether some property ranges are empty.
   *
   * @return True if at least one property range is empty, false if all properties have at
   *     least one valid value range
   */
  public boolean hasSomeEmptyRanges() {
    boolean empty = false;
    Iterator<NonOverlappingRanges> it = propertyRanges.values().iterator();
    while (!empty && it.hasNext()) {
      NonOverlappingRanges ranges = it.next();
      empty = ranges.isEmpty();
    }
    return empty;
  }

  public void add(Combinations c) {
    for (var entry : propertyRanges.entrySet()) {
      Character property = entry.getKey();
      NonOverlappingRanges ranges = entry.getValue();
      ranges.addAll(c.getRangesFor(property));
    }
  }

  private NonOverlappingRanges getRangesFor(Character property) {
    return propertyRanges.get(property);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    final String props = "xmas";
    for (int i = 0; i < props.length(); ++i) {
      char property = props.charAt(i);
      sb.append(property);
      sb.append(": ");
      sb.append(getRangesFor(property));
      sb.append(" ");
    }
    return sb.toString();
  }
}
