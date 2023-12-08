package problem.day08;

/**
 * Represents a node in the network.
 *
 * @param name          A unique name of the node
 * @param leftNodeName  Name of the node which can be reached when moving left from this node
 * @param rightNodeName Name of the node which can be reached when moving right from this node
 */
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
