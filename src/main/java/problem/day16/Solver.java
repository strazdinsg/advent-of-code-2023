package problem.day16;


import tools.CharArrayGrid;
import tools.InputFile;
import tools.Logger;

/**
 * Solution for the problem of Day 16
 * See description here: https://adventofcode.com/2023/day/16
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
    InputFile inputFile = new InputFile("problem16.input");
    if (!inputFile.exists()) {
      Logger.error("Input file not found");
      return;
    }

    CharArrayGrid grid = inputFile.readAllIntoCharGrid();
    Cave maze = new Cave(grid);
    maze.bounceLight();
    Logger.info("Energized tile count: " + maze.getEnergizedTileCount());
  }
}


