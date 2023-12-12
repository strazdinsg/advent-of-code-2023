package problem.day12;


import tools.InputFile;
import tools.Logger;
import java.util.List;

/**
 * Solution for the problem of Day 12
 * See description here: https://adventofcode.com/2023/day/12
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
    InputFile inputFile = new InputFile("problem12.input");
    if (!inputFile.exists()) {
      Logger.error("Input file not found");
      return;
    }

    long arrangementCountSum = 0;
    List<String> lines = inputFile.readLinesUntilEmptyLine();
    for (String line : lines) {
      Arrangements arrangements = new Arrangements(line);
      Logger.info("");
      arrangementCountSum += arrangements.findCount();
    }

    Logger.info("Sum of arrangement counts: " + arrangementCountSum);
  }
}


