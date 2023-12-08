package problem.day08;

public class Directions {

  private final String directionString;
  private int currentPosition;

  public Directions(String directionString) {
    this.directionString = directionString;
    this.currentPosition = 0;
  }

  public char getNext() {
    char direction = directionString.charAt(currentPosition);
    currentPosition = (currentPosition + 1) % directionString.length();
    return direction;
  }

  public void reset() {
    currentPosition = 0;
  }
}
