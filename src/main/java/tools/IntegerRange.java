package tools;

import java.util.Objects;

/**
 * A range of integers.
 */
public class IntegerRange {
  private long start;
  private long end;

  /**
   * Create an integer range, which spans from the start to the end, inclusive.
   *
   * @param start The start of the range
   * @param end   The end of the range
   * @throws IllegalArgumentException If start > end
   */
  public IntegerRange(long start, long end) throws IllegalArgumentException {
    this.start = start;
    this.end = end;
    validate();
  }

  private void validate() {
    if (start > end) {
      throw new IllegalArgumentException("Start (" + start + ") can't be higher than end("
          + end + ")");
    }
  }

  /**
   * Check whether this range includes range r fully.
   *
   * @param r The range to check for
   * @return True if this range includes range r fully, false otherwise
   */
  public boolean containsFully(IntegerRange r) {
    return r != null && start <= r.start && end >= r.end;
  }

  /**
   * Check whether this starts within the range r.
   *
   * @param r The range to check
   * @return True if this starts inside range r
   */
  public boolean startsWithin(IntegerRange r) {
    return this.start >= r.start && this.start <= r.end;
  }

  /**
   * Check whether this end within the range r.
   *
   * @param r The range to check
   * @return True if this end inside range r
   */
  public boolean endsWithin(IntegerRange r) {
    return this.end >= r.start && this.end <= r.end;
  }

  @Override
  public String toString() {
    return "[" + start + ".." + end + "]";
  }

  /**
   * Get the end of the range.
   *
   * @return The integer representing the end of the range
   */
  public long getEnd() {
    return end;
  }

  /**
   * Get the start of the interval.
   *
   * @return The integer value representing the start of the interval
   */
  public long getStart() {
    return start;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    IntegerRange that = (IntegerRange) o;
    return start == that.start && end == that.end;
  }

  @Override
  public int hashCode() {
    return Objects.hash(start, end);
  }

  public boolean overlapsWith(IntegerRange r) {
    return this.startsWithin(r) || this.endsWithin(r) || this.containsFully(r);
  }

  /**
   * Merge this range with another range. Note: this range is unchanged!
   *
   * @param r The new range to merge with
   * @return A new range where this and r are merged
   * @throws IllegalArgumentException When this and r don't overlap
   */
  public IntegerRange mergeWith(IntegerRange r) throws IllegalArgumentException {
    if (!this.overlapsWith(r)) {
      throw new IllegalArgumentException("Can't merge two non-overlapping ranges "
          + this + " and " + r);
    }

    return new IntegerRange(Math.min(this.start, r.start), Math.max(this.end, r.end));
  }

  /**
   * Check if this range contains value v.
   *
   * @param v The value to check
   * @return True if v fits within this range
   */
  public boolean containsValue(long v) {
    return start <= v && end >= v;
  }

  /**
   * Get the length of the range, inclusive start and end.
   *
   * @return The length of the range.
   */
  public long getLength() {
    return end - start + 1;
  }

  /**
   * Set a new start value for the range.
   *
   * @param newStart The new start value
   * @throws IllegalArgumentException when start > end
   */
  public void setStart(long newStart) throws IllegalArgumentException {
    this.start = newStart;
    validate();
  }

  /**
   * Set a new end value for the range.
   *
   * @param newEnd The new end value
   * @throws IllegalArgumentException when start > end
   */
  public void setEnd(long newEnd) throws IllegalArgumentException {
    this.end = newEnd;
    validate();
  }

}
