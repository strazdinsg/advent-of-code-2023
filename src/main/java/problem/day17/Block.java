package problem.day17;

import java.util.HashMap;
import java.util.Map;
import tools.Direction;
import tools.Logger;

/**
 * One block of the maze, with information about the visits: what energy loss can be achieved by
 * arriving from different directions.
 */
public final class Block {

  private final Map<DirectionalMove, Long> entries = new HashMap<>();
  private final long heatLoss;

  /**
   * Create a new block.
   *
   * @param heatLoss How much heat is lost within this block
   */
  public Block(long heatLoss) {
    this.heatLoss = heatLoss;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (Map.Entry<DirectionalMove, Long> entry : entries.entrySet()) {
      DirectionalMove move = entry.getKey();
      Long loss = entry.getValue();
      sb.append(move.moves());
      sb.append("x");
      sb.append(move.direction());
      sb.append("=");
      sb.append(loss);
      sb.append("; ");
    }
    return sb.toString();
  }

  /**
   * Get the minimum heat loss achievable, considering all the valid paths leading to this block.
   *
   * @return The minimum heat loss, Long.MAX_VALUE if this block is not reachable
   */
  public long getMinimumHeatLoss() {
    long minimumHeatLoss = Long.MAX_VALUE;
    for (Map.Entry<DirectionalMove, Long> entry : entries.entrySet()) {
      DirectionalMove move = entry.getKey();
      if (move.isValid()) {
        Long loss = entry.getValue();
        minimumHeatLoss = Math.min(loss, minimumHeatLoss);
      }
    }
    return minimumHeatLoss;
  }

  /**
   * Try to move from this block in the specified direction and reach nextBlock.
   *
   * @param nextBlock The next block to reach
   * @param direction The direction of move
   * @return True if the next block was reached in at least one way
   */
  public boolean moveTo(Block nextBlock, Direction direction) {
    boolean moved = false;
    for (Map.Entry<DirectionalMove, Long> entry : entries.entrySet()) {
      DirectionalMove entryMove = entry.getKey();
      DirectionalMove nextMove = entryMove.step(direction);
      if (nextMove != null) {
        Long loss = entry.getValue();
        boolean arrived = nextBlock.arrive(nextMove, loss);
        moved = moved || arrived;
      }
    }
    return moved;
  }

  /**
   * Arrive to this block with a given entry move.
   *
   * @param entryMove        The move leading to this block
   * @param previousHeatLoss How much heat was lost previously along the journey
   */
  private boolean arrive(DirectionalMove entryMove, long previousHeatLoss) {
    boolean arrived = false;
    long loss = previousHeatLoss + this.heatLoss;
    Long oldLoss = entries.get(entryMove);
    if (oldLoss == null || oldLoss > loss) {
      Logger.info("    " + entryMove + " lose " + loss);
      entries.put(entryMove, loss);
      arrived = true;
    }
    return arrived;
  }

  /**
   * Set this block as the entry point for the maze.
   */
  public void setAsStart() {
    for (Direction direction : Direction.values()) {
      entries.put(new DirectionalMove(direction, 0), 0L);
    }
  }
}
