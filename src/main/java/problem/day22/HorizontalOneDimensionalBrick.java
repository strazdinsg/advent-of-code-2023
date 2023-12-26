package problem.day22;


import tools.Vector;

/**
 * A horizontal area, which is supposed to be one-unit wide in one dimension and 1 or more units
 * long in the other dimension.
 *
 * @param start The start position of the area
 * @param end   The end position of the area
 * @param step  The step to take when moving from start to end
 */
public record HorizontalOneDimensionalBrick(Vector start, Vector end, Vector step) {
  /**
   * Create a horizontal, one-dimensional brick.
   *
   * @param v1 Position of one end of the brick
   * @param v2 Position of the other end of the brick
   * @return A brick, where the start and end will be v1 and v2, so that the start is <= end
   */
  public static HorizontalOneDimensionalBrick create(Vector v1, Vector v2) {
    Vector start;
    Vector end;
    if (v1.x() < v2.x() || v1.y() < v2.y()) {
      start = v1;
      end = v2;
    } else {
      start = v2;
      end = v1;
    }
    Vector step = start.x() < end.x() ? new Vector(1, 0) : new Vector(0, 1);
    return new HorizontalOneDimensionalBrick(start, end, step);
  }

  /**
   * Move the cube towards the end of the brick.
   *
   * @param cube The cube to move
   * @return A new cube (position), which can be outside of this brick!
   */
  public Vector moveTowardsEnd(Vector cube) {
    return cube.plus(step);
  }

  @Override
  public String toString() {
    return start + " - " + end;
  }

  /**
   * Check whether the given cube is inside this brick.
   *
   * @param cube The cube to check
   * @return True when cube is inside this brick, false otherwise
   */
  public boolean isInside(Vector cube) {
    return cube.x() >= start.x() && cube.x() <= end.x()
        && cube.y() >= start.y() && cube.y() <= end.y();
  }
}
