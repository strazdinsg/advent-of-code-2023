package problem.day08;

import java.util.List;
import tools.Algos;
import tools.InputFile;
import tools.Logger;

/**
 * Solution for the problem of Day 08
 * See description here: https://adventofcode.com/2023/day/8
 */
public class Solver {

  /**
   * Run the solver - solve the puzzle.
   *
   * @param args Command line arguments, not used (enforced by Java).
   */
  public static void main(String[] args) {
    Logger.info("Starting...");
    Solver solver = new Solver();
    solver.solve();
  }

  private void solve() {
    InputFile inputFile = new InputFile("problem08.input");
    if (!inputFile.exists()) {
      Logger.error("Input file not found");
      return;
    }

    Directions directions = new Directions(inputFile.readLine());
    inputFile.skipEmptyLine();

    Network network = parseNetwork(inputFile);
    List<Node> startNodes = network.getStartNodes();
    if (startNodes.isEmpty()) {
      throw new IllegalStateException("Start nodes not found");
    }
    Logger.info(startNodes.size() + " start nodes");

    long commonLoopLength = 1;
    for (Node startNode : startNodes) {
      long loopLength = network.findLoopLength(startNode, directions);
      directions.reset();
      commonLoopLength = Algos.leastCommonMultiplier(commonLoopLength, loopLength);
      Logger.info("Path length after node " + startNode.name() + ": " + commonLoopLength);
    }

    Logger.info("Reached destination in " + commonLoopLength + " steps");
  }

  private Network parseNetwork(InputFile inputFile) {
    Network network = new Network();
    List<String> lines = inputFile.readLinesUntilEmptyLine();
    for (String line : lines) {
      network.addNode(parseNode(line));
    }
    return network;
  }

  private Node parseNode(String line) {
    if (line.length() != 16 || !line.startsWith(" = (", 3) || !line.startsWith(", ", 10)) {
      throw new IllegalArgumentException("Wrong line format: " + line);
    }

    String nodeName = line.substring(0, 3);
    String leftNodeName = line.substring(7, 10);
    String rightNodeName = line.substring(12, 15);

    return new Node(nodeName, leftNodeName, rightNodeName);
  }
}


