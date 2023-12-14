package tools;

/**
 * An array of characters.
 */
public class CharArray {
  private final char[] chars;

  /**
   * Create a character array from a string.
   *
   * @param s The string to use as a basis for creating this character array.
   */
  public CharArray(String s) {
    chars = s.toCharArray();
  }

  public int length() {
    return chars.length;
  }

  @Override
  public String toString() {
    return new String(chars);
  }

  public char get(int i) {
    assertIndexWithinBoundaries(i);
    return chars[i];
  }

  public void update(int i, char c) {
    assertIndexWithinBoundaries(i);
    chars[i] = c;
  }

  private void assertIndexWithinBoundaries(int i) {
    if (i < 0 || i >= chars.length) {
      throw new IllegalArgumentException("Can't update char[" + i + "] for " + toString());
    }
  }
}
