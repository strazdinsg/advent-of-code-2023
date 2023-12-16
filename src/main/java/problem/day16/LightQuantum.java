package problem.day16;

import tools.Direction;
import tools.Vector;

public record LightQuantum(Vector position, Direction direction) {
  @Override
  public String toString() {
    return position + " " + direction;
  }
}
