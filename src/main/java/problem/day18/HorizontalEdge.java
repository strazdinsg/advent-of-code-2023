package problem.day18;

import java.util.LinkedList;
import java.util.List;

/**
 * A horizontal edge.
 *
 * @param left  Left x-coordinate
 * @param right Right x-coordinate
 * @param y     y-coordinate
 */
public record HorizontalEdge(long left, long right, long y) implements Comparable<HorizontalEdge> {

  /**
   * Create a horizontal edge from a given edge.
   *
   * @param edge The edge to use as a basis.
   * @return A horizontal edge, clone of the given edge
   */
  public static HorizontalEdge createFrom(Edge edge) {
    int y = edge.start().y();
    int x1 = edge.start().x();
    int x2 = edge.end().x();
    int left = Math.min(x1, x2);
    int right = Math.max(x1, x2);
    return new HorizontalEdge(left, right, y);
  }

  @Override
  public int compareTo(HorizontalEdge e) {
    int c = Long.compare(this.y, e.y);
    return c != 0 ? c : Long.compare(this.left, e.left);
  }

  @Override
  public String toString() {
    return y + ": " + left + "-" + right;
  }

  public boolean projectionIntersects(HorizontalEdge e) {
    return this.projectionContains(e.left) || e.projectionContains(this.left);
  }

  private boolean projectionContains(long x) {
    return this.left <= x && this.right >= x;
  }

  /**
   * Get a new horizontal edge, which corresponds to the intersection part of this edge and e.
   *
   * @param e The projection edge to use
   * @return a projected horizontal edge with the same y coordinate as this one
   */
  public HorizontalEdge getIntersectedPart(HorizontalEdge e) {
    if (!projectionIntersects(e)) {
      throw new IllegalArgumentException(e + " does not intersect with " + this);
    }
    return new HorizontalEdge(Math.max(this.left, e.left), Math.min(this.right, e.right), y);
  }

  /**
   * Create a new list with the given edge "subtracted".
   *
   * @param e The edge to remove
   * @return a new list with horizontal edges, where a projection of the given edge is removed.
   */
  public List<HorizontalEdge> minus(HorizontalEdge e) {
    List<HorizontalEdge> edges = new LinkedList<>();
    if (!projectionIntersects(e)) {
      edges.add(this);
    } else {
      if (e.left <= this.left) {
        if (e.right < this.right) {
          // Cut off the left side
          edges.add(new HorizontalEdge(e.right + 1, this.right, this.y));
        }
      } else {
        // e.left is within this edge, the result will contain the left part
        edges.add(new HorizontalEdge(this.left, e.left - 1, this.y));
        if (e.right < this.right) {
          // some part left over on the right side
          edges.add(new HorizontalEdge(e.right + 1, this.right, this.y));
        }
      }
    }
    return edges;
  }

  public long width() {
    return right - left + 1;
  }
}