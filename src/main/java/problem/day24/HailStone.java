package problem.day24;

import tools.Logger;
import tools.Sign;
import tools.Vector3;
import tools.VectorF;

/**
 * Represents a Hailstone, with a trajectory line in the format y = ax + b.
 *
 * @param a     Slope of the trajectory line
 * @param b     Intercept of the trajectory line
 * @param start The starting coordinates of the hailstone, at time 0
 * @param speed The speed vector of the hailstone
 */
public record HailStone(double a, double b, Vector3 start, Vector3 speed) {
  public static HailStone create(Vector3 start, Vector3 speed) {
    double a = (double) speed.y() / (double) speed.x();
    double b = (double) start.y() - a * (double) start.x();
    return new HailStone(a, b, start, speed);
  }

  /**
   * Find the point where this line intersects with the other line l.
   *
   * @param stone The other line to check
   * @return The point of intersection, or null if lines are parallel or if the intersection
   *     happened already in the past
   */
  public VectorF intersectsInFuture(HailStone stone) {
    if (areParallel(this, stone)) {
      Logger.info("Lines " + this + " and " + stone + " are parallel");
      return null;
    }

    double intersectX = (stone.b - b) / (a - stone.a);
    double intersectY = a * intersectX + b;
    VectorF intersection = new VectorF(intersectX, intersectY);
    boolean inFuture = isInFuture(intersection) && stone.isInFuture(intersection);
    if (!inFuture) {
      Logger.info("  Past intersection: " + this + " & " + stone + " @ " + intersection);
    }
    return inFuture ? intersection : null;
  }

  private boolean isInFuture(VectorF intersection) {
    return Sign.from(intersection.x() - start.x()) == Sign.from(speed.x());
  }

  private boolean areParallel(HailStone hailStone, HailStone hailStone2) {
    return hailStone.a == hailStone2.a;
  }

  @Override
  public String toString() {
    return "y = " + a + "x + " + b;
  }
}
