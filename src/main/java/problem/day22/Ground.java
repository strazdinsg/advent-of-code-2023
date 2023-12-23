package problem.day22;

import tools.Vector;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.Stack;

public class Ground {

  private static final int WIDTH = 10;
  private static final int BREADTH = 10;
  private final PriorityQueue<Brick> bricksInAir = new PriorityQueue<>();
  private Stack<Brick>[][] stacks;
  private List<Brick> landedBricks = new LinkedList<>();

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

  public void letBricksFall() {
    while (!bricksInAir.isEmpty()) {
      Brick brick = bricksInAir.poll();
      landedBricks.add(land(brick));
    }
  }

  private Brick land(Brick brick) {
    Set<Brick> supportingBricks = new HashSet<>();
    HorizontalArea area = brick.getBottomArea();
    Vector cube = area.start();
    int landedHeight = brick.bottom.z();
    do {
      Brick supportOfCell = getSupportAt(cube);
      int height = supportOfCell.top.z();
      if (height > brick.bottom.z()) {
        throw new IllegalStateException("Brick " + brick + " intercepted by " + supportOfCell);
      }
      if (height == landedHeight) {
        // This is one of several supports
      } else if (height > landedHeight) {
        // New supporting brick found
        supportingBricks.clear();
        supportingBricks.add(supportOfCell);
        landedHeight = height;
      }
      cube = area.moveTowardsEnd(cube);
    } while (cube != area.end());
    return brick.dropOnTopOf(supportingBricks);
  }

  private Brick getSupportAt(Vector cube) {
    return null; // TODO
  }

  public int getRemovableBrickCount() {
    Set<Brick> removable = new HashSet<>();
    removable.addAll(getNonUniqueSupportBricks());
    removable.addAll(getFreeStandingBricks());
    return removable.size();
  }

  private Set<Brick> getNonUniqueSupportBricks() {
    Set<Brick> removableSupports = new HashSet<>();
    for (Brick brick : landedBricks) {
      Set<Brick> supporting = brick.getSupportedBy();
      if (supporting.size() > 1) {
        removableSupports.addAll(supporting);
      }
    }
    return removableSupports;
  }

  private Set<Brick> getFreeStandingBricks() {
    Set<Brick> freeStanding = new HashSet<>();
    for (Brick brick : landedBricks) {
      if (brick.isFreeStanding()) {
        freeStanding.add(brick);
      }
    }
    return freeStanding;
  }
}
