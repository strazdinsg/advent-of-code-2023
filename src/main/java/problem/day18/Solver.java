package problem.day18;


import tools.*;
import java.util.LinkedList;
import java.util.List;

/**
 * Solution for the problem of Day 18
 * See description here: https://adventofcode.com/2023/day/18
 */
public class Solver {
  Vector cursor;

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
    InputFile inputFile = new InputFile("problem18.input");
    if (!inputFile.exists()) {
      Logger.error("Input file not found");
      return;
    }

    List<String> lines = inputFile.readLinesUntilEmptyLine();
    List<Edge> edges = new LinkedList<>();
    cursor = new Vector(0, 0);
    for (String line : lines) {
      edges.add(parseEdge(line));
    }
    Rectangle boundaries = findBoundaries(edges);
    Painting painting = new Painting(boundaries);
    painting.paint(edges);
    debugPrint(painting);
    painting.findInteriorArea();
    Logger.info("Total size: " + painting.getTotalSize());
  }

  private Rectangle findBoundaries(List<Edge> edges) {
    Rectangle boundaries = new Rectangle(0, 0, 0, 0);
    for (Edge edge : edges) {
      boundaries = boundaries.extend(edge.end());
    }
    return boundaries;
  }

  private Edge parseEdge(String line) {
    PaintCommand command = parseCommand(line);
    Vector endPosition = cursor.plus(command.toVector());
    Edge edge = new Edge(cursor, endPosition, command.color());
    cursor = endPosition;
    return edge;
  }

  private PaintCommand parseCommand(String line) {
    String[] parts = line.split(" ");
    if (parts.length != 3 || parts[0].length() != 1 || parts[2].length() != 9) {
      throw new IllegalArgumentException("Invalid line format: " + line);
    }

    char dir = parts[0].charAt(0);
    int distance = Integer.parseInt(parts[1]);
    String color = parts[2].substring(2, 8);
    return new PaintCommand(Direction.fromRelative(dir), distance, new Color(color));
  }

  private void debugPrint(Painting painting) {
    CharArrayGrid grid = painting.createDebugGrid();
    OutputFile outputFile = new OutputFile("painting.out");
    outputFile.writeGrid(grid);
    outputFile.close();
  }
}


