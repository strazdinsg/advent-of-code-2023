package problem.day21;

import tools.CharArrayGrid;
import tools.InputFile;
import tools.Logger;

/**
 * Solution for the problem of Day 21
 * See description here: https://adventofcode.com/2023/day/21
 */
public class Solver {

  private static final int STEP_COUNT = 64;

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
    InputFile inputFile = new InputFile("problem21.input");
    if (!inputFile.exists()) {
      Logger.error("Input file not found");
      return;
    }

    CharArrayGrid grid = inputFile.readAllIntoCharGrid();
    GardenMap map = new GardenMap(grid);
    long spotCount = map.countReachableSpots(STEP_COUNT);
    Logger.info("After " + STEP_COUNT + " steps the elf can visit " + spotCount + " spots");
  }

}


