package problem.day08;

import java.util.HashMap;
import java.util.Map;

public class Network {
  private final Map<String, Node> nodes = new HashMap<>();
  private Node startNode = null;

  public Node getStartNode() {
    return startNode;
  }

  public void addNode(Node node) {
    nodes.put(node.name(), node);
    if (node.isStart()) {
      startNode = node;
    }
  }

  public Node move(Node currentNode, Direction direction) {
    String nextNodeName = direction == Direction.LEFT ?
        currentNode.leftNodeName() : currentNode.rightNodeName();
    Node nextNode = nodes.get(nextNodeName);
    if (nextNode == null) {
      throw new IllegalStateException("Node " + nextNodeName + " not found");
    }
    return nextNode;
  }
}
