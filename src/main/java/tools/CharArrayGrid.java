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
}