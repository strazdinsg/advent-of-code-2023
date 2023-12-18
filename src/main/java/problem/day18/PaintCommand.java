package problem.day18;

import tools.Direction;
import tools.Vector;

public record PaintCommand(Direction direction, int distance) {
  public Vector toVector() {
    int x = 0;
    int y = 0;
    switch (direction) {
      case WEST -> x = -distance;
      case NORTH -> y = -distance;
      case EAST -> x = distance;
      case SOUTH -> y = distance;
    }
    return new Vector(x, y);
  }

  @Override
  public String toString() {
    return distance + " " + direction;
  }
}
