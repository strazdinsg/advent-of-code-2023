package tools;

import java.util.List;

/**
 * Some commonly used algorithms.
 */
public class Algos {
  /**
   * Not allowed to instantiate this class.
   */
  private Algos() {
  }

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

  /**
   * convert a List to array with the same values.
   *
   * @param values The value list
   * @return An array with the same values
   */
  public static long[] listToArray(List<Long> values) {
    int n = values.size();
    long[] v = new long[n];
    for (int i = 0; i < n; ++i) {
      v[i] = values.get(i);
    }
    return v;
  }
}
