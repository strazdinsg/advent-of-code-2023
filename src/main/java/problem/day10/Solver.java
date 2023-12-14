package problem.day10;


import tools.InputFile;
import tools.Logger;
import tools.OutputFile;
import tools.Vector;

/**
 * Solution for the problem of Day 10
 * See description here: https://adventofcode.com/2023/day/10
 * Idea: create a grid twice as big as the original one. When finding the loop, fill in
 * walls in the intermediate cells.
 * Then remove mark all the non-loop cells as empty.
 * Then "flood water" from all the boundary cells. The cells that are left empty, are inside
 * the loop.
 */
public class Solver {
  DoubleSizeMaze maze;

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
    InputFile inputFile = new InputFile("problem10.input");
    if (!inputFile.exists()) {
      Logger.error("Input file not found");
      return;
    }

    maze = new DoubleSizeMaze(inputFile.readAllIntoStringGrid());
    Vector start = maze.findStartPosition();
    Logger.info("Start position: " + start);
    maze.findLoop(start);
    long steps = maze.getLoopLength() / 2;
    Logger.info("The furthest point is " + steps + " steps away");

    maze.markAllNonLoopCellsAsEmpty();
    maze.floodWaterFromOutside();
    long innerCellCount = maze.countDryInnerCells();
    Logger.info("Number of dry inner cells: " + innerCellCount);

    OutputFile outputFile = new OutputFile("maze.out");
    outputFile.writeGrid(maze.toGrid());
    outputFile.close();
  }
}


