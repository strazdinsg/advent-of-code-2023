package problem.day13;

import java.util.HashSet;
import java.util.Set;
import tools.StringGrid;

/**
 * A mirror map containing some rocks on it.
 */
public class MirrorMap {
  private static final char ROCK = '#';
  private static final int MAX_BITS = 20;
  private final StringGrid grid;
  private long[] rows;
  private long[] columns;
  private static final Set<Long> bits = new HashSet<>();

  /**
   * Create a mirror map.
   *
   * @param grid The grid representing the map
   */
  public MirrorMap(StringGrid grid) {
    this.grid = grid;
    convertRowsToNumbers();
    convertColumnsToNumbers();
    if (bits.isEmpty()) {
      initializeBits();
    }
  }

  private void convertColumnsToNumbers() {
    columns = new long[grid.getColumnCount()];
    for (int j = 0; j < grid.getColumnCount(); ++j) {
      columns[j] = convertToNumber(grid.getColumnAsString(j));
    }
  }

  private void convertRowsToNumbers() {
    rows = new long[grid.getRowCount()];
    for (int i = 0; i < grid.getRowCount(); ++i) {
      rows[i] = convertToNumber(grid.getRow(i));
    }
  }

  private long convertToNumber(String s) {
    long n = 0;
    for (int i = 0; i < s.length(); ++i) {
      long bit = s.charAt(i) == ROCK ? 1 : 0;
      n = (n << 1) + bit;
    }
    return n;
  }

  private void initializeBits() {
    long bit = 1;
    for (int i = 0; i < MAX_BITS; ++i) {
      bits.add(bit);
      bit <<= 1;
    }
  }

  /**
   * Find the symmetry center of a given number array.
   *
   * @param numbers      The array to check
   * @param expectSmudge Whether to expect exactly one smudged bit in the numbers
   * @return The symmetry center i, meaning that numbers i-1 and i are the same, i-2 and i+1, etc
   */
  public long findSymmetryCenter(long[] numbers, boolean expectSmudge) {
    long symmetryCenter = 0;
    int i = 1;
    while (symmetryCenter == 0 && i < numbers.length) {
      if (isSymmetry(numbers, i, expectSmudge)) {
        symmetryCenter = i;
      }
      i++;
    }
    return symmetryCenter;
  }

  private boolean isSymmetry(long[] numbers, int split, boolean smudgeExpected) {
    int numbersToTheRight = numbers.length - split;
    int length = Math.min(split, numbersToTheRight);
    boolean symmetrical = true;
    int i = 1;
    while (symmetrical && i <= length) {
      long leftNumber = numbers[split - i];
      long rightNumber = numbers[split + i - 1];
      symmetrical = (leftNumber == rightNumber);
      if (!symmetrical && smudgeExpected && onlyOneBitDifference(leftNumber, rightNumber)) {
        // Smudge found, ignore it once, remember that this bit was smudged
        symmetrical = true;
        smudgeExpected = false;
      }
      i++;
    }
    return symmetrical && !smudgeExpected;
  }

  /**
   * Returns true if numbers n1 and n2 differ only by a single bit.
   *
   * @param n1 The first number
   * @param n2 The second number
   * @return True if n1 and n2 differ only by a single bit
   */
  private boolean onlyOneBitDifference(long n1, long n2) {
    return bits.contains(Math.abs(n2 - n1));
  }

  /**
   * Find the symmetry score of this mirror map.
   *
   * @param expectSmudge Whether to expect exactly one smudged bit
   * @return The symmetry score of this map
   */
  public long findSymmetryScore(boolean expectSmudge) {
    long score;
    long symmetry = findSymmetryCenter(columns, expectSmudge);
    if (symmetry > 0) {
      score = symmetry;
    } else {
      symmetry = findSymmetryCenter(rows, expectSmudge);
      if (symmetry == 0) {
        throw new IllegalStateException("A map has neither symmetrical rows, nor columns");
      }
      score = 100L * symmetry;
    }
    return score;
  }
}
