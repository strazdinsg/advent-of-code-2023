package problem.day12;

import java.util.HashMap;
import java.util.Map;

/**
 * A cache for pattern counts.
 */
public class PatternCountCache {
  private final Map<Integer, Map<Integer, Long>> cache = new HashMap<>();

  /**
   * Find cached pattern count for a given pattern, at given position.
   *
   * @param patternIndex The index of the pattern
   * @param position     The position at which the pattern is now placed
   * @return The cached number of placements of the remaining patterns, when the given pattern
   *     is at this position
   */
  public Long load(int patternIndex, int position) {
    Map<Integer, Long> patternCache = cache.get(patternIndex);
    return patternCache != null ? patternCache.get(position) : null;
  }

  /**
   * Store the number of patterns, when the given pattern is at the given position.
   *
   * @param patternIndex           The index of the given pattern
   * @param position               The position of the given pattern
   * @param patternsAtThisPosition The number of patterns with this pattern at the given position
   */
  public void store(int patternIndex, int position, long patternsAtThisPosition) {
    Map<Integer, Long> patternCache = cache.computeIfAbsent(patternIndex, k -> new HashMap<>());
    patternCache.put(position, patternsAtThisPosition);
  }
}
