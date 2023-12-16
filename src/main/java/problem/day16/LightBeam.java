package problem.day16;

import tools.Direction;
import tools.Vector;

/**
 * A light beam.
 *
 * @param position  The current position of the light
 * @param direction The direction where the beam is heading
 */
public record LightBeam(Vector position, Direction direction) {
  @Override
  public String toString() {
    return position + " " + direction;
  }
}
