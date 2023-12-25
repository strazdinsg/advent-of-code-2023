package tools.graph;

/**
 * A bidirectional edge from vertex u to v (and vice versa).
 *
 * @param u      Name of vertex u
 * @param v      Name of vertex v
 * @param weight The weight of the edge
 */
public record Edge(String u, String v, int weight) {
}
