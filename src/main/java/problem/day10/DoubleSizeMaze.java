package problem.day10;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;
import tools.StringGrid;
import tools.Vector;

/**
 * Represents a maze where the size of the grid is doubled - empty spaces filled in the odd
 * places in the grid. These will be filled inn with walls (or empty space) when the loop is found.
 */
public class DoubleSizeMaze {
  private static final char START_SYMBOL = 'S';
  private static final char VISITED = 'V';
  private static final char NORTH_SOUTH = '|';
  private static final char SOUTH_EAST = 'F';
  private static final char SOUTH_WEST = '7';
  private static final char NORTH_EAST = 'L';
  private static final char NORTH_WEST = 'J';
  private static final char EAST_WEST = '-';
  private static final char EMPTY = ' ';
  private static final char WATER = '~';

  private Queue<Vector> waterCellQueue = new ArrayDeque<>();

  private final char[][] grid;
  private final Set<Vector> loopCells = new HashSet<>();

  /**
   * Create a double sized maze.
   *
   * @param originalGrid The original grid from the input file
   */
  public DoubleSizeMaze(StringGrid originalGrid) {
    int rows = originalGrid.getRowCount();
    int columns = originalGrid.getColumnCount();
    grid = new char[rows * 2][columns * 2];
    for (int row = 0; row < rows; ++row) {
      for (int column = 0; column < columns; ++column) {
        grid[row * 2][column * 2] = originalGrid.getCharacter(row, column);
        grid[row * 2][column * 2 + 1] = ' ';
        grid[row * 2 + 1][column * 2] = ' ';
        grid[row * 2 + 1][column * 2 + 1] = ' ';
      }
    }
  }

  /**
   * Find the start position within the maze.
   *
   * @return The start position, in the original coordinate system
   */
  public Vector findStartPosition() {
    for (int row = 0; row < getRowCount(); row++) {
      for (int column = 0; column < getColumnCount(); ++column) {
        if (getCharacter(row, column) == START_SYMBOL) {
          return new Vector(column, row);
        }
      }
    }
    throw new IllegalStateException("Start position not found");
  }

  private char getCharacter(int row, int column) {
    checkBounds(row, column);
    return grid[row * 2][column * 2];
  }

  private void checkBounds(int row, int column) {
    if (row < 0 || row >= getRowCount() || column < 0 || column >= getColumnCount()) {
      throw new IllegalArgumentException("Invalid coordinates: (" + column + ", " + row + ")");
    }
  }

  private int getColumnCount() {
    return grid[0].length / 2;
  }

  private int getRowCount() {
    return grid.length / 2;
  }

  /**
   * Convert this maze to a string grid format, for debugging.
   *
   * @return The StringGrid representation of this maze, including the inner cells which were
   *     not there in the original input
   */
  public StringGrid toGrid() {
    StringGrid sg = new StringGrid();
    for (char[] rowChars : grid) {
      StringBuilder sb = new StringBuilder();
      for (char c : rowChars) {
        sb.append(c);
      }
      sg.appendRow(sb.toString());
    }

    return sg;
  }

  /**
   * Find a loop in the maze.
   *
   * @param start The start position of the loop
   */
  public void findLoop(Vector start) {
    Vector position = start;
    Vector lastNonNull = start;
    do {
      loopCells.add(position);
      Vector next = moveToAdjacent(position);
      setCharacter(position, VISITED);
      position = next;
      if (position != null) {
        lastNonNull = position;
      }
    } while (position != null);
    drawWallBetween(start, lastNonNull);
  }

  private Vector moveToAdjacent(Vector position) {
    Vector next = tryGoUp(position);
    if (next == null) {
      next = tryGoDown(position);
    }
    if (next == null) {
      next = tryGoLeft(position);
    }
    if (next == null) {
      next = tryGoRight(position);
    }

    if (next != null) {
      drawWallBetween(position, next);
    }

    return next;
  }

  private Vector tryGoRight(Vector position) {
    Vector next = null;
    if (position.x() < getColumnCount() - 1) {
      Vector right = new Vector(position.x() + 1, position.y());
      if (canGoRightFrom(position) && canGoLeftFrom(right)) {
        next = right;
      }
    }
    return next;
  }

  private Vector tryGoLeft(Vector position) {
    Vector next = null;
    if (position.x() > 0) {
      Vector left = new Vector(position.x() - 1, position.y());
      if (canGoLeftFrom(position) && canGoRightFrom(left)) {
        next = left;
      }
    }
    return next;
  }

  private Vector tryGoUp(Vector position) {
    Vector next = null;
    if (position.y() > 0) {
      Vector top = new Vector(position.x(), position.y() - 1);
      if (canGoUpFrom(position) && canGoDownFrom(top)) {
        next = top;
      }
    }
    return next;
  }

  private Vector tryGoDown(Vector position) {
    Vector next = null;
    if (position.y() < getRowCount() - 1) {
      Vector bottom = new Vector(position.x(), position.y() + 1);
      if (canGoDownFrom(position) && canGoUpFrom(bottom)) {
        next = bottom;
      }
    }
    return next;
  }

  private void drawWallBetween(Vector pos1, Vector pos2) {
    int x = pos1.x() + pos2.x();
    int y = pos1.y() + pos2.y();
    boolean isVertical = pos1.x() == pos2.x();
    char wallSymbol = isVertical ? NORTH_SOUTH : EAST_WEST;
    grid[y][x] = wallSymbol;
  }

  public long getLoopLength() {
    return loopCells.size();
  }

  private boolean canGoDownFrom(Vector position) {
    char c = getCharacter(position.y(), position.x());
    return c == NORTH_SOUTH || c == SOUTH_WEST || c == SOUTH_EAST
        || c == START_SYMBOL;
  }

  private boolean canGoUpFrom(Vector position) {
    char c = getCharacter(position.y(), position.x());
    return c == NORTH_SOUTH || c == NORTH_EAST || c == NORTH_WEST
        || c == START_SYMBOL;
  }

  private boolean canGoRightFrom(Vector position) {
    char c = getCharacter(position.y(), position.x());
    return c == EAST_WEST || c == NORTH_EAST || c == SOUTH_EAST
        || c == START_SYMBOL;
  }

  private boolean canGoLeftFrom(Vector position) {
    char c = getCharacter(position.y(), position.x());
    return c == EAST_WEST || c == NORTH_WEST || c == SOUTH_WEST
        || c == START_SYMBOL;
  }

  private void setCharacter(Vector position, char c) {
    setCharacter(position.y(), position.x(), c);
  }

  private void setCharacter(int row, int column, char c) {
    grid[row * 2][column * 2] = c;
  }

  /**
   * Mark all the cells not belonging to the loop as empty.
   */
  public void markAllNonLoopCellsAsEmpty() {
    for (int row = 0; row < getRowCount(); ++row) {
      for (int column = 0; column < getColumnCount(); ++column) {
        if (getCharacter(row, column) != VISITED) {
          setCharacter(row, column, EMPTY);
        }
      }
    }
  }

  /**
   * Flood water starting at each of the boundary cells. The water will not flow into the cells
   * which are inside the loop (the area we are looking for will stay dry).
   */
  public void floodWaterFromOutside() {
    floodWaterInRawRow(0);
    floodWaterInRawRow(grid.length - 1);
    floodWaterInRawColumn(0);
    floodWaterInRawColumn(grid[0].length - 1);
  }

  private void floodWaterInRawColumn(int rawColumn) {
    for (int rawRow = 0; rawRow < grid.length; ++rawRow) {
      floodWaterFrom(rawRow, rawColumn);
    }
  }

  private void floodWaterInRawRow(int rawRow) {
    for (int rawColumn = 0; rawColumn < grid[0].length; ++rawColumn) {
      floodWaterFrom(rawRow, rawColumn);
    }
  }

  private void floodWaterFrom(int rawRow, int rawColumn) {
    waterCellQueue.clear();
    tryPourWater(new Vector(rawColumn, rawRow));
    while (!waterCellQueue.isEmpty()) {
      Vector rawPosition = waterCellQueue.remove();
      tryPourWater(rawPosition.plus(0, -1));
      tryPourWater(rawPosition.plus(0, 1));
      tryPourWater(rawPosition.plus(-1, 0));
      tryPourWater(rawPosition.plus(1, 0));
    }
  }

  private void tryPourWater(Vector rawPosition) {
    if (isRawCellEmpty(rawPosition)) {
      markAsWater(rawPosition);
      waterCellQueue.add(rawPosition);
    }
  }

  private boolean isRawCellEmpty(Vector rawPosition) {
    return rawPosition.y() >= 0 && rawPosition.y() < grid.length
        && rawPosition.x() >= 0 && rawPosition.x() < grid[0].length
        && grid[rawPosition.y()][rawPosition.x()] == EMPTY;
  }

  private void markAsWater(Vector rawPosition) {
    grid[rawPosition.y()][rawPosition.x()] = WATER;
  }

  /**
   * Count the dry cells, which are inside the loop.
   *
   * @return The number of cells which remained dry after flooding the water.
   */
  public long countDryInnerCells() {
    long dryCellCount = 0;
    for (int row = 0; row < getRowCount(); ++row) {
      for (int column = 0; column < getColumnCount(); ++column) {
        if (getCharacter(row, column) == EMPTY) {
          dryCellCount++;
        }
      }
    }
    return dryCellCount;
  }
}
