package problem.day18;


import tools.Direction;
import tools.InputFile;
import tools.Logger;
import tools.Vector;
import java.util.LinkedList;
import java.util.List;

/**
 * Solution for the problem of Day 18
 * See description here: https://adventofcode.com/2023/day/18
 */
public class Solver {
  private static final boolean PART2 = false;
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
    Painting painting = new Painting();
    painting.add(edges);
    Logger.info("Total size: " + painting.findInteriorArea());
  }

  private Edge parseEdge(String line) {
    PaintCommand command = parseCommand(line);
    Vector endPosition = cursor.plus(command.toVector());
    Edge edge = new Edge(cursor, endPosition);
    cursor = endPosition;
    return edge;
  }

  private PaintCommand parseCommand(String line) {
    String[] parts = line.split(" ");
    if (parts.length != 3 || parts[0].length() != 1 || parts[2].length() != 9) {
      throw new IllegalArgumentException("Invalid line format: " + line);
    }

    Direction direction;
    int distance;
    if (PART2) {
      direction = parseDirection(parts[2].charAt(7));
      distance = parseDistance(parts[2].substring(2, 7));
    } else {
      direction = Direction.fromRelative(parts[0].charAt(0));
      distance = Integer.parseInt(parts[1]);
    }
    return new PaintCommand(direction, distance);
  }

  private int parseDistance(String hexDistance) {
    return Integer.parseInt(hexDistance, 16);
  }

  private Direction parseDirection(char d) {
    return switch (d) {
      case '0' -> Direction.EAST;
      case '1' -> Direction.SOUTH;
      case '2' -> Direction.WEST;
      case '3' -> Direction.NORTH;
      default -> throw new IllegalArgumentException("Invalid direction: " + d);
    };
  }
}


