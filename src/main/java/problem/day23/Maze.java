package problem.day23;

import tools.CharArrayGrid;
import tools.Direction;
import tools.Logger;
import tools.Vector;
import java.util.*;

public class Maze {
  private static final char EMPTY = '.';
  private static final char SLOPE_NORTH = '^';
  private static final char SLOPE_EAST = '>';
  private static final char SLOPE_WEST = '<';
  private static final char SLOPE_SOUTH = 'v';
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
      Vector slope = toVisit.poll();
      visited.add(slope);
      Set<Edge> reachableEdges = findReachableSlopes(slope);
      graph.addAll(reachableEdges);
      for (Edge edge : reachableEdges) {
        if (!visited.contains(edge.to()) && !edge.to().equals(end)) {
          toVisit.add(edge.to());
        }
      }
    }
  }

  private Set<Edge> findReachableSlopes(Vector from) {
    Logger.info("Slopes from " + from + ":");
    Set<Edge> edges = new HashSet<>();
    Queue<PathCell> toVisit = new ArrayDeque<>();
    Set<Vector> visited = new HashSet<>();
    PathCell firstCell = getNextForSlope(from);
    toVisit.add(firstCell);
    while (!toVisit.isEmpty()) {
      PathCell cell = toVisit.poll();
      visited.add(cell.position());
//      Logger.info("  visit " + cell);
      Set<PathCell> reachableCells = getReachableFrom(cell);
      for (PathCell reachable : reachableCells) {
        if (!visited.contains(reachable.position())) {
          if (isSlope(reachable)) {
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

  private PathCell getNextForSlope(Vector slope) {
    PathCell nextCell = null;

    int row = switch (grid.getCharacter(slope)) {
      case SLOPE_EAST, SLOPE_WEST -> slope.y();
      case SLOPE_NORTH -> slope.y() - 1;
      case SLOPE_SOUTH -> slope.y() + 1;
      default -> throw new IllegalArgumentException("Invalid slope character at " + slope);
    };
    int column = switch (grid.getCharacter(slope)) {
      case SLOPE_NORTH, SLOPE_SOUTH -> slope.x();
      case SLOPE_EAST -> slope.x() + 1;
      case SLOPE_WEST -> slope.x() - 1;
      default -> throw new IllegalArgumentException("Invalid slope character at " + slope);
    };

    if (grid.isWithin(row, column) && grid.getCharacter(row, column) == EMPTY) {
      nextCell = new PathCell(new Vector(column, row), 1);
    }

    return nextCell;
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
      case NORTH -> '^';
      case WEST -> '<';
      case SOUTH -> 'v';
      case EAST -> '>';
    };
    return cell == EMPTY || cell == allowedSlope;
  }

  private boolean isSlope(PathCell cell) {
    char c = grid.getCharacter(cell.position());
    return c == SLOPE_NORTH || c == SLOPE_EAST || c == SLOPE_SOUTH || c == SLOPE_WEST;
  }


  public long getLongestPath() {
    return graph.getLongestPathLength(start, end);
  }
}
