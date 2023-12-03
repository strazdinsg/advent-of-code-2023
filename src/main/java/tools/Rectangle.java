package tools;

/**
 * Represents rectangular boundaries.
 */
public class Rectangle {
  private final Vector topLeft;
  private final Vector bottomRight;

  /**
   * Create boundaries.
   *
   * @param minX x coordinate of top-left corner
   * @param minY y coordinate of top-left corner
   * @param maxX x coordinate of bottom-right corner
   * @param maxY y coordinate of bottom-right corner
   */
  public Rectangle(int minX, int minY, int maxX, int maxY) {
    if (minX > maxX || minY > maxY) {
      throw new IllegalArgumentException("Coordinates don't form a rectangle: ("
          + minX + ", " + minY + ") - (" + maxX + ", " + maxY + ")");
    }
    topLeft = new Vector(minX, minY);
    bottomRight = new Vector(maxX, maxY);
  }

  /**
   * Create a new Rectangle.
   *
   * @param topLeft     The top-left corner coordinates
   * @param bottomRight The bottom-right corner coordinates
   * @throws IllegalArgumentException When the coordinates are invalid
   */
  public Rectangle(Vector topLeft, Vector bottomRight) throws IllegalArgumentException {
    if (topLeft == null) {
      throw new IllegalArgumentException("Top-left corner can't be null");
    }
    if (bottomRight == null) {
      throw new IllegalArgumentException("Bottom-right corner can't be null");
    }
    if (topLeft.x() > bottomRight.x() || topLeft.y() > bottomRight.y()) {
      throw new IllegalArgumentException("Top-left corner " + topLeft
          + " can't be below bottomRight " + bottomRight);
    }
    this.topLeft = topLeft;
    this.bottomRight = bottomRight;
  }

  /**
   * Get the minimum X value (the X-position of the left side).
   *
   * @return The X-position of the left side of the rectangle
   */
  public int getMinX() {
    return topLeft.x();
  }

  /**
   * Get the maximum X value (the X-position of the right side).
   *
   * @return The X-position of the right side of the rectangle
   */
  public int getMaxX() {
    return bottomRight.x();
  }

  /**
   * Get the minimum Y value (the Y-position of the top side).
   *
   * @return the Y-position of the top side
   */
  public int getMinY() {
    return topLeft.y();
  }

  /**
   * Get the maximum Y value (the Y-position of the bottom side).
   *
   * @return the Y-position of the bottom side
   */
  public int getMaxY() {
    return bottomRight.y();
  }

  /**
   * Get the width of the rectangle - number of pixels (cells).
   *
   * @return The width
   */
  public int getWidth() {
    return bottomRight.x() - topLeft.x() + 1;
  }

  /**
   * Get the height of the rectangle - number of pixels (cells).
   *
   * @return The height
   */
  public int getHeight() {
    return bottomRight.y() - topLeft.y() + 1;
  }

  /**
   * Get the top-left position.
   *
   * @return Position of the top-left corner
   */
  public Vector getTopLeft() {
    return topLeft;
  }

  /**
   * Get the bottom-right position.
   *
   * @return Position of the bottom-right corner
   */
  public Vector getBottomRight() {
    return bottomRight;
  }

  /**
   * Create a new Rectangle where the coordinates are covering both the original rectangle and
   * the new rectangle.
   *
   * @param other The other rectangle to include in the resulting rectangle
   * @return A new rectangle covering both the original rectangle and the new rectangle.
   */
  public Rectangle extend(Rectangle other) {
    int minX = Math.min(getTopLeft().x(), other.getTopLeft().x());
    int minY = Math.min(getTopLeft().y(), other.getTopLeft().y());
    int maxX = Math.max(getBottomRight().x(), other.getBottomRight().x());
    int maxY = Math.max(getBottomRight().y(), other.getBottomRight().y());
    return new Rectangle(minX, minY, maxX, maxY);
  }

  /**
   * Create a new Rectangle where the coordinates are covering both the original rectangle and
   * the new dot (which can be outside the original rectangle).
   *
   * @param dot The dot to cover
   * @return A new rectangle covering the original rectangle and the new dot
   */
  public Rectangle extend(Vector dot) {
    return extend(new Rectangle(dot.x(), dot.y(), dot.x(), dot.y()));
  }

  @Override
  public String toString() {
    return "" + getTopLeft() + "-" + getBottomRight();
  }

  /**
   * Create a new rectangle which is shifted by the vector v in the negative direction
   * (the topLeft coordinate becomes topLeft minus v).
   *
   * @param v The vector to subtract from the topLeft corner
   * @return A new rectangle with the shifted coordinates
   */
  public Rectangle minus(Vector v) {
    Vector newTopLeft = topLeft.minus(v);
    Vector newBottomRight = bottomRight.minus(v);
    return new Rectangle(newTopLeft, newBottomRight);
  }
}
