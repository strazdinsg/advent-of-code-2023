package problem.day22;

import tools.InputFile;
import tools.Logger;
import tools.Vector3;
import java.util.List;

/**
 * Solution for the problem of Day 22
 * See description here: https://adventofcode.com/2023/day/22
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
    InputFile inputFile = new InputFile("problem22.input");
    if (!inputFile.exists()) {
      Logger.error("Input file not found");
      return;
    }

    List<String> lines = inputFile.readLinesUntilEmptyLine();
    Ground ground = new Ground();
    for (String line : lines) {
      Brick brick = parseBrick(line);
      ground.addBrick(brick);
    }
    ground.letBricksFall();
    int removableCount = ground.getRemovableBrickCount();
  }

  private Brick parseBrick(String line) {
    String[] parts = line.split("~");
    if (parts.length != 2) {
      throw new IllegalArgumentException("Invalid line format: " + line);
    }
    return new Brick(parseVector3(parts[0]), parseVector3(parts[1]));
  }

  private Vector3 parseVector3(String s) {
    String[] parts = s.split(",");
    if (parts.length != 3) {
      throw new IllegalArgumentException("Invalid line part format: " + s);
    }
    return new Vector3(
        Integer.parseInt(parts[0]),
        Integer.parseInt(parts[1]),
        Integer.parseInt(parts[2])
    );
  }

}


