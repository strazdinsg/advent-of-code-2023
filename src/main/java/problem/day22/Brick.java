package problem.day22;

import java.util.HashSet;
import java.util.Set;
import tools.Vector;
import tools.Vector3;

/**
 * A brick having length in one direction (length 1 in the other direction).
 */
public class Brick implements Comparable<Brick> {
  Vector3 top;
  Vector3 bottom;
  private final Set<Brick> supportedBy = new HashSet<>();
  private final Set<Brick> supports = new HashSet<>();

  /**
   * Create a brick.
   *
   * @param v1 Coordinates of one end of the brick
   * @param v2 Coordinates of the other end of the brick
   */
  public Brick(Vector3 v1, Vector3 v2) {
    if (v1.z() < v2.z()) {
      bottom = v1;
      top = v2;
    } else {
      bottom = v2;
      top = v1;
    }
  }

  @Override
  public int compareTo(Brick o) {
    int comparison = this.bottom.compareTo(o.bottom);
    if (comparison == 0) {
      comparison = this.top.compareTo(o.top);
    }
    return comparison;
  }

  public Set<Brick> getSupportedBy() {
    return supportedBy;
  }

  public boolean isFreeStanding() {
    return supports.isEmpty();
  }

  /**
   * Get the bottom area of the brick.
   *
   * @return The bottom area of the brick
   */
  public HorizontalArea getBottomArea() {
    Vector start = new Vector((int) bottom.x(), (int) bottom.y());
    Vector end = top.z() == bottom.z() ? new Vector((int) top.x(), (int) top.y()) : start;
    return new HorizontalArea(start, end);
  }

  /**
   * Drop this brick until it lands on top of the supporting bricks (on the highest among them).
   *
   * @param supportingBricks The supporting blocks below this brick.
   * @return A new brick object representing the location of this brick when it has landed
   */
  public Brick dropOnTopOf(Set<Brick> supportingBricks) {
    return null; // TODO
  }
}
