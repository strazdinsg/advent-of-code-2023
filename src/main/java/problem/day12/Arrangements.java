package problem.day12;

import tools.Logger;
import java.util.Arrays;

public class Arrangements {

  private static final char BROKEN = '#';
  private static final char UNKNOWN = '?';
  private static final char EMPTY = '.';
  private final String pattern;
  private final int[] patternSizes;
  private int[] rightmostPositions;

  public Arrangements(String line) {
    String[] parts = line.split(" ");
    if (parts.length != 2) {
      throw new IllegalArgumentException("Invalid line format: " + line);
    }

    this.pattern = parts[0];
    this.patternSizes = Arrays.stream(parts[1].split(","))
        .mapToInt(Integer::parseInt)
        .toArray();
    calculateRightmostPositions();
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

  public long findCount() {
    return countPatternsStartingAt(0, 0, -1);
  }

  private long countPatternsStartingAt(int patternIndex, int startPosition, int prevEndPosition) {
    long patternCount = 0;
    for (int position = startPosition; position <= rightmostPositions[patternIndex]; position++) {
      if (canPlacePatternAt(patternIndex, position, prevEndPosition)) {
        int endPosition = position + patternSizes[patternIndex] - 1;
        if (patternIndex < patternSizes.length - 1) {
          int nextPatternStartPosition = position + patternSizes[patternIndex] + 1;
          Logger.info(patternIndex + " at " + position);
          patternCount += countPatternsStartingAt(patternIndex + 1,
              nextPatternStartPosition, endPosition);
        } else {
          // This is the last pattern to check
          if (noBrokenPatternBetween(endPosition, pattern.length())) {
            patternCount++;
            Logger.info(patternIndex + " at " + position + " == last");
          }
        }
      }
    }
    return patternCount;
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
