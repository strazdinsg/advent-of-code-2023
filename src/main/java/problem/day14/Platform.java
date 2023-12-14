package problem.day14;

import java.util.List;
import tools.CharArrayGrid;
import tools.CharacterGrid;
import tools.Vector;

/**
 * A platform with some rocks on it.
 */
public class Platform {
  private static final char ROUNDED_ROCK = 'O';
  private static final char EMPTY = '.';
  private final CharArrayGrid grid;

  public Platform(CharArrayGrid grid) {
    this.grid = grid;
  }

  /**
   * Get the total vertical load of the platform.
   *
   * @return The total vertical load for the platform
   */
  public long getTotalVerticalLoad() {
    long load = 0;
    List<Vector> roundRockPositions = grid.findCharacterLocations(ROUNDED_ROCK);
    for (Vector position : roundRockPositions) {
      load += getLoadFor(position);
    }
    return load;
  }

  private long getLoadFor(Vector position) {
    return grid.getRowCount() - position.y();
  }

  /**
   * Tilt the platform to the north leading to some rocks rolling to the north.
   */
  public void tiltNorth() {
    for (int row = 0; row < grid.getRowCount(); ++row) {
      for (int column = 0; column < grid.getColumnCount(); ++column) {
        if (isRoundedRock(row, column)) {
          rollRockNorth(row, column);
        }
      }
    }
  }

  private boolean isRoundedRock(int row, int column) {
    return grid.getCharacter(row, column) == ROUNDED_ROCK;
  }

  private boolean isEmpty(int row, int column) {
    return grid.getCharacter(row, column) == EMPTY;
  }

  private void rollRockNorth(int row, int column) {
    int newRow = findLastEmptyColumnAbove(row, column);
    if (newRow >= 0) {
      move(row, column, newRow, column);
    }
  }

  private int findLastEmptyColumnAbove(int row, int column) {
    int y = row - 1;
    while (y >= 0 && isEmpty(y, column)) {
      --y;
    }
    y++; // Move back down one row
    return isEmpty(y, column) ? y : -1;
  }

  private void move(int row, int column, int newRow, int newColumn) {
    char c = grid.getCharacter(row, column);
    grid.setCharacter(newRow, newColumn, c);
    grid.setCharacter(row, column, EMPTY);
  }

  public CharacterGrid getGrid() {
    return grid;
  }
}
