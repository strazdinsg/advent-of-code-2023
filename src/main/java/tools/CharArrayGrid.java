package tools;

import java.util.ArrayList;
import java.util.List;

/**
 * A grid which stores the characters as a two-dimensional char-array internally.
 * Each character can be both accessed and replaced in constant time.
 * Returning a whole row takes linear time.
 */
public class CharArrayGrid extends CharacterGrid {
  private final List<CharArray> chars = new ArrayList<>();

  /**
   * Create a char array grid.
   *
   * @param rows    The number of rows
   * @param columns The number of columns
   * @param fill    The character to use for the fill.
   * @return The grid with desired dimensions, filled with the specified character
   */
  public static CharArrayGrid createWithFill(int rows, int columns, char fill) {
    CharArrayGrid grid = new CharArrayGrid();
    String emptyRow = ("" + fill).repeat(columns);
    for (int row = 0; row < rows; ++row) {
      grid.chars.add(new CharArray(emptyRow));
    }
    return grid;
  }

  @Override
  public void appendRow(String row) throws IllegalArgumentException {
    chars.add(new CharArray(row));
  }

  @Override
  public int getRowCount() {
    return chars.size();
  }

  @Override
  public int getColumnCount() {
    return chars.isEmpty() ? 0 : chars.get(0).length();
  }

  @Override
  public String getRow(int rowIndex) throws IllegalArgumentException {
    assertRowWithinBoundaries(rowIndex);
    return chars.get(rowIndex).toString();
  }

  @Override
  public void setCharacter(int rowIndex, int columnIndex, char c) throws IllegalArgumentException {
    assertRowWithinBoundaries(rowIndex);
    assertColumnWithinBoundaries(columnIndex);
    chars.get(rowIndex).update(columnIndex, c);
  }

  @Override
  public void replaceRow(int rowIndex, String row) throws IllegalArgumentException {
    assertRowWithinBoundaries(rowIndex);
    chars.set(rowIndex, new CharArray(row));
  }

  @Override
  public char getCharacter(int rowIndex, int columnIndex) throws IllegalArgumentException {
    assertRowWithinBoundaries(rowIndex);
    assertColumnWithinBoundaries(columnIndex);
    return chars.get(rowIndex).get(columnIndex);
  }

  /**
   * Create an expanded grid: an "almost clone" of this grid, with the only difference that the
   * extended grid has one empty row and column around this one, with empty spaces.
   *
   * @return An expanded grid.
   */
  public CharArrayGrid cloneInExpandedGrid() {
    CharArrayGrid expandedGrid = new CharArrayGrid();
    String emptyRow = " ".repeat(getColumnCount() + 2);
    expandedGrid.appendRow(emptyRow);
    for (int row = 0; row < getRowCount(); ++row) {
      String expandedRow = " " + getRow(row) + " ";
      expandedGrid.appendRow(expandedRow);
    }
    expandedGrid.appendRow(emptyRow);
    return expandedGrid;
  }
}
