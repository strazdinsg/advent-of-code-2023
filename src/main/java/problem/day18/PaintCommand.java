package problem.day18;

import tools.Direction;
import tools.Vector;

/**
 * A paint command.
 *
 * @param direction The direction in which to paint.
 * @param distance  The distance how long to paint
 */
public record PaintCommand(Direction direction, int distance) {
  /**
   * Convert this command to a vector.
   *
   * @return A vector representing this command, with the start position in the origin (0, 0)
   */
  public Vector toVector() {
    int x = 0;
    int y = 0;
    switch (direction) {
      case WEST -> x = -distance;
      case NORTH -> y = -distance;
      case EAST -> x = distance;
      case SOUTH -> y = distance;
      default -> throw new IllegalStateException("Invalid direction: " + direction);
    }
    return new Vector(x, y);
  }

  @Override
  public String toString() {
    return distance + " " + direction;
  }
}
