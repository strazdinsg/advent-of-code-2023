package tools;

import java.util.ArrayList;
import java.util.List;

/**
 * A grid holding each row as a string of the sam length.
 */
public class StringGrid extends CharacterGrid {
  private final List<String> rows = new ArrayList<>();
  private int columnCount = 0;

  @Override
  public void appendRow(String row) throws IllegalArgumentException {
    checkRowValidity(row);
    columnCount = row.length();
    rows.add(row);
  }

  @Override
  public int getRowCount() {
    return rows.size();
  }

  @Override
  public int getColumnCount() {
    return columnCount;
  }

  @Override
  public String getRow(int rowIndex) throws IllegalArgumentException {
    if (rowIndex < 0 || rowIndex >= getRowCount()) {
      throw new IllegalArgumentException("Row outside the grid: " + rowIndex);
    }
    return rows.get(rowIndex);
  }

  @Override
  public void setCharacter(int rowIndex, int columnIndex, char c)
      throws IllegalArgumentException {
    assertColumnWithinBoundaries(columnIndex);

    String row = getRow(rowIndex);
    replaceRow(rowIndex, replaceCharAt(row, columnIndex, c));
  }

  @Override
  public void replaceRow(int rowIndex, String row) throws IllegalArgumentException {
    assertRowWithinBoundaries(rowIndex);
    rows.set(rowIndex, row);
  }

  @Override
  public char getCharacter(int rowIndex, int columnIndex) throws IllegalArgumentException {
    assertColumnWithinBoundaries(columnIndex);
    String row = getRow(rowIndex);
    return row.charAt(columnIndex);
  }


  private static String replaceCharAt(String s, int i, char c) {
    return s.substring(0, i) + c + s.substring(i + 1);
  }
}
