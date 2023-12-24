package problem.day23;

import tools.Vector;

/**
 * One cell along a path.
 *
 * @param position The position of the cell
 * @param steps    How many steps were taken (since the last "checkpoint") to reach this cell
 */
public record PathCell(Vector position, int steps) {
  @Override
  public String toString() {
    return position + " = " + steps;
  }
}
