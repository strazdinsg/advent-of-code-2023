package problem.day23;

import tools.Vector;

public record PathCell(Vector position, int steps) {
  @Override
  public String toString() {
    return position + " = " + steps;
  }
}
