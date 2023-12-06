package problem.day06;

import tools.InputFile;
import tools.Logger;
import java.util.List;

/**
 * Solution for the problem of Day 06
 * See description here: https://adventofcode.com/2023/day/6
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
    InputFile inputFile = new InputFile("problem06.input");
    if (!inputFile.exists()) {
      Logger.error("Input file not found");
      return;
    }

    List<Long> times = inputFile.readSpacedIntegerLine("Time:");
    List<Long> distances = inputFile.readSpacedIntegerLine("Distance:");
    if (times.size() != distances.size()) {
      throw new IllegalStateException("Time and distance arrays have different sizes");
    }

    long winningWayMultiplication = 0;
    for (int i = 0; i < times.size(); ++i) {
      long time = times.get(i);
      long distance = distances.get(i);
      long winningWays = calculateWinningWays(time, distance);
      Logger.info("    ==> " + winningWays);
      if (winningWayMultiplication > 0) {
        winningWayMultiplication *= winningWays;
      } else {
        winningWayMultiplication = winningWays;
      }
    }

    Logger.info("Answer for part 1: " + winningWayMultiplication);
  }

  private long calculateWinningWays(long maxTime, long recordDistance) {
    long winningWays = 0;

    for (long hold = 1; hold < maxTime; ++hold) {
      long distance = calculateDistance(hold, maxTime);
      Logger.info("Hold " + hold + " drive " + distance);
      if (distance > recordDistance) {
        winningWays++;
      }
    }

    return winningWays;
  }

  private long calculateDistance(long hold, long maxTime) {
    long driveTime = maxTime - hold;
    return hold * driveTime;
  }
}


