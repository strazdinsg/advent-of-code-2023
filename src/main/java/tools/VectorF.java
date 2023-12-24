package tools;

import java.util.Objects;

/**
 * A two-dimensional position (vector), with floating point value support.
 *
 * @param x The x-dimension of the position
 * @param y The y-dimension of the position
 */
public record VectorF(double x, double y) {
  /**
   * Create a new vector: this vector minus the provided vector v. This vector is unchanged!
   *
   * @param v The vector to subtract
   * @return New vector: this vector minus the provided vector v
   */
  public VectorF minus(VectorF v) {
    return new VectorF(this.x - v.x, this.y - v.y);
  }

  /**
   * Create a new vector: this vector plus the provided vector v. This vector is unchanged!
   *
   * @param v The vector to add
   * @return New vector: this vector plus the provided vector v
   */
  public VectorF plus(VectorF v) {
    return new VectorF(this.x + v.x, this.y + v.y);
  }

  /**
   * Create a new vector: this vector plus the provided vector (x, y). This vector is unchanged!
   *
   * @param x The distance to add on the x-axis
   * @param y The distance to add on the y-axis
   * @return New vector: this vector plus the provided vector v
   */
  public VectorF plus(int x, int y) {
    return new VectorF(this.x + x, this.y + y);
  }

  /**
   * Create a new vector where the speed is kept intact but the length of the new vector
   * is one unit.
   * Warning: the implementation works with integers, so there may be errors if the vector
   * is not perpendicular to x-axis or y-axis!
   * The original vector (this vector) is kept intact
   *
   * @return A scaled vector
   */
  public VectorF scaleToOneUnit() {
    double scaledX = x != 0 ? x / Math.abs(x) : 0;
    double scaledY = y != 0 ? y / Math.abs(y) : 0;
    return new VectorF(scaledX, scaledY);
  }

  /**
   * Get the absolute value of the x-axis.
   *
   * @return The absolute value of the x-axis
   */
  public double getAbsoluteX() {
    return Math.abs(x);
  }

  /**
   * Get the absolute value of the y-axis.
   *
   * @return The absolute value of the y-axis
   */
  public double getAbsoluteY() {
    return Math.abs(y);
  }

  @Override
  public String toString() {
    return "(" + x + "," + y + ")";
  }

  /**
   * Create a new vector by moving one unit in the specified speed.
   *
   * @param d The speed in which to move from the vector
   * @return A new vector with the specified destination
   */
  public VectorF step(Direction d) {
    return switch (d) {
      case NORTH -> this.plus(0, -1);
      case WEST -> this.plus(-1, 0);
      case SOUTH -> this.plus(0, 1);
      case EAST -> this.plus(1, 0);
    };
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    VectorF vector = (VectorF) o;
    return x == vector.x && y == vector.y;
  }

  @Override
  public int hashCode() {
    return Objects.hash(x, y);
  }

}
