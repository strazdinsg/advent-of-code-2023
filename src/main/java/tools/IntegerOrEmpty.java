package tools;

/**
 * An integer which can have an empty value.
 */
public class IntegerOrEmpty {
  private final Long value;

  /**
   * An empty value.
   */
  public static final IntegerOrEmpty empty = new IntegerOrEmpty(null);


  /**
   * Create an instance.
   *
   * @param value The value to use, it can be null
   */
  public IntegerOrEmpty(Long value) {
    this.value = value;
  }

  /**
   * Create an integer object from a given string value.
   *
   * @param value The integer value represented as a string
   * @return An object representing the given integer value, null if it could not be parsed
   *     as a valid integer.
   */
  public static IntegerOrEmpty fromString(String value) {
    IntegerOrEmpty i;
    try {
      i = new IntegerOrEmpty(Long.parseLong(value));
    } catch (NumberFormatException e) {
      i = IntegerOrEmpty.empty;
    }
    return i;
  }

  /**
   * Check if the value is a number.
   *
   * @return True if a number value is stored here, false when it is empty.
   */
  public boolean isNumber() {
    return value != null;
  }

  /**
   * Get the value.
   *
   * @return The integer value.
   */
  public Long getValue() {
    return value;
  }

  @Override
  public String toString() {
    return isNumber() ? "" + value : "NaN";
  }
}
