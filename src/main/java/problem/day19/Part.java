package problem.day19;

public record Part(int x, int m, int a, int s) {
  public long getPropertySum() {
    return x + m + a + s;
  }

  public int getPropertyValue(char property) {
    return switch (property) {
      case 'x' -> x;
      case 'm' -> m;
      case 'a' -> a;
      case 's' -> s;
      default -> throw new IllegalArgumentException("Invalid property: " + property);
    };
  }
}
