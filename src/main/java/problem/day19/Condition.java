package problem.day19;

public record Condition(char property, Comparison comparison, int threshold) {
  @Override
  public String toString() {
    return "" + property + (comparison == Comparison.GREATER_THAN ? '>' : '<') + threshold;
  }

  public boolean matches(Part part) {
    int value = part.getPropertyValue(property);
    return comparison == Comparison.GREATER_THAN ? value > threshold : value < threshold;
  }
}
