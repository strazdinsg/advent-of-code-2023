package problem.day11;


import tools.InputFile;
import tools.Logger;

/**
 * Solution for the problem of Day 11
 * See description here: https://adventofcode.com/2023/day/11
 * Idea: load the galaxy map, find empty columns and empty rows.
 * Distance between each two galaxies goes horizontally and vertically. Each step
 * is treated as a long distance if it goes over an empty column or row; as a
 * short distance if it goes over a regular column or row.
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
    InputFile inputFile = new InputFile("problem11.input");
    if (!inputFile.exists()) {
      Logger.error("Input file not found");
      return;
    }

    GalaxyMap map = new GalaxyMap(inputFile.readAllIntoGridBuffer());
    map.findExpandedDistances();
    map.findGalaxies();
    Logger.info("Sum of distances: " + map.calculateDistances());
  }
}


