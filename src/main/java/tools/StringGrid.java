package tools;

import java.util.ArrayList;
import java.util.List;

/**
 * A grid holding each row as a string of the sam length.
 */
public class StringGrid {
  private final List<String> rows = new ArrayList<>();
  private int columnCount = 0;

  /**
   * Append a row to the grid.
   *
   * @param row The row as a string
   * @throws IllegalArgumentException If the number of characters in this row differs from
   *                                  column count of the previously added rows
   */
  public void appendRow(String row) throws IllegalArgumentException {
    if (row == null || row.isEmpty()) {
      throw new IllegalArgumentException("Can't add empty rows to grid");
    } else if (!rows.isEmpty() && row.length() != columnCount) {
      throw new IllegalArgumentException("Column count must be the same for all rows in the grid!");
    }

    columnCount = row.length();
    rows.add(row);
  }

  /**
   * Get the number of rows stored in the grid.
   *
   * @return The number of rows. Zero when grid is empty.
   */
  public int getRowCount() {
    return rows.size();
  }

  /**
   * Get the number of columns in the grid.
   *
   * @return The number of columns, zero the grid is empty.
   */
  public int getColumnCount() {
    return columnCount;
  }

  /**
   * Get the row at a specific index.
   *
   * @param rowIndex Index of the row to return. Indexing starts at zero.
   * @return The row
   * @throws IllegalArgumentException if the provided rowIndex is invalid
   */
  public String getRow(int rowIndex) throws IllegalArgumentException {
    if (rowIndex < 0 || rowIndex >= getRowCount()) {
      throw new IllegalArgumentException("Row outside the grid: " + rowIndex);
    }
    return rows.get(rowIndex);
  }

  /**
   * Replace a character at a given row and given column with the given character c.
   *
   * @param rowIndex    The row of the character to replace
   * @param columnIndex The column of the character to replace
   * @param c           The replacement character
   * @throws IllegalArgumentException When the coordinates are outside the current grid
   */
  public void replaceCharacter(int rowIndex, int columnIndex, char c)
      throws IllegalArgumentException {
    assertColumnWithinBoundaries(columnIndex);

    String row = getRow(rowIndex);
    replaceRow(rowIndex, replaceCharAt(row, columnIndex, c));
  }

  private void assertColumnWithinBoundaries(int columnIndex) {
    if (columnIndex < 0 || columnIndex >= getColumnCount()) {
      throw new IllegalArgumentException("Column outside the grid: " + columnIndex);
    }
  }

  private static String replaceCharAt(String s, int i, char c) {
    return s.substring(0, i) + c + s.substring(i + 1);
  }

  /**
   * Replace a row at the given place with a given string.
   *
   * @param rowIndex The index of the existing row to replace
   * @param row      The replacement row
   * @throws IllegalArgumentException When the row is outside the current grid boundaries
   */
  public void replaceRow(int rowIndex, String row) throws IllegalArgumentException {
    assertRowWithinBoundaries(rowIndex);
    rows.set(rowIndex, row);
  }

  private void assertRowWithinBoundaries(int rowIndex) {
    if (rowIndex < 0 || rowIndex >= getRowCount()) {
      throw new IllegalArgumentException("Invalid row, outside the grid: " + rowIndex);
    }
  }

  /**
   * Get character at a specific row and column.
   *
   * @param rowIndex    Index of the row
   * @param columnIndex Index of the column
   * @return The character at the specified row and column
   * @throws IllegalArgumentException When row index of column index out of bounds
   */
  public char getCharacter(int rowIndex, int columnIndex) throws IllegalArgumentException {
    assertColumnWithinBoundaries(columnIndex);
    String row = getRow(rowIndex);
    return row.charAt(columnIndex);
  }

  public char getCharacter(Vector position) {
    return getCharacter(position.y(), position.x());
  }

  /**
   * Find locations of all instances of a particular character.
   *
   * @param c The character to find.
   * @return All the locations of the character instances
   */
  public List<Vector> findCharacterLocations(char c) {
    List<Vector> positions = new ArrayList<>();
    for (int row = 0; row < getRowCount(); row++) {
      for (int column = 0; column < getColumnCount(); column++) {
        if (getCharacter(row, column) == c) {
          positions.add(new Vector(column, row));
        }
      }
    }
    return positions;
  }
}
