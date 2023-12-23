package problem.day23;

import tools.Logger;
import tools.Vector;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class PathGraph {
  private final Map<Vector, Set<Edge>> edges = new HashMap<>();

  public void add(Edge edge) {
    Set<Edge> edgesFromVertex = edges.computeIfAbsent(edge.from(), k -> new HashSet<>());
    edgesFromVertex.add(edge);
  }

  public void addAll(Set<Edge> edges) {
    edges.forEach(this::add);
  }

  public long getLongestPathLength(Vector from, Vector end) {
    if (from.equals(end)) {
      return 0;
    }

    Set<Edge> edgesFrom = edges.get(from);
    if (edgesFrom == null) {
      throw new IllegalStateException("Edges missing for " + from);
    }

    long longestPath = 0;
    for (Edge e : edgesFrom) {
      long pathAlongThisEdge = e.length() + getLongestPathLength(e.to(), end);
      longestPath = Math.max(pathAlongThisEdge, longestPath);
    }

    return longestPath;
  }
}
