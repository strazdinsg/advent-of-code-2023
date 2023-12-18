package problem.day13;

import tools.CharacterGrid;
import tools.Logger;

/**
 * A mirror map containing some rocks on it.
 */
public class MirrorMap {
  private String[] rows;
  private String[] columns;

  /**
   * Create a mirror map.
   *
   * @param grid The grid representing the map
   */
  public MirrorMap(CharacterGrid grid) {
    initializeRows(grid);
    initializeColumns(grid);
  }

  private void initializeColumns(CharacterGrid grid) {
    columns = new String[grid.getColumnCount()];
    for (int j = 0; j < grid.getColumnCount(); ++j) {
      columns[j] = grid.getColumnAsString(j);
    }
  }

  private void initializeRows(CharacterGrid grid) {
    rows = new String[grid.getRowCount()];
    for (int i = 0; i < grid.getRowCount(); ++i) {
      rows[i] = grid.getRow(i);
    }
  }

  /**
   * Find the symmetry center of a given string array.
   *
   * @param strings      The strings to check
   * @param expectSmudge Whether to expect exactly one smudged bit in the numbers
   * @return The symmetry center i, meaning that numbers i-1 and i are the same, i-2 and i+1, etc
   */
  public int findSymmetryCenter(String[] strings, boolean expectSmudge) {
    int symmetryCenter = 0;
    int i = 1;
    while (symmetryCenter == 0 && i < strings.length) {
      if (isSymmetry(strings, i, expectSmudge)) {
        symmetryCenter = i;
      }
      i++;
    }
    return symmetryCenter;
  }

  private boolean isSymmetry(String[] strings, int split, boolean smudgeExpected) {
    int numbersToTheRight = strings.length - split;
    int length = Math.min(split, numbersToTheRight);
    boolean symmetrical = true;
    int i = 1;
    while (symmetrical && i <= length) {
      String left = strings[split - i];
      String right = strings[split + i - 1];
      int bitDifferences = findDifference(left, right);
      symmetrical = bitDifferences == 0;
      if (!symmetrical && smudgeExpected && bitDifferences == 1) {
        // Smudge found, ignore it once, remember that this bit was smudged
        symmetrical = true;
        smudgeExpected = false;
      }
      i++;
    }
    return symmetrical && !smudgeExpected;
  }

  private int findDifference(String left, String right) {
    int difference = 0;
    for (int i = 0; i < left.length(); ++i) {
      if (left.charAt(i) != right.charAt(i)) {
        difference++;
      }
    }
    return difference;
  }

  /**
   * Find the symmetry score of this mirror map.
   *
   * @param expectSmudge Whether to expect exactly one smudged bit
   * @return The symmetry score of this map
   */
  public long findSymmetryScore(boolean expectSmudge) {
    long score;
    int symmetry = findSymmetryCenter(columns, expectSmudge);
    if (symmetry > 0) {
      score = symmetry;
    } else {
      symmetry = findSymmetryCenter(rows, expectSmudge);
      if (symmetry == 0) {
        throw new IllegalStateException("A map has neither symmetrical rows, nor columns");
      }
      score = 100L * symmetry;
    }
    if (expectSmudge) {
      Logger.info(" Symmetry: " + score);
      Logger.info("");
    }
    return score;
  }
}
