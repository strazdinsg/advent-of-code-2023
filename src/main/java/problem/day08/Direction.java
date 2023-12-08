package problem.day08;

public enum Direction {
  LEFT, RIGHT;

  public static Direction fromChar(char c) {
    return switch (c) {
      case 'L' -> LEFT;
      case 'R' -> RIGHT;
      default -> throw new IllegalArgumentException("Invalid direction: " + c);
    };
  }
}
