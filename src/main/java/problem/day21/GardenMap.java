package problem.day21;

import static tools.Direction.EAST;
import static tools.Direction.NORTH;
import static tools.Direction.SOUTH;
import static tools.Direction.WEST;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;
import tools.CharArrayGrid;
import tools.Logger;
import tools.Vector;

/**
 * A map for a garden.
 */
public class GardenMap {
  private static final char START = 'S';
  private static final char GARDEN = '.';
  private final CharArrayGrid grid;
  private final Queue<Vector> toVisit = new ArrayDeque<>();
  boolean[][] visited;

  public GardenMap(CharArrayGrid grid) {
    this.grid = grid;
  }


  /**
   * Count the number of sports reachable in the given number of steps.
   *
   * @param steps The steps to take
   * @return The number of reachable spots
   */
  public long countReachableSpots(int steps) {
    Vector startPosition = getStartPosition();
    Logger.info("Start at " + startPosition);
    toVisit.add(startPosition);
    for (int i = 0; i < steps; ++i) {
      int spotCount = toVisit.size();
      Logger.info("  " + i + ": " + spotCount);
      clearVisited();
      for (int j = 0; j < spotCount; ++j) {
        Vector position = toVisit.poll();
        Logger.info(position.toString());
        tryVisitAdjacent(position);
      }
    }
    return toVisit.size();
  }

  private void clearVisited() {
    visited = new boolean[grid.getRowCount()][];
    for (int row = 0; row < grid.getRowCount(); ++row) {
      visited[row] = new boolean[grid.getColumnCount()];
    }
  }

  private Vector getStartPosition() {
    List<Vector> positions = grid.findCharacterLocations(START);
    if (positions.size() != 1) {
      throw new IllegalStateException("Invalid number of start positions: " + positions.size());
    }
    return positions.get(0);
  }

  private void tryVisitAdjacent(Vector position) {
    tryVisit(position.step(NORTH));
    tryVisit(position.step(EAST));
    tryVisit(position.step(SOUTH));
    tryVisit(position.step(WEST));
  }

  private void tryVisit(Vector position) {
    if (canVisit(position)) {
      Logger.info("    can reach " + position);
      toVisit.add(position);
      visited[position.y()][position.x()] = true;
    }
  }

  private boolean canVisit(Vector position) {
    boolean canVisit = false;
    if (grid.isWithin(position)) {
      char c = grid.getCharacter(position);
      canVisit = (c == GARDEN || c == START) && !visited[position.y()][position.x()];
    }
    return canVisit;
  }
}
