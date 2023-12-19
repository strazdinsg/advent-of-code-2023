package problem.day19;

public record Operation(Condition condition, String destination) {
  @Override
  public String toString() {
    return condition != null ? (condition + " -> " + destination) : destination;
  }

  public String process(Part part) {
    return condition == null || condition.matches(part) ? destination : null;
  }
}
