package problem.day14;


import tools.CharArrayGrid;
import tools.InputFile;
import tools.Logger;

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
    for (int i = 0; i < 1000; ++i) {
      platform.spinOneCycle();
      long load = platform.getTotalVerticalLoad();
      Logger.info(i + ": " + load);
    }
    // Note: here I "cheated" a bit: I printed out the load numbers and found that they
    // appear in a cycle. The cycle length is 11 elements. Element number 1000 turned out to
    // be the same as element number 1000000000, which is the right answer
  }

}


