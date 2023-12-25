package problem.day25;

import java.util.List;
import java.util.Set;
import tools.InputFile;
import tools.Logger;
import tools.graph.BidirectionalGraph;
import tools.graph.DotWriter;

/**
 * Solution for the problem of Day 25
 * See description here: https://adventofcode.com/2023/day/25
 */
public class Solver {
  private final BidirectionalGraph graph = new BidirectionalGraph();

  // Here we cheat a bit - from the SVG visualization we found that vertex vvs belongs
  // to the first cluster while fgn belongs to the other. Therefore, we specify the source
  // and target vertices manually here for the max-flow / min-cut algorithm.
  private static final String SOURCE = "vvs";
  private static final String TARGET = "fgn";

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

    Set<String> sourceCluster = graph.findStartCluster(SOURCE, TARGET);
    Logger.info("Source cluster vertices: " + sourceCluster.size());
    Set<String> targetCluster = graph.verticesExcept(sourceCluster);
    Logger.info("Terminating cluster vertices: " + targetCluster.size());
    if (sourceCluster.size() + targetCluster.size() != graph.getVertexCount()) {
      throw new IllegalArgumentException("Some vertices missing in the clusters!");
    }
    Logger.info("Cluster sizes multiplied: " + sourceCluster.size() * targetCluster.size());

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
      graph.addBidirectionalEdge(u, v);
    }
  }
}


