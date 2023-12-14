package problem.day14;


import tools.CharArrayGrid;
import tools.InputFile;
import tools.Logger;
import tools.OutputFile;

/**
 * Solution for the problem of Day 14
 * See description here: https://adventofcode.com/2023/day/14
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
    InputFile inputFile = new InputFile("problem14.input");
    if (!inputFile.exists()) {
      Logger.error("Input file not found");
      return;
    }

    CharArrayGrid grid = inputFile.readAllIntoCharGrid();
    Platform platform = new Platform(grid);
    platform.tiltNorth();
    Logger.info("Total load: " + platform.getTotalVerticalLoad());
  }

}


