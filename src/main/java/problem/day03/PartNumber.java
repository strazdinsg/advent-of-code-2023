package problem.day03;

import tools.Rectangle;

/**
 * Represents a part number.
 *
 * @param number   The part number
 * @param position The position where it was located in the map
 */
public record PartNumber(long number, Rectangle position) {
  /**
   * Get the row index (Y-position) of this number.
   *
   * @return The Y-position of this number
   */
  int getRowIndex() {
    return position.getMaxY();
  }

  /**
   * Get the column position where the first digit was located in the map.
   *
   * @return The X position of the first digit
   */
  int getStartColumn() {
    return position.getMinX();
  }


  /**
   * Get the column position where the last digit was located in the map.
   *
   * @return The X position of the last digit
   */
  int getEndColumn() {
    return position.getMaxX();
  }
}
