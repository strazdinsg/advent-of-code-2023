package problem.day13;

import tools.StringGrid;

public class MirrorMap {
  private static final char ROCK = '#';
  private final StringGrid grid;
  private long[] rows;
  private long[] columns;

  public MirrorMap(StringGrid grid) {
    this.grid = grid;
    convertRowsToNumbers();
    convertColumnsToNumbers();
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
      long bit = s.charAt(i) == ROCK? 1 : 0;
      n = (n << 1) + bit;
    }
    return n;
  }

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
      if (!symmetrical && smudgeExpected) {
        // Smudge found, ignore it once
        symmetrical = true;
        smudgeExpected = false;
      }
      i++;
    }
    return symmetrical && !smudgeExpected;
  }

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
