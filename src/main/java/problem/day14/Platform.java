package problem.day14;

import java.util.List;
import tools.CharArrayGrid;
import tools.CharacterGrid;
import tools.Direction;
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
  public void tilt(Direction direction) {
    int horizontalGradient = direction.getHorizontalGradient();
    int verticalGradient = direction.getVerticalGradient();
    List<Vector> rocks = grid.findCharacterLocations(ROUNDED_ROCK);
    for (Vector rockPosition : rocks) {
      rollRock(rockPosition, verticalGradient, horizontalGradient);
    }
  }

  private boolean isEmpty(int row, int column) {
    return row >= 0 && row < grid.getRowCount() && column >= 0 && column < grid.getColumnCount()
        && grid.getCharacter(row, column) == EMPTY;
  }

  private void rollRock(Vector rockPosition, int verticalGradient, int horizontalGradient) {
    Vector newPosition = findLastEmptyCell(rockPosition, verticalGradient, horizontalGradient);
    if (newPosition != null) {
      move(rockPosition, newPosition);
    }
  }

  private Vector findLastEmptyCell(Vector rockPosition, int verticalGradient,
                                   int horizontalGradient) {
    int row = rockPosition.y() + verticalGradient;
    int column = rockPosition.x() + horizontalGradient;

    while (isEmpty(row, column)) {
      row += verticalGradient;
      column += horizontalGradient;
    }
    row -= verticalGradient; // Move back one step
    column -= horizontalGradient;
    return isEmpty(row, column) ? new Vector(column, row) : null;
  }

  private void move(Vector position, Vector newPosition) {
    char c = grid.getCharacter(position);
    grid.setCharacter(newPosition.y(), newPosition.x(), c);
    grid.setCharacter(position.y(), position.x(), EMPTY);
  }

  public CharacterGrid getGrid() {
    return grid;
  }

  /**
   * Spin the platform for one cycle: north, west, south, east.
   */
  public void spinOneCycle() {
    tilt(Direction.NORTH);
    tilt(Direction.WEST);
    tilt(Direction.SOUTH);
    tilt(Direction.EAST);
  }
}
