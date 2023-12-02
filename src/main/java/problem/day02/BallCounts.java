package problem.day02;

/**
 * Contains number of ball for each color.
 *
 * @param red   Number of the red balls.
 * @param green Number of the green balls.
 * @param blue  Number of the blue balls.
 */
public record BallCounts(long red, long green, long blue) {
  @Override
  public String toString() {
    return red + ", " + green + ", " + blue;
  }
}
