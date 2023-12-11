package problem.day11;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import tools.StringGrid;
import tools.Vector;

/**
 * A map of galaxies.
 */
public class GalaxyMap {
  private static final char EMPTY = '.';
  private static final char GALAXY = '#';
  private static final long SHORT_DISTANCE = 1;
  private static final long LONG_DISTANCE = 1000000;
  private StringGrid grid;
  private List<Vector> galaxies = new ArrayList<>();
  private long[] rowDistances;
  private long[] columnDistances;

  /**
   * Create a galaxy map.
   *
   * @param grid The grid representing the map
   */
  public GalaxyMap(StringGrid grid) {
    this.grid = grid;
  }

  /**
   * Find the empty columns and rows where the distances are expanded.
   */
  public void findExpandedDistances() {
    findRowDistances();
    findColumnDistances();
  }

  private void findRowDistances() {
    rowDistances = new long[grid.getRowCount()];
    Set<Integer> emptyRows = findEmptyRows();
    for (int row = 0; row < grid.getRowCount(); ++row) {
      if (emptyRows.contains(row)) {
        rowDistances[row] = LONG_DISTANCE;
      } else {
        rowDistances[row] = SHORT_DISTANCE;
      }
    }
  }

  private void findColumnDistances() {
    columnDistances = new long[grid.getColumnCount()];
    Set<Integer> emptyColumns = findEmptyColumns();
    for (int column = 0; column < grid.getColumnCount(); ++column) {
      if (emptyColumns.contains(column)) {
        columnDistances[column] = LONG_DISTANCE;
      } else {
        columnDistances[column] = SHORT_DISTANCE;
      }
    }
  }

  private Set<Integer> findEmptyRows() {
    Set<Integer> emptyRows = new HashSet<>();
    for (int row = 0; row < grid.getRowCount(); ++row) {
      if (isRowEmpty(row)) {
        emptyRows.add(row);
      }
    }
    return emptyRows;
  }

  private boolean isRowEmpty(int row) {
    boolean empty = true;
    int column = 0;
    while (empty && column < grid.getColumnCount()) {
      empty = grid.getCharacter(row, column) == EMPTY;
      column++;
    }
    return empty;
  }

  private Set<Integer> findEmptyColumns() {
    Set<Integer> emptyColumns = new HashSet<>();
    for (int column = 0; column < grid.getColumnCount(); ++column) {
      if (isColumnEmpty(column)) {
        emptyColumns.add(column);
      }
    }
    return emptyColumns;
  }

  private boolean isColumnEmpty(int column) {
    boolean empty = true;
    int row = 0;
    while (empty && row < grid.getRowCount()) {
      empty = grid.getCharacter(row, column) == EMPTY;
      ++row;
    }
    return empty;
  }

  /**
   * Find the locations of galaxies.
   */
  public void findGalaxies() {
    galaxies = grid.findCharacterLocations(GALAXY);
  }

  /**
   * Calculate distances between the galaxies.
   *
   * @return The sum of distances between the galaxies
   */
  public long calculateDistances() {
    long distanceSum = 0;
    for (int i = 0; i < galaxies.size(); ++i) {
      for (int j = i + 1; j < galaxies.size(); ++j) {
        distanceSum += calculateDistanceBetween(galaxies.get(i), galaxies.get(j));
      }
    }
    return distanceSum;
  }

  private long calculateDistanceBetween(Vector galaxy1, Vector galaxy2) {
    long rowDistance = getRowDistance(galaxy1, galaxy2);
    long columnDistance = getColumnDistance(galaxy1, galaxy2);
    return rowDistance + columnDistance;
  }

  private long getRowDistance(Vector galaxy1, Vector galaxy2) {
    int step = galaxy2.minus(galaxy1).y() >= 0 ? 1 : -1;
    long rowDistance = 0;
    for (int row = galaxy1.y() + step; row != galaxy2.y() + step; row += step) {
      rowDistance += rowDistances[row];
    }
    return rowDistance;
  }

  private long getColumnDistance(Vector galaxy1, Vector galaxy2) {
    int step = galaxy2.minus(galaxy1).x() >= 0 ? 1 : -1;
    long columnDistance = 0;
    for (int column = galaxy1.x() + step; column != galaxy2.x() + step; column += step) {
      columnDistance += columnDistances[column];
    }
    return columnDistance;
  }
}
