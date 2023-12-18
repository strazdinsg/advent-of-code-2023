package problem.day18;

import static tools.Direction.EAST;
import static tools.Direction.NORTH;
import static tools.Direction.SOUTH;
import static tools.Direction.WEST;

import tools.*;
import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;

public class Painting {
  private static final Color INTERIOR_COLOR = new Color("I");
  private final Color[][] colors;
  private final Vector shift;

  public Painting(Rectangle boundaries) {
    this.shift = boundaries.getTopLeft();
    boundaries = boundaries.moveToOrigin();
    int rows = boundaries.getMaxY() + 1;
    int columns = boundaries.getMaxX() + 1;
    colors = new Color[rows][];
    for (int row = 0; row < rows; ++row) {
      colors[row] = new Color[columns];
    }
  }

  public void paint(List<Edge> edges) {
    for (Edge edge : edges) {
      edge = edge.minus(shift);
      paintEdge(edge);
    }
  }

  private void paintEdge(Edge edge) {
    Vector cursor = edge.start();
    Vector step = findStep(edge);
    do {
      colors[cursor.y()][cursor.x()] = edge.color();
      cursor = cursor.plus(step);
    } while (!cursor.equals(edge.end()));
    colors[cursor.y()][cursor.x()] = edge.color(); // Pain the last position as well
  }

  private Vector findStep(Edge edge) {
    return edge.end().minus(edge.start()).scaleToOneUnit();
  }

  public void findInteriorArea() {
    Vector cursor = findInteriorPoint();
    if (cursor == null) {
      throw new IllegalStateException("Could not find an interior point");
    }
    fillWithColor(cursor);
  }

  private Vector findInteriorPoint() {
    Vector interior = null;
    int row = 1;
    int column = 1;
    while (interior == null && row < colors.length - 1 && column < colors[0].length - 1) {
      if (isInterior(row, column)) {
        interior = new Vector(column, row);
      } else {
        ++column;
        if (column == colors[0].length - 2) {
          column = 1;
          ++row;
        }
      }
    }
    return interior;
  }

  private boolean isInterior(int row, int column) {
    return colors[row][column] == null && oddBoundariesAbove(row, column);
  }

  private boolean oddBoundariesAbove(int row, int column) {
    int boundaryCount = 0;
    for (int r = 0; r < row; ++r) {
      if (isBoundary(r, column)) {
        ++boundaryCount;
      }
    }
    return (boundaryCount % 2) == 1;
  }

  private boolean isBoundary(int row, int column) {
    return colors[row][column] != null;
  }

  private void fillWithColor(Vector start) {
    Queue<Vector> toVisit = new ArrayDeque<>();
    colors[start.y()][start.x()] = INTERIOR_COLOR;
    toVisit.add(start);
    while (!toVisit.isEmpty()) {
      Vector cursor = toVisit.poll();
      Logger.info(cursor.toString());
      tryMove(cursor, NORTH, toVisit);
      tryMove(cursor, WEST, toVisit);
      tryMove(cursor, EAST, toVisit);
      tryMove(cursor, SOUTH, toVisit);
    }
  }

  private void tryMove(Vector position, Direction direction, Queue<Vector> toVisit) {
    position = position.step(direction);
    if (position.x() >= 0 && position.x() < colors[0].length
        && position.y() >= 0 && position.y() < colors.length
        && colors[position.y()][position.x()] == null) {
      colors[position.y()][position.x()] = INTERIOR_COLOR;
      toVisit.add(position);
    }
  }

  public int getTotalSize() {
    int size = 0;
    for (int row = 0; row < colors.length; ++row) {
      for (int column = 0; column < colors[0].length; ++column) {
        if (colors[row][column] != null) {
          size++;
        }
      }
    }
    return size;
  }

  public CharArrayGrid createDebugGrid() {
    CharArrayGrid grid = new CharArrayGrid();
    for (int row = 0; row < colors.length; ++row) {
      grid.appendRow(createDebugRow(row));
    }
    return grid;
  }

  private String createDebugRow(int row) {
    StringBuilder sb = new StringBuilder();
    for (int column = 0; column < colors[row].length; ++column) {
      Color c = colors[row][column];
      sb.append(c == INTERIOR_COLOR ? "I" : (c != null ? "#" : "."));
    }
    return sb.toString();
  }
}
