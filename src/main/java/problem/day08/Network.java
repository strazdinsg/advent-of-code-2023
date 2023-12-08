package problem.day08;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import tools.Logger;

/**
 * Represents a network of connected nodes.
 */
public class Network {
  private static final char LEFT = 'L';
  private final Map<String, Node> nodes = new HashMap<>();

  /**
   * Add a node to the network. If a node with the same name exists, it is replaced.
   *
   * @param node The node to add.
   */
  public void addNode(Node node) {
    nodes.put(node.name(), node);
  }

  /**
   * Move to the next node.
   *
   * @param node The current node where to start (where to move from)
   * @param direction   The direction to take
   * @return The destination node after making one move in the desired direction
   */
  public Node move(Node node, char direction) {
    String nextNodeName = direction == LEFT ? node.leftNodeName() : node.rightNodeName();
    Node nextNode = nodes.get(nextNodeName);
    if (nextNode == null) {
      throw new IllegalStateException("Node " + nextNodeName + " not found");
    }
    return nextNode;
  }

  /**
   * Get a list of all start nodes within the network.
   *
   * @return The list of all start nodes
   */
  public List<Node> getStartNodes() {
    List<Node> startNodes = new LinkedList<>();
    for (Node node : nodes.values()) {
      if (node.isStart()) {
        startNodes.add(node);
      }
    }
    return startNodes;
  }

  /**
   * Find the length of the path from a given node to a finish-node. We assume that after
   * reaching the finish node, there is another loop to reach a finish node, with the same length.
   * Therefore, we say "loop length". Strictly speaking, this would fail if the loop after
   * reaching the finish line is of a different length. But it was not the case in this task ;)
   *
   * @param node       The node where to start
   * @param directions A list of directions to take
   * @return The length of the loop from the start node to a finish node
   */
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
