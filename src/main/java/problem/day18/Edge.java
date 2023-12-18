package problem.day18;

import tools.Vector;

public record Edge(Vector start, Vector end) {

  @Override
  public String toString() {
    return start + " - " + end;
  }

  public Edge minus(Vector shift) {
    return new Edge(start.minus(shift), end.minus(shift));
  }

  public boolean isHorizontal() {
    Vector diff = end.minus(start);
    return diff.y() == 0 && diff.x() != 0;
  }
}
