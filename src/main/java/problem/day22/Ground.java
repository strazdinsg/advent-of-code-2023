package problem.day22;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.Stack;
import tools.Vector;

/**
 * A flat ground where the stacks of bricks can be located.
 */
public class Ground {

  private static final int WIDTH = 10;
  private static final int BREADTH = 10;
  private final PriorityQueue<Brick> bricksInAir = new PriorityQueue<>();
  private Stack<Brick>[][] stacks;
  private List<Brick> landedBricks = new LinkedList<>();

  /**
   * Create the ground.
   */
  public Ground() {
    this.stacks = new Stack[WIDTH][BREADTH];
    for (int i = 0; i < WIDTH; ++i) {
      for (int j = 0; j < BREADTH; ++j) {
        stacks[i][j] = new Stack<>();
      }
    }
  }

  public void addBrick(Brick brick) {
    bricksInAir.add(brick);
  }

  /**
   * Let all the bricks fall until they land.
   */
  public void letBricksFall() {
    while (!bricksInAir.isEmpty()) {
      Brick brick = bricksInAir.poll();
      landedBricks.add(land(brick));
    }
  }

  private Brick land(Brick brick) {
    Set<Brick> supportingBricks = new HashSet<>();
    HorizontalOneDimensionalBrick area = brick.getBottomArea();
    Vector cube = area.start();
    long landedHeight = 0;
    do {
      Brick supportingBrick = getSupportAt(cube);
      long supportHeight = supportingBrick != null ? supportingBrick.top.z() : 0;
      if (supportHeight >= brick.bottom.z()) {
        throw new IllegalStateException("Brick " + brick + " intercepted by " + supportingBrick);
      }
      if (supportHeight == landedHeight) {
        // This is one of several supports
        if (supportingBrick != null) {
          supportingBricks.add(supportingBrick);
        }
      } else if (supportHeight > landedHeight) {
        // New supporting brick found, all previous supports are not valid anymore
        supportingBricks.clear();
        supportingBricks.add(supportingBrick);
        landedHeight = supportHeight;
      }
      cube = area.moveTowardsEnd(cube);
    } while (area.isInside(cube));
    Brick landedBrick = brick.dropOnTopOf(supportingBricks, landedHeight);
    addToStacks(landedBrick);
    return landedBrick;
  }

  private Brick getSupportAt(Vector position) {
    Stack<Brick> stack = stacks[position.y()][position.x()];
    return !stack.isEmpty() ? stack.peek() : null;
  }

  private void addToStacks(Brick landedBrick) {
    HorizontalOneDimensionalBrick area = landedBrick.getBottomArea();
    Vector cube = area.start();
    while (area.isInside(cube)) {
      addToStack(cube, landedBrick);
      cube = area.moveTowardsEnd(cube);
    }
  }

  private void addToStack(Vector position, Brick brick) {
    Stack<Brick> stack = stacks[position.y()][position.x()];
    stack.push(brick);
  }

  /**
   * Get the number of bricks which can be removed without causing other bricks to fail.
   *
   * @return The number of removable bricks
   */
  public int getRemovableBrickCount() {
    Set<Brick> removable = new HashSet<>(landedBricks);
    for (Brick brick : landedBricks) {
      Brick singleSupport = brick.getSingleSupport();
      if (singleSupport != null) {
        removable.remove(singleSupport);
      }
    }
    return removable.size();
  }
}
