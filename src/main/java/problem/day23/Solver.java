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
    Maze maze = new Maze(grid, false);
    maze.findPaths();
    Logger.info("Longest path: " + maze.getLongestPath());
  }

}


