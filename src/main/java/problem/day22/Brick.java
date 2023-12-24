package problem.day22;

import tools.Vector;
import tools.Vector3;
import java.util.HashSet;
import java.util.Set;

public class Brick implements Comparable<Brick> {
  Vector3 top;
  Vector3 bottom;
  private final Set<Brick> supportedBy = new HashSet<>();
  private final Set<Brick> supports = new HashSet<>();

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

  public HorizontalArea getBottomArea() {
    Vector start = new Vector((int) bottom.x(), (int) bottom.y());
    Vector end = top.z() == bottom.z() ? new Vector((int)top.x(), (int)top.y()) : start;
    return new HorizontalArea(start, end);
  }

  public Brick dropOnTopOf(Set<Brick> supportingBricks) {
    return null; // TODO
  }
}
