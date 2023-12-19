package problem.day19;

import tools.NonOverlappingRanges;

public record Condition(char property, Comparison comparison, int threshold) {
  @Override
  public String toString() {
    return "" + property + (comparison == Comparison.GREATER_THAN ? '>' : '<') + threshold;
  }

  public boolean matches(Part part) {
    int value = part.getPropertyValue(property);
    return comparison == Comparison.GREATER_THAN ? value > threshold : value < threshold;
  }

  public void applyTo(char prop, NonOverlappingRanges ranges) {
    if (prop == property) {
      if (comparison == Comparison.GREATER_THAN) {
        ranges.removeAllLowerThan(threshold + 1);
      } else {
        ranges.removeAllGreaterThan(threshold - 1);
      }
    }
  }

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
