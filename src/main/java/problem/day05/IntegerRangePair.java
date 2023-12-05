package problem.day05;

public class IntegerRangePair {

  private final long delta;
  private final long firstStart;
  private final long secondStart;
  private final long length;

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

  public long getMappingFor(long seed) {
    long mapping = -1;
    if (seed >= firstStart && seed <= getFirstEnd()) {
      mapping = seed + delta;
    }
    return mapping;
  }
}
