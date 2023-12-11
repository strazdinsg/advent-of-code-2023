package problem.day11;


import tools.InputFile;
import tools.Logger;
import tools.OutputFile;

/**
 * Solution for the problem of Day 11
 * See description here: https://adventofcode.com/2023/day/11
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
    map.expand();
    writeDebugOutput(map);
    map.findGalaxies();
    Logger.info("Sum of distances: " + map.getDistanceSum());
  }

  private static void writeDebugOutput(GalaxyMap map) {
    OutputFile outputFile = new OutputFile("galaxies.out");
    outputFile.writeGrid(map.getGrid());
    outputFile.close();
  }
}


