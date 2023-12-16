package problem.day16;

import static tools.Direction.*;

import tools.CharArrayGrid;
import tools.Direction;
import tools.Logger;
import tools.Vector;
import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;

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
  private Queue<LightQuantum> toVisit = new ArrayDeque<>();
  private Set<LightQuantum> visited = new HashSet<>();

  public Cave(CharArrayGrid grid) {
    this.grid = grid;
    energized = new boolean[grid.getRowCount()][];
    for (int row = 0; row < grid.getRowCount(); ++row) {
      energized[row] = new boolean[grid.getColumnCount()];
    }
  }

  public void bounceLight() {
    toVisit.add(new LightQuantum(new Vector(0, 0), EAST));
    while (!toVisit.isEmpty()) {
      LightQuantum light = toVisit.poll();
      if (!visited.contains(light)) {
        visit(light);
      } else {
        Logger.info("Already visited " + light);
      }
    }
  }

  private void visit(LightQuantum light) {
    energized[light.position().y()][light.position().x()] = true;
    visited.add(light);
    char c = grid.getCharacter(light.position());
    Logger.info("" + light);
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


  private void continueBeam(LightQuantum light) {
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
      toVisit.add(new LightQuantum(position, direction));
    }
  }

  private void splitBeam(LightQuantum light) {
    if (light.direction().isHorizontal()) {
      lightEnters(light.position().minus(VERTICAL), NORTH);
      lightEnters(light.position().plus(VERTICAL), SOUTH);
    } else {
      lightEnters(light.position().minus(HORIZONTAL), WEST);
      lightEnters(light.position().plus(HORIZONTAL), EAST);
    }
  }

  private void rotateNorthEast(LightQuantum light) {
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
    };
  }

  private void rotateNorthWest(LightQuantum light) {
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
    };
  }

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
