package problem.day05;

import tools.InputFile;
import tools.Logger;
import java.util.List;

/**
 * Solution for the problem of Day 04
 * See description here: https://adventofcode.com/2023/day/4
 */
public class Solver {
  private IntegerRangeMap headMap = null;

  private static final int MAP_COUNT = 7;

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
    InputFile inputFile = new InputFile("problem05.input");
    if (!inputFile.exists()) {
      Logger.error("Input file not found");
      return;
    }

    List<Long> seeds = inputFile.readSpacedIntegerLine("seeds: ");
    inputFile.skipEmptyLine();

    IntegerRangeMap map = null;
    for (int i = 0; i < MAP_COUNT; ++i) {
      IntegerRangeMap nextMap = readIntegerRangeMap(inputFile);
      if (headMap == null) {
        headMap = nextMap;
      } else {
        map.setLinkedMap(nextMap);
      }
      map = nextMap;
    }

    long minLocation = Long.MAX_VALUE;
    for (Long seed : seeds) {
      long seedLocation = headMap.findFinalMappingFor(seed);
      if (seedLocation < minLocation) {
        minLocation = seedLocation;
      }
    }

    Logger.info("Closest location: " + minLocation);
  }

  private IntegerRangeMap readIntegerRangeMap(InputFile inputFile) {
    IntegerRangeMap map = new IntegerRangeMap();

    inputFile.readLine(); // Skip title line

    List<Long> numbers;
    do {
      numbers = inputFile.readSpacedIntegerLine("");
      if (numbers.size() == 3) {
        map.addRange(numbers.get(1), numbers.get(0), numbers.get(2));
      } else if (!numbers.isEmpty()){
        throw new IllegalStateException("Unexpected line format: " + numbers);
      }
    } while (!numbers.isEmpty());

    return map;
  }

}
