package problem.day19;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import tools.IntegerRange;

/**
 * Keep a list of allowed combinations for all the xmas properties.
 */
public class Combinations {
  private static final long MIN_VALUE = 1;
  private static final long MAX_VALUE = 4000;
  private final Map<Character, IntegerRange> propertyRanges = new HashMap<>();

  /**
   * Don't allow direct instantiation.
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

  private static IntegerRange createWholeRange() {
    return new IntegerRange(MIN_VALUE, MAX_VALUE);
  }

  /**
   * Create an empty combination collection - no possible combinations.
   *
   * @return Empty combinations collection
   */
  public static Combinations empty() {
    Combinations combinations = new Combinations();
    combinations.propertyRanges.put('x', null);
    combinations.propertyRanges.put('m', null);
    combinations.propertyRanges.put('a', null);
    combinations.propertyRanges.put('s', null);
    return combinations;
  }

  /**
   * Get the total number of combinations of all the allowed property ranges.
   *
   * @return The total number of combinations.
   */
  public long count() {
    long count = 1;
    for (Character property : propertyRanges.keySet()) {
      count *= getCountFor(property);
    }
    return count;
  }

  private long getCountFor(Character property) {
    IntegerRange range = propertyRanges.get(property);
    return range != null ? range.getLength() : 0;
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
      char property = condition.property();
      IntegerRange range = c.propertyRanges.get(property);
      c.propertyRanges.put(property, condition.applyTo(range));
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
    Iterator<IntegerRange> it = propertyRanges.values().iterator();
    while (!empty && it.hasNext()) {
      IntegerRange range = it.next();
      empty = range == null;
    }
    return empty;
  }

  /**
   * Add the given combinations to this object.
   *
   * @param c The combinations to add
   */
  public void add(Combinations c) {
    for (Character property : c.propertyRanges.keySet()) {
      propertyRanges.put(property, c.propertyRanges.get(property).createCopy());
    }
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    final String props = "xmas";
    for (int i = 0; i < props.length(); ++i) {
      char property = props.charAt(i);
      sb.append(property);
      sb.append(": ");
      sb.append(propertyRanges.get(property));
      sb.append(" ");
    }
    return sb.toString();
  }
}
