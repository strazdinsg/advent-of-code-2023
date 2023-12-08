package tools;

/**
 * Some commonly used algorithms.
 */
public class Algos {
  /**
   * Not allowed to instantiate this class.
   */
  private Algos() {}

  /**
   * Find the least common multiplier of a and b.
   * Code adapted from https://www.baeldung.com/java-least-common-multiple
   *
   * @param a The first integer
   * @param b The second integer
   * @return The least common multiplier of a and b
   */
  public static long leastCommonMultiplier(long a, long b) {
    if (a == 0 || b == 0) {
      return 0;
    }
    a = Math.abs(a);
    b = Math.abs(b);
    long highest = Math.max(a, b);
    long lowest = Math.min(a, b);
    long lcm = highest;
    while (lcm % lowest != 0) {
      lcm += highest;
    }
    return lcm;
  }
}
