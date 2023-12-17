package problem.day17;

import tools.CharArrayGrid;
import tools.Direction;
import tools.Logger;
import tools.Vector;
import java.util.ArrayDeque;
import java.util.Queue;

public class Maze {
  private final CharArrayGrid grid;
  private Block[][] blocks;
  private final Queue<Vector> toVisit = new ArrayDeque<>();

  public Maze(CharArrayGrid grid) {
    this.grid = grid;
    initializeBlocks();
  }

  private void initializeBlocks() {
    blocks = new Block[grid.getRowCount()][];
    for (int row = 0; row < grid.getRowCount(); ++row) {
      blocks[row] = new Block[grid.getColumnCount()];
      for (int column = 0; column < grid.getColumnCount(); ++column) {
        int heatLossInBlock = grid.getCharacter(row, column) - '0';
        blocks[row][column] = new Block(heatLossInBlock);
      }
    }
  }

  public long findShortestPath() {
    toVisit.add(new Vector(0, 0));
    blocks[0][0].setAsStart();
    while (!toVisit.isEmpty()) {
      Vector position = toVisit.poll();
      Logger.info(position + ":");
      Block block = blocks[position.y()][position.x()];
      for (Direction direction : Direction.values()) {
        Vector nextPosition = position.step(direction);
        Block nextBlock = getBlock(nextPosition);
        if (nextBlock != null) {
          Logger.info("  " + nextPosition);
          if (block.moveTo(nextBlock, direction)) {
            toVisit.add(nextPosition);
          }
        }
      }
    }

    long loss = getMinimumLossFor(grid.getRowCount() - 1, grid.getColumnCount() - 1);
    if (loss == Long.MAX_VALUE) {
      throw new IllegalStateException("Did not find path to the destination!");
    }

    return loss;
  }

  private Block getBlock(Vector position) {
    return grid.isWithin(position) ? blocks[position.y()][position.x()] : null;
  }

  private long getMinimumLossFor(Vector newPosition) {
    return blocks[newPosition.y()][newPosition.x()].getMinimumHeatLoss();
  }

  private long getMinimumLossFor(int row, int column) {
    return getMinimumLossFor(new Vector(column, row));
  }
}
