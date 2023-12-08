package problem.day08;

/**
 * Holds a string with sequential directions.
 */
public class Directions {

  private final String directionString;
  private int currentPosition;

  public Directions(String directionString) {
    this.directionString = directionString;
    this.currentPosition = 0;
  }

  /**
   * Get the expected direction and advance to the next direction.
   *
   * @return The currently expected next direction
   */
  public char getNext() {
    char direction = directionString.charAt(currentPosition);
    currentPosition = (currentPosition + 1) % directionString.length();
    return direction;
  }

  /**
   * Reset the direction "pointer" to the start.
   */
  public void reset() {
    currentPosition = 0;
  }
}
