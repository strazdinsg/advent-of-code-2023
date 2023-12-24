package tools;

public enum Sign {
  POSITIVE, NEGATIVE;

  public static Sign from(double value) {
    return value >= 0 ? POSITIVE : NEGATIVE;
  }
}
