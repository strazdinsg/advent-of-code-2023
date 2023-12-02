package problem.day02;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Represents a ball-bag game.
 */
public class Game {
  private final long id;
  private final List<BallCounts> tries = new LinkedList<>();

  /**
   * Create a game.
   *
   * @param id ID of this game
   */
  public Game(long id) {
    this.id = id;
  }

  /**
   * Check whether it would be possible to perform this game with the given number of balls.
   *
   * @param redBalls   The number of red balls i the bag.
   * @param greenBalls The number of green balls in the bag.
   * @param blueBalls  The number of blue balls in the bag.
   * @return True if it would be possible to run this game, false otherwise.
   */
  public boolean isPossibleWith(int redBalls, int greenBalls, int blueBalls) {
    boolean possible = true;
    Iterator<BallCounts> it = tries.iterator();

    while (possible && it.hasNext()) {
      BallCounts oneTry = it.next();
      possible = oneTry.red() <= redBalls
          && oneTry.green() <= greenBalls
          && oneTry.blue() <= blueBalls;
    }

    return possible;
  }

  /**
   * Get the ID of the game.
   *
   * @return The ID of the game.
   */
  public long getId() {
    return id;
  }

  /**
   * Add one try (a set of balls retrieved from the bag) to the game.
   *
   * @param ballCounts The number of balls representing one try of the game
   */
  public void addTry(BallCounts ballCounts) {
    tries.add(ballCounts);
  }

  @Override
  public String toString() {
    return "Game #" + id + ": " + getTriesAsString();
  }

  private String getTriesAsString() {
    StringBuilder sb = new StringBuilder();
    for (BallCounts bc : tries) {
      sb.append(bc.toString());
      sb.append("; ");
    }
    return sb.toString();
  }
}
