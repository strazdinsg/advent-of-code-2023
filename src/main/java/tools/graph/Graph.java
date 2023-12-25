package tools.graph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * A graph with vertices and edges.
 */
public class Graph {
  private final Set<String> vertices = new HashSet<>();
  private final Map<String, Set<Edge>> vertexEdges = new HashMap<>();
  private final boolean bidirectional;

  /**
   * Create a new graph.
   *
   * @param bidirectional Whether the graph should be bidirectional (true) or directed (false)
   */
  public Graph(boolean bidirectional) {
    this.bidirectional = bidirectional;

  }

  /**
   * Add an unidirectional edge from vertex u to vertex v.
   *
   * @param u      Name of vertex u
   * @param v      Name of vertex v
   * @param weight Weight of the edge
   */
  public void addUnidirectionalEdge(String u, String v, int weight) {
    vertices.add(u);
    vertices.add(v);
    addEdge(u, v, weight);
  }

  /**
   * Add a bidirectional edge from vertex u to vertex v.
   *
   * @param u      Name of vertex u
   * @param v      Name of vertex v
   * @param weight Weight of the edge
   */
  public void addBidirectionalEdge(String u, String v, int weight) {
    vertices.add(u);
    vertices.add(v);
    addEdge(u, v, weight);
    addEdge(v, u, weight);
  }


  private void addEdge(String u, String v, int weight) {
    Set<Edge> edges = vertexEdges.computeIfAbsent(u, (k) -> new HashSet<>());
    edges.add(new Edge(u, v, weight));
  }

  public Set<String> getVertices() {
    return vertices;
  }

  public Set<Edge> getConnectionsFor(String vertex) {
    return vertexEdges.get(vertex);
  }

  /**
   * Whether the graph is bidirectional.
   *
   * @return True when bidirectional, false when unidirectional (directed edges)
   */
  public boolean isBidirectional() {
    return bidirectional;
  }
}
