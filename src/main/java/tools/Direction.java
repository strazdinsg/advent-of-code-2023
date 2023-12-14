package tools;

/**
 * A geographical direction.
 */
public enum Direction {
  NORTH, WEST, SOUTH, EAST;

  /**
   * Get the horizontal gradient for this position.
   *
   * @return A gradient of -1, 0, or 1, where -1 means "to the left" and +1 means "to the right"
   */
  public int getHorizontalGradient() {
    return switch (this) {
      case NORTH, SOUTH -> 0;
      case WEST -> -1;
      case EAST -> 1;
    };
  }

  /**
   * Get the vertical gradient for this position.
   *
   * @return A gradient of -1, 0, or 1, where -1 means "up" and +1 means "down"
   */
  public int getVerticalGradient() {
    return switch (this) {
      case WEST, EAST -> 0;
      case NORTH -> -1;
      case SOUTH -> 1;
    };
  }
}
