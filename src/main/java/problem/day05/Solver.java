package problem.day05;

import java.util.LinkedList;
import java.util.List;
import tools.InputFile;
import tools.IntegerRange;
import tools.Logger;

/**
 * Solution for the problem of Day 05
 * See description here: https://adventofcode.com/2023/day/5
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

    List<Long> seeds = processPartOne(inputFile);
    processPartTwo(seeds);
  }

  private List<Long> processPartOne(InputFile inputFile) {
    List<Long> seeds = inputFile.readSpacedIntegerLine("seeds: ");
    inputFile.skipEmptyLine();
    IntegerRangeMap map = null;
    for (int i = 0; i < MAP_COUNT; ++i) {
      IntegerRangeMap nextMap = readIntegerRangeMap(inputFile);
      if (map == null) {
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
    Logger.info("Closest location (Part 1): " + minLocation);
    return seeds;
  }


  private void processPartTwo(List<Long> seeds) {
    long minLocationSeedRanges = Long.MAX_VALUE;
    int rangeNumber = 1;
    List<IntegerRange> seedRanges = createSeedRanges(seeds);
    for (IntegerRange range : seedRanges) {
      Logger.info("Processing range " + rangeNumber++);
      for (long seed = range.getStart(); seed <= range.getEnd(); ++seed) {
        long seedLocation = headMap.findFinalMappingFor(seed);
        if (seedLocation < minLocationSeedRanges) {
          minLocationSeedRanges = seedLocation;
        }
      }
    }

    Logger.info("Closest location for seed ranges (Part 2): " + minLocationSeedRanges);
  }

  private IntegerRangeMap readIntegerRangeMap(InputFile inputFile) {
    IntegerRangeMap map = new IntegerRangeMap();

    inputFile.readLine(); // Skip title line

    List<Long> numbers;
    do {
      numbers = inputFile.readSpacedIntegerLine("");
      if (numbers.size() == 3) {
        map.addRange(numbers.get(1), numbers.get(0), numbers.get(2));
      } else if (!numbers.isEmpty()) {
        throw new IllegalStateException("Unexpected line format: " + numbers);
      }
    } while (!numbers.isEmpty());

    return map;
  }

  private List<IntegerRange> createSeedRanges(List<Long> seeds) {
    List<IntegerRange> ranges = new LinkedList<>();
    for (int i = 0; i < seeds.size(); i += 2) {
      long start = seeds.get(i);
      long length = seeds.get(i + 1);
      ranges.add(new IntegerRange(start, start + length - 1));
    }
    return ranges;
  }
}
