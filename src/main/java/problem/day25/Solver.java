package problem.day25;

import java.util.List;
import tools.InputFile;
import tools.Logger;
import tools.graph.DotWriter;
import tools.graph.Graph;

/**
 * Solution for the problem of Day 25
 * See description here: https://adventofcode.com/2023/day/25
 */
public class Solver {
  private final Graph graph = new Graph(true);

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
    InputFile inputFile = new InputFile("problem25.input");
    if (!inputFile.exists()) {
      Logger.error("Input file not found");
      return;
    }

    List<String> lines = inputFile.readLinesUntilEmptyLine();
    for (String line : lines) {
      parseEdges(line);
    }

    DotWriter.write(graph, "problem25.dot");
  }

  private void parseEdges(String line) {
    String[] parts = line.split(": ");
    if (parts.length != 2 || parts[0].isEmpty() || parts[1].isEmpty()) {
      throw new IllegalArgumentException("Invalid line format: " + line);
    }
    String u = parts[0];
    String[] destinations = parts[1].split(" ");
    for (String v : destinations) {
      graph.addBidirectionalEdge(u, v, 1);
    }
  }
}


