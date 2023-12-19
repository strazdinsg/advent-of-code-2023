package problem.day19;

import tools.IntegerRange;

/**
 * A condition to check.
 *
 * @param property   The property to check
 * @param comparison The comparison type (greater or less than)
 * @param threshold  The threshold value
 */
public record Condition(char property, Comparison comparison, int threshold) {
  @Override
  public String toString() {
    return "" + property + (comparison == Comparison.GREATER_THAN ? '>' : '<') + threshold;
  }

  public boolean matches(Part part) {
    int value = part.getPropertyValue(property);
    return comparison == Comparison.GREATER_THAN ? value > threshold : value < threshold;
  }

  /**
   * Apply the condition to allowed ranges of a given property.
   *
   * @param range The range to constrain with this condition
   */
  public IntegerRange applyTo(IntegerRange range) {
    IntegerRange newRange = null;
    if (comparison == Comparison.GREATER_THAN) {
      if (range.getEnd() > threshold) {
        long start = Math.max(threshold + 1, range.getStart());
        newRange = new IntegerRange(start, range.getEnd());
      }
    } else if (range.getStart() < threshold) {
      long end = Math.min(threshold - 1, range.getEnd());
      newRange = new IntegerRange(range.getStart(), end);
    }
    return newRange;
  }

  /**
   * Create an opposite condition.
   *
   * @return A new condition, opposite to this one
   */
  public Condition reverse() {
    Comparison comp;
    int t;
    if (comparison == Comparison.GREATER_THAN) {
      comp = Comparison.LESS_THAN;
      t = threshold + 1;
    } else {
      comp = Comparison.GREATER_THAN;
      t = threshold - 1;
    }
    return new Condition(property, comp, t);
  }
}
