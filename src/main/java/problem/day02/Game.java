package problem.day02;

import java.util.LinkedList;
import java.util.List;

/**
 * Represents a ball-bag game.
 */
public class Game {
  private final long id;
  private long minRed = 0;
  private long minGreen = 0;
  private long minBlue = 0;
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
    return redBalls >= minRed && greenBalls >= minGreen && blueBalls >= minBlue;
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
    if (ballCounts.red() > minRed) {
      minRed = ballCounts.red();
    }
    if (ballCounts.green() > minGreen) {
      minGreen = ballCounts.green();
    }
    if (ballCounts.blue() > minBlue) {
      minBlue = ballCounts.blue();
    }
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

  /**
   * Get the minimum power of this game - minimum number of red, green and blue cubes multipled.
   *
   * @return The minimum power of the game.
   */
  public long getMinCubePower() {
    long power = minRed * minGreen * minBlue;
    if (power == 0) {
      throw new IllegalStateException("Power can't be zero: " + this);
    }
    return power;
  }
}
