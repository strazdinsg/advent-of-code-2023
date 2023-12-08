package problem.day08;

public record Node(String name, String leftNodeName, String rightNodeName) {
  private static final String FINISH_NODE_NAME = "ZZZ";
  private static final String START_NODE_NAME = "AAA";

  public boolean isFinish() {
    return name.equals(FINISH_NODE_NAME);
  }

  public boolean isStart() {
    return name.equals(START_NODE_NAME);
  }
}
