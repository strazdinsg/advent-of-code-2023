package problem.day16;

import static tools.Direction.EAST;
import static tools.Direction.NORTH;
import static tools.Direction.SOUTH;
import static tools.Direction.WEST;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;
import tools.CharArrayGrid;
import tools.Direction;
import tools.Logger;
import tools.Vector;

/**
 * A cave full of mirrors.
 */
public class Cave {
  private static final char EMPTY = '.';
  private static final char VERTICAL_SPLITTER = '|';
  private static final char HORIZONTAL_SPLITTER = '-';
  private static final char NORTH_EAST_MIRROR = '/';
  private static final char NORTH_WEST_MIRROR = '\\';
  private static final Vector VERTICAL = new Vector(0, 1);
  private static final Vector HORIZONTAL = new Vector(1, 0);
  private final CharArrayGrid grid;
  private final boolean[][] energized;
  private Queue<LightBeam> toVisit = new ArrayDeque<>();
  private Set<LightBeam> visited = new HashSet<>();

  /**
   * Create a cave.
   *
   * @param grid The grid with the mirror map.
   */
  public Cave(CharArrayGrid grid) {
    this.grid = grid;
    energized = new boolean[grid.getRowCount()][];
    for (int row = 0; row < grid.getRowCount(); ++row) {
      energized[row] = new boolean[grid.getColumnCount()];
    }
  }

  /**
   * Shine the light inside the cave, let it bounce across all the mirrors.
   *
   * @param enteringLight The light beam that enters the cave
   */
  public void bounceLight(LightBeam enteringLight) {
    toVisit.add(enteringLight);
    while (!toVisit.isEmpty()) {
      LightBeam light = toVisit.poll();
      if (!visited.contains(light)) {
        visit(light);
      }
    }
  }

  private void visit(LightBeam light) {
    energized[light.position().y()][light.position().x()] = true;
    visited.add(light);
    char c = grid.getCharacter(light.position());
    switch (c) {
      case EMPTY:
        continueBeam(light);
        break;
      case VERTICAL_SPLITTER:
        if (light.direction().isHorizontal()) {
          splitBeam(light);
        } else {
          continueBeam(light);
        }
        break;
      case HORIZONTAL_SPLITTER:
        if (light.direction().isHorizontal()) {
          continueBeam(light);
        } else {
          splitBeam(light);
        }
        break;
      case NORTH_EAST_MIRROR:
        rotateNorthEast(light);
        break;
      case NORTH_WEST_MIRROR:
        rotateNorthWest(light);
        break;
      default:
        throw new UnsupportedOperationException("Not implemented: " + c);
    }
  }


  private void continueBeam(LightBeam light) {
    Vector nextPosition = switch (light.direction()) {
      case NORTH -> light.position().minus(VERTICAL);
      case EAST -> light.position().plus(HORIZONTAL);
      case SOUTH -> light.position().plus(VERTICAL);
      case WEST -> light.position().minus(HORIZONTAL);
    };
    lightEnters(nextPosition, light.direction());
  }

  private void lightEnters(Vector position, Direction direction) {
    if (grid.isWithin(position)) {
      toVisit.add(new LightBeam(position, direction));
    }
  }

  private void splitBeam(LightBeam light) {
    if (light.direction().isHorizontal()) {
      lightEnters(light.position().minus(VERTICAL), NORTH);
      lightEnters(light.position().plus(VERTICAL), SOUTH);
    } else {
      lightEnters(light.position().minus(HORIZONTAL), WEST);
      lightEnters(light.position().plus(HORIZONTAL), EAST);
    }
  }

  private void rotateNorthEast(LightBeam light) {
    switch (light.direction()) {
      case NORTH:
        lightEnters(light.position().plus(HORIZONTAL), EAST);
        break;
      case SOUTH:
        lightEnters(light.position().minus(HORIZONTAL), WEST);
        break;
      case EAST:
        lightEnters(light.position().minus(VERTICAL), NORTH);
        break;
      case WEST:
        lightEnters(light.position().plus(VERTICAL), SOUTH);
        break;
      default:
        throw new IllegalArgumentException("Unsupported direction: " + light.direction());
    }
    ;
  }

  private void rotateNorthWest(LightBeam light) {
    switch (light.direction()) {
      case NORTH:
        lightEnters(light.position().minus(HORIZONTAL), WEST);
        break;
      case SOUTH:
        lightEnters(light.position().plus(HORIZONTAL), EAST);
        break;
      case EAST:
        lightEnters(light.position().plus(VERTICAL), SOUTH);
        break;
      case WEST:
        lightEnters(light.position().minus(VERTICAL), NORTH);
        break;
      default:
        throw new IllegalArgumentException("Unsupported direction: " + light.direction());
    }
    ;
  }

  /**
   * Calculate the number of energized tiles after bouncing the light in the cave.
   *
   * @return The number of energized tiles
   */
  public long getEnergizedTileCount() {
    long energizedCellCount = 0;
    for (int row = 0; row < energized.length; ++row) {
      for (int column = 0; column < energized[0].length; ++column) {
        energizedCellCount += energized[row][column] ? 1 : 0;
      }
    }
    return energizedCellCount;
  }
}
