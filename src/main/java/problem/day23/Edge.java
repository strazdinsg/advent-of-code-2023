package problem.day23;

import tools.Vector;

public record Edge(Vector from, Vector to, int length) {
  @Override
  public String toString() {
    return from + " - " + length + " -> " + to;
  }
}
