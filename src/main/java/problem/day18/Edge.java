package problem.day18;

import tools.Vector;

public record Edge(Vector start, Vector end, Color color) {

  @Override
  public String toString() {
    return start + " - " + end + " = " + color;
  }

  public Edge minus(Vector shift) {
    return new Edge(start.minus(shift), end.minus(shift), color);
  }
}
