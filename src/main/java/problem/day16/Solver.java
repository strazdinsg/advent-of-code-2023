package problem.day16;


import tools.CharArrayGrid;
import tools.Direction;
import tools.InputFile;
import tools.Logger;
import tools.Vector;

/**
 * Solution for the problem of Day 16
 * See description here: https://adventofcode.com/2023/day/16
 */
public class Solver {
  private long maxEnergy;
  private CharArrayGrid grid;

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

    grid = inputFile.readAllIntoCharGrid();
    maxEnergy = 0;
    for (int row = 0; row < grid.getRowCount(); ++row) {
      updateMaxEnergy(row, 0, Direction.EAST);
      updateMaxEnergy(row, grid.getColumnCount() - 1, Direction.WEST);
    }
    for (int column = 0; column < grid.getColumnCount(); ++column) {
      updateMaxEnergy(0, column, Direction.SOUTH);
      updateMaxEnergy(grid.getRowCount() - 1, column, Direction.NORTH);
    }
    Logger.info("Max energized tile count: " + maxEnergy);
  }

  private void updateMaxEnergy(int row, int column, Direction direction) {
    Cave maze = new Cave(grid);
    LightBeam enteringLight = new LightBeam(new Vector(column, row), direction);
    maze.bounceLight(enteringLight);
    long energy = maze.getEnergizedTileCount();
    if (energy > maxEnergy) {
      maxEnergy = energy;
    }
  }
}


