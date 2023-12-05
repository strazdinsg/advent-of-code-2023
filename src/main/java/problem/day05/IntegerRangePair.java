package problem.day05;

/**
 * A pair of integer ranges.
 */
public class IntegerRangePair {

  private final long delta;
  private final long firstStart;
  private final long secondStart;
  private final long length;

  /**
   * Create an integer range pair.
   *
   * @param firstStart  The start of the first range
   * @param secondStart The start of the second range
   * @param length      The length of both ranges
   */
  public IntegerRangePair(long firstStart, long secondStart, long length) {
    this.firstStart = firstStart;
    this.secondStart = secondStart;
    this.length = length;
    this.delta = secondStart - firstStart;
  }

  private long getFirstEnd() {
    return firstStart + length - 1;
  }

  private long getSecondEnd() {
    return secondStart + length - 1;
  }

  @Override
  public String toString() {
    return "[" + firstStart + "-" + getFirstEnd()
        + "]->[" + secondStart + "-" + getSecondEnd() + "]";
  }

  /**
   * Find the given seed inside the range and map it to a corresponding value in the second range.
   *
   * @param seed The seed to map
   * @return The mapped value or -1 if the seed does not fit within the first range
   */
  public long getMappingFor(long seed) {
    long mapping = -1;
    if (seed >= firstStart && seed <= getFirstEnd()) {
      mapping = seed + delta;
    }
    return mapping;
  }
}
