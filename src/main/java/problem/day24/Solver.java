package problem.day24;

import java.util.ArrayList;
import java.util.List;
import tools.InputFile;
import tools.Logger;
import tools.Vector3;
import tools.VectorF;

/**
 * Solution for the problem of Day 24
 * See description here: https://adventofcode.com/2023/day/24
 */
public class Solver {

  private static final long MIN_BOUNDARY = 200000000000000L;
  private static final long MAX_BOUNDARY = 400000000000000L;

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
    InputFile inputFile = new InputFile("problem24.input");
    if (!inputFile.exists()) {
      Logger.error("Input file not found");
      return;
    }

    List<String> inputLines = inputFile.readLinesUntilEmptyLine();
    List<HailStone> hailStones = new ArrayList<>();
    for (String inputLine : inputLines) {
      hailStones.add(parseLine(inputLine));
    }
    int intersections = 0;
    for (int i = 0; i < hailStones.size(); ++i) {
      for (int j = i + 1; j < hailStones.size(); ++j) {
        VectorF intersection = hailStones.get(i).intersectsInFuture(hailStones.get(j));
        if (intersection != null) {
          if (isWithinBoundaries(intersection)) {
            Logger.info("Lines " + i + " and " + j + " intersect at " + intersection);
            intersections++;
          } else {
            Logger.info("Out of bounds: lines " + i + " and " + j + " intersect at "
                + intersection);
          }
        }
      }
    }
    Logger.info(intersections + " intersections within the boundaries");
  }

  private HailStone parseLine(String inputLine) {
    String[] parts = inputLine.split(" @ ");
    if (parts.length != 2) {
      throw new IllegalArgumentException("Invalid line format: " + inputLine);
    }
    Vector3 initialCoordinates = parseThreeLongs(parts[0]);
    Vector3 direction = parseThreeLongs(parts[1]);
    return HailStone.create(initialCoordinates, direction);
  }

  private Vector3 parseThreeLongs(String s) {
    String[] parts = s.split(", ");
    if (parts.length != 3) {
      throw new IllegalArgumentException("Invalid integer string format: " + s);
    }
    return new Vector3(
        Long.parseLong(parts[0]),
        Long.parseLong(parts[1]),
        Long.parseLong(parts[2])
    );
  }

  private boolean isWithinBoundaries(VectorF intersection) {
    return intersection != null
        && intersection.x() >= MIN_BOUNDARY && intersection.x() <= MAX_BOUNDARY
        && intersection.y() >= MIN_BOUNDARY && intersection.y() <= MAX_BOUNDARY;
  }
}


