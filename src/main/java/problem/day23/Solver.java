package problem.day23;

import tools.CharArrayGrid;
import tools.InputFile;
import tools.Logger;

/**
 * Solution for the problem of Day 23
 * See description here: https://adventofcode.com/2023/day/23
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
    InputFile inputFile = new InputFile("problem23.input");
    if (!inputFile.exists()) {
      Logger.error("Input file not found");
      return;
    }

    CharArrayGrid grid = inputFile.readAllIntoCharGrid();
    Maze mazePart1 = new Maze(grid, false);
    mazePart1.findPaths();

    Maze mazePart2 = new Maze(grid, true);
    mazePart2.findPaths();

    long longestPath1 = mazePart1.getLongestPath();
    long longestPath2 = mazePart2.getLongestPath();
    Logger.info("Longest path for Part 1: " + longestPath1);
    Logger.info("Longest path for Part 2: " + longestPath2);

  }

}


