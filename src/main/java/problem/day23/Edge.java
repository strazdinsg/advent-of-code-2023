package problem.day23;

import tools.Vector;

/**
 * An edge in a graph.
 *
 * @param from   The vertex it goes from
 * @param to     The vertex it goes to
 * @param length The length of the edge
 */
public record Edge(Vector from, Vector to, int length) {
  @Override
  public String toString() {
    return from + " - " + length + " -> " + to;
  }
}
