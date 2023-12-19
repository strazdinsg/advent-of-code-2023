package tools;

/**
 * A geographical direction.
 */
public enum Direction {
  NORTH, WEST, SOUTH, EAST;

  /**
   * Create a direction from relative direction character.
   *
   * @param dir Direction characters: U, D, L or R
   * @return The corresponding direction
   * @throws IllegalArgumentException When the direction character is invalid
   */
  public static Direction fromRelative(char dir) {
    return switch (dir) {
      case 'U' -> NORTH;
      case 'D' -> SOUTH;
      case 'L' -> WEST;
      case 'R' -> EAST;
      default -> throw new IllegalArgumentException("Invalid relative direction: " + dir);
    };
  }

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

  public boolean isHorizontal() {
    return this == EAST || this == WEST;
  }

  /**
   * Get the opposite direction of this one.
   *
   * @return The opposite direction
   */
  public Direction getOpposite() {
    return switch (this) {
      case NORTH -> SOUTH;
      case WEST -> EAST;
      case SOUTH -> NORTH;
      case EAST -> WEST;
    };
  }
}
