package problem.day22;

import java.util.Objects;

public record Vector3(int x, int y, int z) implements Comparable<Vector3> {

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Vector3 vector3 = (Vector3) o;
    return x == vector3.x && y == vector3.y && z == vector3.z;
  }

  @Override
  public int hashCode() {
    return Objects.hash(x, y, z);
  }

  @Override
  public int compareTo(Vector3 o) {
    int comparison = Integer.compare(this.z, o.z);
    if (comparison == 0) {
      comparison = Integer.compare(this.x, o.x);
    }
    if (comparison == 0) {
      comparison = Integer.compare(this.y, o.y);
    }
    return comparison;
  }
}
