package tools;

import java.util.ArrayList;
import java.util.List;

/**
 * An abstract base class for keeping a grid of characters.
 */
public abstract class CharacterGrid implements Cloneable {
  /**
   * Append a row to the grid.
   *
   * @param row The row as a string
   * @throws IllegalArgumentException If the number of characters in this row differs from
   *                                  column count of the previously added rows
   */
  public abstract void appendRow(String row) throws IllegalArgumentException;

  /**
   * Get the number of rows stored in the grid.
   *
   * @return The number of rows. Zero when grid is empty.
   */
  public abstract int getRowCount();

  /**
   * Get the number of columns in the grid.
   *
   * @return The number of columns, zero the grid is empty.
   */
  public abstract int getColumnCount();

  /**
   * Get the row at a specific index.
   *
   * @param rowIndex Index of the row to return. Indexing starts at zero.
   * @return The row
   * @throws IllegalArgumentException if the provided rowIndex is invalid
   */
  public abstract String getRow(int rowIndex) throws IllegalArgumentException;

  /**
   * Replace a character at a given row and given column with the given character c.
   *
   * @param rowIndex    The row of the character to replace
   * @param columnIndex The column of the character to replace
   * @param c           The replacement character
   * @throws IllegalArgumentException When the coordinates are outside the current grid
   */
  public abstract void setCharacter(int rowIndex, int columnIndex, char c)
      throws IllegalArgumentException;

  /**
   * Replace a row at the given place with a given string.
   *
   * @param rowIndex The index of the existing row to replace
   * @param row      The replacement row
   * @throws IllegalArgumentException When the row is outside the current grid boundaries
   */
  public abstract void replaceRow(int rowIndex, String row) throws IllegalArgumentException;

  /**
   * Get character at a specific row and column.
   *
   * @param rowIndex    Index of the row
   * @param columnIndex Index of the column
   * @return The character at the specified row and column
   * @throws IllegalArgumentException When row index of column index out of bounds
   */
  public abstract char getCharacter(int rowIndex, int columnIndex) throws IllegalArgumentException;

  public char getCharacter(Vector position) {
    return getCharacter(position.y(), position.x());
  }

  protected void checkRowValidity(String row) {
    if (row == null || row.isEmpty()) {
      throw new IllegalArgumentException("Can't add empty rows to grid");
    } else if (getColumnCount() > 0 && getColumnCount() != row.length()) {
      throw new IllegalArgumentException("Column count must be the same for all rows in the grid!");
    }
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

  /**
   * Get the whole column as a single string.
   *
   * @param columnIndex The index of the column to retrieve
   * @return Teh column as a string
   * @throws IllegalArgumentException When the column index is outside boundaries
   */
  public String getColumnAsString(int columnIndex) throws IllegalArgumentException {
    assertColumnWithinBoundaries(columnIndex);
    StringBuilder sb = new StringBuilder();
    for (int rowIndex = 0; rowIndex < getRowCount(); ++rowIndex) {
      sb.append(getCharacter(rowIndex, columnIndex));
    }
    return sb.toString();
  }

  protected void assertColumnWithinBoundaries(int columnIndex) {
    if (columnIndex < 0 || columnIndex >= getColumnCount()) {
      throw new IllegalArgumentException("Column outside the grid: " + columnIndex);
    }
  }

  protected void assertRowWithinBoundaries(int rowIndex) {
    if (rowIndex < 0 || rowIndex >= getRowCount()) {
      throw new IllegalArgumentException("Invalid row, outside the grid: " + rowIndex);
    }
  }

  /**
   * Print the grid to the console, for debugging.
   */
  public void debugLog() {
    for (int row = 0; row < getRowCount(); ++row) {
      Logger.info(getRow(row));
    }
  }

  public boolean isWithin(Vector position) {
    return position.y() >= 0 && position.y() < getRowCount()
        && position.x() >= 0 && position.x() < getColumnCount();
  }
}