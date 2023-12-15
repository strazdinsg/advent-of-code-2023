package problem.day13;


import tools.CharArrayGrid;
import tools.InputFile;
import tools.Logger;
import tools.StringGrid;

/**
 * Solution for the problem of Day 13
 * See description here: https://adventofcode.com/2023/day/13
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
    InputFile inputFile = new InputFile("problem13.input");
    if (!inputFile.exists()) {
      Logger.error("Input file not found");
      return;
    }

    long sum = 0;
    long fixedSum = 0;
    int i = 1;
    while (!inputFile.isEndOfFile()) {
      Logger.info("Grid " + i);
      CharArrayGrid grid = inputFile.readAllIntoCharGrid();
      MirrorMap map = new MirrorMap(grid);
      sum += map.findSymmetryScore(false);
      fixedSum += map.findSymmetryScore(true);
      i++;
    }
    Logger.info("Symmetry sum: " + sum);
    Logger.info("Fixed symmetry sum: " + fixedSum);
  }
}


