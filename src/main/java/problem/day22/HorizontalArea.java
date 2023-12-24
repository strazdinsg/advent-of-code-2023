package problem.day22;

import tools.Vector;

/**
 * A horizontal area.
 *
 * @param start The start position of the area
 * @param end   The end position of the area
 */
public record HorizontalArea(Vector start, Vector end) {
  public Vector moveTowardsEnd(Vector cube) {
    return null; // TODO
  }
}
