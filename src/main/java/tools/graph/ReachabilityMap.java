package tools.graph;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Reachability map - which vertices were reached, in how many steps and from which "previous node".
 */
public class ReachabilityMap implements Iterable<PathVertex> {
  Map<String, PathVertex> reachable = new HashMap<>();

  @Override
  public Iterator<PathVertex> iterator() {
    return reachable.values().iterator();
  }

  public Set<String> getVertices() {
    return reachable.keySet();
  }

  public void put(String vertex, PathVertex pathVertex) {
    reachable.put(vertex, pathVertex);
  }

  public PathVertex get(String vertex) {
    return reachable.get(vertex);
  }
}
