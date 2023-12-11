package problem.day11;

import tools.StringGrid;
import tools.Vector;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GalaxyMap {
  private static final char EMPTY = '.';
  private static final char GALAXY = '#';
  private StringGrid grid;
  private List<Vector> galaxies = new ArrayList<>();

  public GalaxyMap(StringGrid grid) {
    this.grid = grid;
  }

  public void expand() {
    Set<Integer> emptyRows = findEmptyRows();
    Set<Integer> emptyColumns = findEmptyColumns();
    grid = expandGrid(emptyRows, emptyColumns);
  }

  private StringGrid expandGrid(Set<Integer> emptyRows, Set<Integer> emptyColumns) {
    StringGrid newGrid = new StringGrid();
    String emptyRow = createEmptyRow(grid.getColumnCount() + emptyColumns.size());
    for (int row = 0; row < grid.getRowCount(); ++row) {
      if (emptyRows.contains(row)) {
        newGrid.appendRow(emptyRow);
        newGrid.appendRow(emptyRow);
      } else {
        newGrid.appendRow(createExpandedRow(grid.getRow(row), emptyColumns));
      }
    }
    return newGrid;
  }

  private String createExpandedRow(String row, Set<Integer> emptyColumns) {
    StringBuilder sb = new StringBuilder();
    for (int column = 0; column < row.length(); ++column) {
      sb.append(row.charAt(column));
      if (emptyColumns.contains(column)) {
        sb.append(EMPTY);
      }
    }
    return sb.toString();
  }

  private String createEmptyRow(int columnCount) {
    return ".".repeat(columnCount);
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

  public void findGalaxies() {
    galaxies = grid.findCharacterLocations(GALAXY);
  }

  public long getDistanceSum() {
    long distanceSum = 0;
    for (int i = 0; i < galaxies.size(); ++i) {
      for (int j = i + 1; j < galaxies.size(); ++j) {
        distanceSum += calculateDistanceBetween(galaxies.get(i), galaxies.get(j));
      }
    }
    return distanceSum;
  }

  private long calculateDistanceBetween(Vector galaxy1, Vector galaxy2) {
    Vector distance = galaxy2.minus(galaxy1);
    return distance.getAbsoluteX() + distance.getAbsoluteY();
  }

  public StringGrid getGrid() {
    return grid;
  }
}
