package problem.day19;

/**
 * An operation to apply to the parts.
 *
 * @param condition   The condition to check
 * @param destination The name of the destination workflow which should be used when the
 *                    condition is met
 */
public record Operation(Condition condition, String destination) {
  @Override
  public String toString() {
    return condition != null ? (condition + " -> " + destination) : destination;
  }

  public String process(Part part) {
    return condition == null || condition.matches(part) ? destination : null;
  }
}
