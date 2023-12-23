package problem.day23;

import static tools.Direction.EAST;
import static tools.Direction.NORTH;
import static tools.Direction.SOUTH;
import static tools.Direction.WEST;

import tools.CharArrayGrid;
import tools.Direction;
import tools.Logger;
import tools.Vector;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;

public class Maze {
  private static final char EMPTY = '.';
  private static final char SLOPE_NORTH = '^';
  private static final char SLOPE_EAST = '>';
  private static final char SLOPE_WEST = '<';
  private static final char SLOPE_SOUTH = 'v';
  private static final char FOREST = '#';
  private final CharArrayGrid grid;
  private final PathGraph graph = new PathGraph();

  private Vector start;
  private Vector end;

  private final boolean canClimb;

  public Maze(CharArrayGrid grid, boolean canClimb) {
    this.grid = grid;
    this.canClimb = canClimb;
  }

  public void findPaths() {
    start = findStart();
    if (start == null) {
      throw new IllegalStateException("Start tile not found");
    }
    end = findEnd();
    if (end == null) {
      throw new IllegalStateException("End tile not found");
    }

    markStartAndStopAsSlopes();

    buildPathTree();
  }

  private void markStartAndStopAsSlopes() {
    grid.setCharacter(start.y(), start.x(), SLOPE_SOUTH);
    grid.setCharacter(end.y(), end.x(), SLOPE_SOUTH);
  }

  private Vector findStart() {
    return findEmptySpotOnRow(0);
  }

  private Vector findEmptySpotOnRow(int row) {
    Vector emptySpot = null;
    int column = 0;
    while (emptySpot == null && column < grid.getColumnCount()) {
      if (grid.getCharacter(row, column) == EMPTY) {
        emptySpot = new Vector(column, row);
      }
      ++column;
    }
    return emptySpot;
  }

  private Vector findEnd() {
    return findEmptySpotOnRow(grid.getRowCount() - 1);
  }

  private void buildPathTree() {
    Queue<Vector> toVisit = new ArrayDeque<>();
    Set<Vector> visited = new HashSet<>();
    toVisit.add(start);
    while (!toVisit.isEmpty()) {
      Vector junction = toVisit.poll();
      visited.add(junction);
      Set<Edge> edges = findReachableJunctions(junction);
      graph.addAll(edges);
      for (Edge edge : edges) {
        if (!visited.contains(edge.to()) && !edge.to().equals(end)) {
          toVisit.add(edge.to());
        }
      }
    }
  }

  private Set<Edge> findReachableJunctions(Vector from) {
    Logger.info("Junctions from " + from + ":");
    Set<Edge> edges = new HashSet<>();
    Queue<PathCell> toVisit = new ArrayDeque<>();
    Set<Vector> visited = new HashSet<>();
    toVisit.add(new PathCell(from, 0));
    while (!toVisit.isEmpty()) {
      PathCell cell = toVisit.poll();
      visited.add(cell.position());
      Set<PathCell> reachableCells = getReachableFrom(cell);
      for (PathCell reachable : reachableCells) {
        if (!visited.contains(reachable.position())) {
          if (isJunction(reachable)) {
            Logger.info("  ==> " + reachable);
            edges.add(new Edge(from, reachable.position(), reachable.steps()));
          } else {
            toVisit.add(reachable);
          }
        }
      }
    }
    return edges;
  }

  private Set<PathCell> getReachableFrom(PathCell cell) {
    Set<PathCell> reachable = new HashSet<>();
    Arrays.stream(Direction.values()).forEach(d -> tryReach(cell, d, reachable));
    return reachable;
  }

  private void tryReach(PathCell cell, Direction direction, Set<PathCell> reachable) {
    Vector next = cell.position().step(direction);
    if (grid.isWithin(next) && isPassable(grid.getCharacter(next), direction)) {
      reachable.add(new PathCell(next, cell.steps() + 1));
    }

  }

  private boolean isPassable(char cell, Direction direction) {
    char allowedSlope = switch (direction) {
      case NORTH -> SLOPE_NORTH;
      case WEST -> SLOPE_WEST;
      case SOUTH -> SLOPE_SOUTH;
      case EAST -> SLOPE_EAST;
    };
    return cell == EMPTY || (cell == allowedSlope || canClimb);
  }

  private boolean isJunction(PathCell cell) {
    return isWalkable(cell.position().step(NORTH))
        + isWalkable(cell.position().step(EAST))
        + isWalkable(cell.position().step(SOUTH))
        + isWalkable(cell.position().step(WEST))
        > 2 || cell.position().equals(end);
  }

  private int isWalkable(Vector position) {
    return grid.isWithin(position) && grid.getCharacter(position) != FOREST ? 1 : 0;
  }

  public long getLongestPath() {
    return graph.getLongestPathLength(start, end);
  }
}
