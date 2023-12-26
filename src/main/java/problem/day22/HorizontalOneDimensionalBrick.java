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

  public Vector moveTowardsEnd(Vector cube) {
    return cube.plus(step);
  }

  @Override
  public String toString() {
    return start + " - " + end;
  }

  public boolean isInside(Vector cube) {
    return cube.x() >= start.x() && cube.x() <= end.x()
        && cube.y() >= start.y() && cube.y() <= end.y();
  }
}
