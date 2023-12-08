package problem.day08;

import tools.Logger;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Network {
  private final static char LEFT = 'L';
  private final Map<String, Node> nodes = new HashMap<>();

  public void addNode(Node node) {
    nodes.put(node.name(), node);
  }

  public Node move(Node currentNode, char direction) {
    String nextNodeName = direction == LEFT ?
        currentNode.leftNodeName() : currentNode.rightNodeName();
    Node nextNode = nodes.get(nextNodeName);
    if (nextNode == null) {
      throw new IllegalStateException("Node " + nextNodeName + " not found");
    }
    return nextNode;
  }

  public List<Node> getStartNodes() {
    List<Node> startNodes = new LinkedList<>();
    for (Node node : nodes.values()) {
      if (node.isStart()) {
        startNodes.add(node);
      }
    }
    return startNodes;
  }

  public long findLoopLength(Node node, Directions directions) {
    String startNodeName = node.name();
    long steps = 0;
    while (!node.isFinish()) {
      steps++;
      node = move(node, directions.getNext());
    }
    Logger.info(node.name() + " reached from " + startNodeName + " in " + steps + " steps");
    return steps;
  }
}
