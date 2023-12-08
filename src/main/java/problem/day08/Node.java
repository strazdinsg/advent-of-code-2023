package problem.day08;

public record Node(String name, String leftNodeName, String rightNodeName) {
  private static final String FINISH_POSTFIX = "Z";
  private static final String START_POSTFIX = "A";

  public boolean isFinish() {
    return name.endsWith(FINISH_POSTFIX);
  }

  public boolean isStart() {
    return name.endsWith(START_POSTFIX);
  }
}
