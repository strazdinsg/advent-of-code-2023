package tools;

import java.util.Objects;

/**
 * A three-dimensional position(vector).
 *
 * @param x The x-coordinate of the position
 * @param y The y-coordinate of the position
 * @param z The z-coordinate of the position
 */
public record Vector3(long x, long y, long z) implements Comparable<Vector3> {

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Vector3 vector3 = (Vector3) o;
    return x == vector3.x && y == vector3.y && z == vector3.z;
  }

  @Override
  public int hashCode() {
    return Objects.hash(x, y, z);
  }

  @Override
  public int compareTo(Vector3 o) {
    int comparison = Long.compare(this.z, o.z);
    if (comparison == 0) {
      comparison = Long.compare(this.x, o.x);
    }
    if (comparison == 0) {
      comparison = Long.compare(this.y, o.y);
    }
    return comparison;
  }

  @Override
  public String toString() {
    return "(" + x + ", " + y + ", " + z + ")";
  }

  public Vector3 minus(Vector3 d) {
    return new Vector3(this.x - d.x, this.y - d.y, this.z - d.z);
  }
}
