package tools.graph;

/**
 * A vertex along a path.
 *
 * @param vertex The name of the vertex
 * @param steps  How many steps were taken to reach this vertex
 * @param prev   The name of the previous vertex from which this vertex was reached
 */
public record PathVertex(String vertex, int steps, String prev) {
  @Override
  public String toString() {
    return prev + " -(" + steps + ")-> " + vertex;
  }
}
