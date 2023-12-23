package problem.day23;

import tools.Logger;
import tools.Vector;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class PathGraph {
  private final Map<Vector, Set<Edge>> edges = new HashMap<>();
  private final Set<Vector> visited = new HashSet<>();

  public void add(Edge edge) {
    Set<Edge> edgesFromVertex = edges.computeIfAbsent(edge.from(), k -> new HashSet<>());
    edgesFromVertex.add(edge);
  }

  public void addAll(Set<Edge> edges) {
    edges.forEach(this::add);
  }

  public long findLongestPath(Vector from, Vector end) {
    if (from.equals(end)) {
      return 0;
    }

    visited.add(from);

    Set<Edge> edgesFrom = edges.get(from);
    if (edgesFrom == null) {
      throw new IllegalStateException("Edges missing for " + from);
    }

    long longestPath = -1;
    for (Edge e : edgesFrom) {
      if (!visited.contains(e.to())) {
        long pathAlongThisEdge = e.length() + findLongestPath(e.to(), end);
        if (pathAlongThisEdge >= 0) {
          longestPath = Math.max(pathAlongThisEdge, longestPath);
        }
      }
    }

    visited.remove(from);

    return longestPath;
  }

  public long getLongestPathLength(Vector start, Vector end) {
    visited.clear();
    return findLongestPath(start, end);
  }
}
