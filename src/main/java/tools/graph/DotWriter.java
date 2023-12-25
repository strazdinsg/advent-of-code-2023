package tools.graph;

import java.util.Set;
import java.util.stream.Collectors;
import tools.OutputFile;

/**
 * Writes a graph to a .dot file.
 * To generate an SVG file from the .dot file, use the `dot` command line tool
 * (`sudo apt install graphviz`), run command `dot -Tsvg yourfile.dot > outputfile.svg`
 */
public class DotWriter {
  /**
   * Not allowed to instantiate the class.
   */
  private DotWriter() {
  }

  /**
   * Write a graph to a .dot file
   *
   * @param graph    The graph to write to the file
   * @param filePath The path to the file where to write the output
   */
  public static void write(Graph graph, String filePath) {
    OutputFile file = new OutputFile(filePath);
    String graphType = graph.isBidirectional() ? "graph" : "digraph";
    file.writeLine(graphType + " g {");
    for (String vertex : graph.getVertices()) {
      writeEdgesToVertex(file, graph, vertex);
    }
    file.writeLine("}");
    file.close();
  }

  private static void writeEdgesToVertex(OutputFile file, Graph graph, String vertex) {
    Set<Edge> edges = graph.getConnectionsFor(vertex);
    if (edges != null && !edges.isEmpty()) {
      if (graph.isBidirectional()) {
        edges = filterConnections(edges, vertex);
      }
      String connection = graph.isBidirectional() ? "--" : "->";
      for (Edge edge : edges) {
        String weight = edge.weight() != 1 ? (" [weight=" + edge.weight() + "]") : "";
        file.writeLine("  " + vertex + " " + connection + " " + edge.v() + weight);
      }
    }
  }

  /**
   * Get only those vertices from the set, which have vertex name "higher than" vertex.
   * This ensures that each bidirectional edge (which will be reported twice by the graph)
   * is only included once in the resulting .dot file.
   *
   * @param connections The connected vertices to filter
   * @param vertex      The name of the vertex
   * @return The connected vertices having name > vertex
   */
  private static Set<Edge> filterConnections(Set<Edge> connections, String vertex) {
    return connections.stream().filter(c -> c.v().compareTo(vertex) > 0)
        .collect(Collectors.toSet());
  }
}
