package problem.day17;


import tools.InputFile;
import tools.Logger;

/**
 * Solution for the problem of Day 17
 * See description here: https://adventofcode.com/2023/day/17
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
    InputFile inputFile = new InputFile("problem17.input");
    if (!inputFile.exists()) {
      Logger.error("Input file not found");
      return;
    }

    // Part 2
    DirectionalMove.minMovesInOneDirection = 4;
    DirectionalMove.maxMovesInOneDirection = 10;

    Maze maze = new Maze(inputFile.readAllIntoCharGrid());
    Logger.info("Shortest path: " + maze.findShortestPath());
  }
}


