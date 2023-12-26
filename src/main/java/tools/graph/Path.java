package tools.graph;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * A path in a graph.
 */
public class Path implements Iterable<PathVertex> {
  List<PathVertex> vertices = new ArrayList<>();

  public void prepend(PathVertex pathVertex) {
    vertices.add(0, pathVertex);
  }

  @Override
  public Iterator<PathVertex> iterator() {
    return vertices.iterator();
  }
}
