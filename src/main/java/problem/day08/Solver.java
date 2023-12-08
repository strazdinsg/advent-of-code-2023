package problem.day08;

import tools.InputFile;
import tools.Logger;
import java.util.List;

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

    Network network = new Network();
    List<String> lines = inputFile.readLinesUntilEmptyLine();
    for (String line : lines) {
      network.addNode(parseNode(line));
    }

    Node currentNode = network.getStartNode();
    if (currentNode == null) {
      throw new IllegalStateException("Start node not found");
    }

    int steps = 0;
    while (!currentNode.isFinish()) {
      Direction direction = directions.getNext();
      currentNode = network.move(currentNode, direction);
      steps++;
      Logger.info(direction + " " + steps + " " + currentNode.name());
    }

    Logger.info("Reached destination in " + steps + " steps");
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


