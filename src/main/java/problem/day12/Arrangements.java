package problem.day12;

import java.util.Arrays;

/**
 * Calculates spring pattern arrangements.
 */
public class Arrangements {

  private static final char BROKEN = '#';
  private static final char UNKNOWN = '?';
  private static final char EMPTY = '.';
  private static final int FOLD_COUNT = 5;
  private final String pattern;
  private final int[] patternSizes;
  private int[] rightmostPositions;

  private final PatternCountCache cache = new PatternCountCache();

  /**
   * Create an arrangement map.
   *
   * @param line The input line representing the arrangement pattern and spring sequence lengths
   */
  public Arrangements(String line) {
    String[] parts = line.split(" ");
    if (parts.length != 2) {
      throw new IllegalArgumentException("Invalid line format: " + line);
    }

    this.pattern = unfold(parts[0], "?");
    this.patternSizes = Arrays.stream(unfold(parts[1], ",").split(","))
        .mapToInt(Integer::parseInt)
        .toArray();
    calculateRightmostPositions();
  }

  private String unfold(String s, String separator) {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < FOLD_COUNT; ++i) {
      if (i > 0) {
        sb.append(separator);
      }
      sb.append(s);
    }
    return sb.toString();
  }

  private void calculateRightmostPositions() {
    rightmostPositions = new int[patternSizes.length];
    int rightMost = pattern.length();
    for (int i = patternSizes.length - 1; i >= 0; --i) {
      rightMost -= patternSizes[i];
      rightmostPositions[i] = rightMost;
      rightMost--;
    }
  }

  /**
   * Find the number of pattern counts for this arrangement.
   *
   * @return The number of different valid pattern counts
   */
  public long findCount() {
    return getPatternsStartingAt(0, 0, -1);
  }

  private long getPatternsStartingAt(int patternIndex, int startPosition, int prevEndPosition) {
    long patternCount = 0;
    for (int position = startPosition; position <= rightmostPositions[patternIndex]; position++) {
      if (canPlacePatternAt(patternIndex, position, prevEndPosition)) {
        Long patternsAtThisPosition = cache.load(patternIndex, position);
        if (patternsAtThisPosition == null) {
          patternsAtThisPosition = countPatternsStartingAt(patternIndex, position);
          if (patternsAtThisPosition > 0) {
            cache.store(patternIndex, position, patternsAtThisPosition);
          }
        }
        patternCount += patternsAtThisPosition;
      }
    }
    return patternCount;
  }

  private long countPatternsStartingAt(int patternIndex, int position) {
    long patternsAtThisPosition = 0;
    int endPosition = position + patternSizes[patternIndex] - 1;
    if (patternIndex < patternSizes.length - 1) {
      int nextPatternStartPosition = position + patternSizes[patternIndex] + 1;
      patternsAtThisPosition = getPatternsStartingAt(patternIndex + 1,
          nextPatternStartPosition, endPosition);
    } else {
      // This is the last pattern to check
      if (noBrokenPatternBetween(endPosition, pattern.length())) {
        patternsAtThisPosition = 1;
      }
    }
    return patternsAtThisPosition;
  }

  private boolean canBeSpaceAt(int position) {
    char c = pattern.charAt(position);
    return c == EMPTY || c == UNKNOWN;
  }

  private boolean canPlacePatternAt(int patternIndex, int position, int prevEndPosition) {
    boolean fits = true;
    int i = position;
    while (fits && i < position + patternSizes[patternIndex]) {
      fits = canBeDamaged(pattern.charAt(i));
      ++i;
    }

    // Check space after
    if (fits && patternIndex < patternSizes.length - 1) {
      fits = canBeSpaceAt(i);
    }

    // Check space before
    if (fits && position > 0) {
      fits = canBeSpaceAt(position - 1);
    }

    return fits && noBrokenPatternBetween(prevEndPosition, position);
  }

  private boolean noBrokenPatternBetween(int pos1, int pos2) {
    boolean noBroken = true;
    int position = pos1 + 1;
    while (noBroken && position < pos2) {
      noBroken = pattern.charAt(position) != BROKEN;
      ++position;
    }
    return noBroken;
  }

  private boolean canBeDamaged(char c) {
    return c == BROKEN || c == UNKNOWN;
  }

}
