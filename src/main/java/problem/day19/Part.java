package problem.day19;

/**
 * A part to process through the workflow.
 *
 * @param x The x-property of the part
 * @param m The m-property of the part
 * @param a The a-property of the part
 * @param s The s-property of the part
 */
public record Part(int x, int m, int a, int s) {
  public int getPropertySum() {
    return x + m + a + s;
  }

  /**
   * Get the value of a given property.
   *
   * @param property The property to check
   * @return The value of the specified property
   */
  public int getPropertyValue(char property) {
    return switch (property) {
      case 'x' -> x;
      case 'm' -> m;
      case 'a' -> a;
      case 's' -> s;
      default -> throw new IllegalArgumentException("Invalid property: " + property);
    };
  }
}
