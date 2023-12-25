package tools.graph;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * A graph with vertices and edges.
 */
public class BidirectionalGraph {
  private final Set<String> vertices = new HashSet<>();
  private final Map<String, Set<String>> vertexEdges = new HashMap<>();


  /**
   * Add a bidirectional edge from vertex u to vertex v.
   *
   * @param u Name of vertex u
   * @param v Name of vertex v
   */
  public void addBidirectionalEdge(String u, String v) {
    vertices.add(u);
    vertices.add(v);
    addEdge(u, v);
    addEdge(v, u);
  }


  private void addEdge(String u, String v) {
    Set<String> edges = vertexEdges.computeIfAbsent(u, (k) -> new HashSet<>());
    edges.add(v);
  }

  public Set<String> getVertices() {
    return vertices;
  }

  public Set<String> getConnectionsFor(String vertex) {
    return vertexEdges.get(vertex);
  }

  /**
   * Whether the graph is bidirectional.
   *
   * @return True when bidirectional, false when unidirectional (directed edges)
   */
  public boolean isBidirectional() {
    return true;
  }

  /**
   * Remove edge (u, v) from the graph. If the graph is bidirectional, remove (v, u) as well.
   *
   * @param u Name of vertex u
   * @param v Name of vertex v
   */
  public void removeEdge(String u, String v) {
    removeDirectionalEdge(u, v);
    if (isBidirectional()) {
      removeDirectionalEdge(v, u);
    }
  }

  private void removeDirectionalEdge(String u, String v) {
    Set<String> edgesForVertex = vertexEdges.get(u);
    if (edgesForVertex != null) {
      edgesForVertex.remove(v);
    }
  }

  /**
   * Find the minimum cut of the graph (max flow), then return the cluster where the source belongs.
   *
   * @param source      The source vertex
   * @param destination The destination vertex, must be in the other cluster
   * @return The names of reachable nodes within the starting cluster
   */
  public Set<String> findStartCluster(String source, String destination) {
    BidirectionalGraph residualNetwork = this.createCopy();
    Path path;
    ReachabilityMap reachable;
    do {
      reachable = residualNetwork.findReachableNodesFrom(source);
      path = constructPath(reachable, source, destination);
      if (path != null) {
        residualNetwork.removeEdges(path);
      }
    } while (path != null);

    return reachable.getVertices();
  }

  private void removeEdges(Path path) {
    for (PathVertex pathVertex : path) {
      removeEdge(pathVertex.prev(), pathVertex.vertex());
    }
  }


  private ReachabilityMap findReachableNodesFrom(String source) {
    Set<String> visited = new HashSet<>();
    Queue<PathVertex> toVisit = new ArrayDeque<>();
    ReachabilityMap reachable = new ReachabilityMap();
    toVisit.add(new PathVertex(source, 0, null));
    while (!toVisit.isEmpty()) {
      PathVertex pathVertex = toVisit.poll();
      String u = pathVertex.vertex();
      if (!visited.contains(u)) {
        visited.add(u);
        reachable.put(u, pathVertex);
        for (String v : vertexEdges.get(u)) {
          if (!visited.contains(v)) {
            toVisit.add(new PathVertex(v, pathVertex.steps() + 1, u));
          }
        }
      }
    }
    return reachable;
  }

  private static Path constructPath(ReachabilityMap reachable, String from, String to) {
    if (reachable.get(to) == null) {
      return null;
    }

    Path path = new Path();
    String vertex = to;
    while (!vertex.equals(from)) {
      PathVertex pathVertex = reachable.get(vertex);
      if (pathVertex == null) {
        throw new IllegalStateException("vertex " + vertex + " not found in the reachable list");
      }
      path.prepend(pathVertex);
      vertex = pathVertex.prev();
    }
    return path;
  }

  private BidirectionalGraph createCopy() {
    BidirectionalGraph residualNetwork = new BidirectionalGraph();
    residualNetwork.vertices.addAll(this.vertices);
    for (var entry : vertexEdges.entrySet()) {
      residualNetwork.vertexEdges.computeIfAbsent(entry.getKey(), (v) -> new HashSet<>())
          .addAll(entry.getValue());
    }
    return residualNetwork;
  }

  public int getVertexCount() {
    return vertices.size();
  }

  public Set<String> verticesExcept(Set<String> discardedVertices) {
    return vertices.stream().filter(v -> !discardedVertices.contains(v))
        .collect(Collectors.toSet());
  }
}
