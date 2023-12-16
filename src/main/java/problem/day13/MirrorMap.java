package problem.day13;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import tools.CharArrayGrid;
import tools.CharacterGrid;
import tools.Logger;
import tools.OutputFile;
import tools.StringGrid;
import tools.Vector;

/**
 * A mirror map containing some rocks on it.
 */
public class MirrorMap {
  private static final char ROCK = '#';
  private static final char EMPTY = '.';
  private static final int MAX_BITS = 20;
  private static final char DELETED_ROCK = 'o';
  private static final char FAKED_ROCK = 'X';
  private final CharArrayGrid grid;
  private long[] rows;
  private long[] columns;
  private static final Set<Long> bits = new HashSet<>();
  private static final Map<Long, Integer> bitPositions = new HashMap<>();

  /**
   * Create a mirror map.
   *
   * @param grid The grid representing the map
   */
  public MirrorMap(CharArrayGrid grid) {
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
      bitPositions.put(bit, i);
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
  public int findSymmetryCenter(long[] numbers, boolean expectSmudge, boolean isRow) {
    int symmetryCenter = 0;
    int i = 1;
    while (symmetryCenter == 0 && i < numbers.length) {
      if (isSymmetry(numbers, i, expectSmudge, isRow)) {
        symmetryCenter = i;
        if (expectSmudge) {
          debugPrint(symmetryCenter, isRow);
        }
      }
      i++;
    }
    return symmetryCenter;
  }

  private boolean isSymmetry(long[] numbers, int split, boolean smudgeExpected, boolean isRow) {
    int numbersToTheRight = numbers.length - split;
    int length = Math.min(split, numbersToTheRight);
    boolean symmetrical = true;
    boolean wasSmudgeExpected = smudgeExpected;
    int i = 1;
    while (symmetrical && i <= length) {
      long leftNumber = numbers[split - i];
      long rightNumber = numbers[split + i - 1];
      symmetrical = (leftNumber == rightNumber);
      if (!symmetrical && smudgeExpected) {
        Integer smudgedBitPosition = findSmudgedBitPosition(leftNumber, rightNumber);
        if (smudgedBitPosition != null) {
          flipBitInGrid(split - i, smudgedBitPosition, isRow);
          // Smudge found, ignore it once, remember that this bit was smudged
          symmetrical = true;
          smudgeExpected = false;
        }
      }
      i++;
    }
    if (!symmetrical && wasSmudgeExpected && !smudgeExpected) {
      revertSmudgedBits();
    }
    return symmetrical && !smudgeExpected;
  }

  private Integer findSmudgedBitPosition(long n1, long n2) {
    long difference = Math.abs(n2 - n1);
    return bitPositions.get(difference);
  }

  private void flipBitInGrid(int index, int smudgedBitPosition, boolean isRow) {
    int row;
    int column;
    if (isRow) {
      row = index;
      column = grid.getColumnCount() - smudgedBitPosition - 1;
    } else {
      row = grid.getRowCount() - smudgedBitPosition - 1;
      column = index;
    }
    char bit = grid.getCharacter(row, column);
    char flipped = getFlippedChar(bit);
    grid.setCharacter(row, column, flipped);
    Logger.info(" Flipping " + bit + " @ (" + row + "," + column + ") to " + flipped);
  }

  private char getFlippedChar(char bit) {
    return switch (bit) {
      case ROCK -> DELETED_ROCK;
      case EMPTY -> FAKED_ROCK;
      default -> throw new IllegalArgumentException("Invalid char to flip: " + bit);
    };
  }

  private void revertSmudgedBits() {
    List<Vector> deletedRocks = grid.findCharacterLocations(DELETED_ROCK);
    for (Vector fakeRock : deletedRocks) {
      grid.setCharacter(fakeRock.y(), fakeRock.x(), ROCK);
    }
    List<Vector> fakeRocks = grid.findCharacterLocations(FAKED_ROCK);
    for (Vector fakeRock : fakeRocks) {
      grid.setCharacter(fakeRock.y(), fakeRock.x(), EMPTY);
    }
  }

  /**
   * Find the symmetry score of this mirror map.
   *
   * @param expectSmudge Whether to expect exactly one smudged bit
   * @return The symmetry score of this map
   */
  public long findSymmetryScore(boolean expectSmudge) {
    long score;
    int symmetry = findSymmetryCenter(columns, expectSmudge, false);
    if (symmetry > 0) {
      score = symmetry;
    } else {
      symmetry = findSymmetryCenter(rows, expectSmudge, true);
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

  private void debugPrint(int symmetryCenter, boolean isRow) {
    CharacterGrid extGrid = grid.cloneInExpandedGrid();
    if (isRow) {
      extGrid.setCharacter(symmetryCenter, 0, 'v');
      extGrid.setCharacter(symmetryCenter + 1, 0, '^');
      extGrid.setCharacter(symmetryCenter, extGrid.getColumnCount() - 1, 'v');
      extGrid.setCharacter(symmetryCenter + 1, extGrid.getColumnCount() - 1, '^');
    } else {
      extGrid.setCharacter(0, symmetryCenter, '>');
      extGrid.setCharacter(0, symmetryCenter + 1, '<');
      extGrid.setCharacter(extGrid.getRowCount() - 1, symmetryCenter, '>');
      extGrid.setCharacter(extGrid.getRowCount() - 1, symmetryCenter + 1, '<');
    }
    extGrid.debugLog();
  }
}
