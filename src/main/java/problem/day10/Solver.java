package problem.day10;


import tools.InputFile;
import tools.Logger;
import tools.StringGrid;
import tools.Vector;
import java.util.LinkedList;
import java.util.List;

/**
 * Solution for the problem of Day 10
 * See description here: https://adventofcode.com/2023/day/10
 */
public class Solver {
  private static final char START_SYMBOL = 'S';
  private static final char VISITED = 'V';
  private static final char NORTH_SOUTH = '|';
  private static final char SOUTH_EAST = 'F';
  private static final char SOUTH_WEST = '7';
  private static final char NORTH_EAST = 'L';
  private static final char NORTH_WEST = 'J';
  private static final char EAST_WEST = '-';
  StringGrid grid;

  /**
   * Run the solver - solve the puzzle.
   *
   * @param args Command line arguments, not used (enforced by Java).
   */
  public static void main(String[] args) {
    Logger.info("Starting...");
    Solver solver = new Solver();
    solver.solve();
  }

  private void solve() {
    InputFile inputFile = new InputFile("problem10.input");
    if (!inputFile.exists()) {
      Logger.error("Input file not found");
      return;
    }

    grid = inputFile.readAllIntoGridBuffer();
    Vector start = findStartPosition();
    Logger.info("Start position: " + start);
    List<Vector> loop = findLoop(start);
    long steps = loop.size() / 2;
    Logger.info("The furthest point is " + steps + " steps away");
  }

  private Vector findStartPosition() {
    for (int row = 0; row < grid.getRowCount(); row++) {
      for (int column = 0; column < grid.getColumnCount(); ++column) {
        if (grid.getCharacter(row, column) == START_SYMBOL) {
          return new Vector(column, row);
        }
      }
    }
    throw new IllegalStateException("Start position not found");
  }

  private List<Vector> findLoop(Vector start) {
    List<Vector> loop = new LinkedList<>();
    Vector position = start;
    do {
      loop.add(position);
      Vector adjacent = findAdjacentNonVisited(position);
      markAsVisited(position);
      position = adjacent;
    } while (position != null);
    return loop;
  }

  private void markAsVisited(Vector position) {
    grid.replaceCharacter(position.y(), position.x(), VISITED);
  }

  private Vector findAdjacentNonVisited(Vector position) {
    Vector found = null;
    if (position.y() > 0) {
      Vector top = new Vector(position.x(), position.y() - 1);
      if (canGoUpFrom(position) && canGoDownFrom(top)) {
        Logger.info("Moving up to " + grid.getCharacter(top));
        found = top;
      }
    }

    if (found == null && position.y() < grid.getRowCount() - 1) {
      Vector bottom = new Vector(position.x(), position.y() + 1);
      if (canGoDownFrom(position) && canGoUpFrom(bottom)) {
        Logger.info("Moving down to " + grid.getCharacter(bottom));
        found = bottom;
      }
    }

    if (found == null && position.x() > 0) {
      Vector left = new Vector(position.x() - 1, position.y());
      if (canGoLeftFrom(position) && canGoRightFrom(left)) {
        Logger.info("Moving left to " + grid.getCharacter(left));
        found = left;
      }
    }

    if (found == null && position.x() < grid.getColumnCount() - 1) {
      Vector right = new Vector(position.x() + 1, position.y());
      if (canGoRightFrom(position) && canGoLeftFrom(right)) {
        Logger.info("Moving right to " + grid.getCharacter(right));
        found = right;
      }
    }

    Logger.info("   -> " + found);

    return found;
  }

  private boolean canGoDownFrom(Vector position) {
    char c = grid.getCharacter(position.y(), position.x());
    return c == NORTH_SOUTH || c == SOUTH_WEST || c == SOUTH_EAST
        || c == START_SYMBOL;
  }

  private boolean canGoUpFrom(Vector position) {
    char c = grid.getCharacter(position.y(), position.x());
    return c == NORTH_SOUTH || c == NORTH_EAST || c == NORTH_WEST
        || c == START_SYMBOL;
  }

  private boolean canGoRightFrom(Vector position) {
    char c = grid.getCharacter(position.y(), position.x());
    return c == EAST_WEST || c == NORTH_EAST || c == SOUTH_EAST
        || c == START_SYMBOL;
  }

  private boolean canGoLeftFrom(Vector position) {
    char c = grid.getCharacter(position.y(), position.x());
    return c == EAST_WEST || c == NORTH_WEST || c == SOUTH_WEST
        || c == START_SYMBOL;
  }
}


