package problem.day15;

import java.util.ArrayList;
import java.util.List;

/**
 * A box of lenses.
 */
public class LensBox {
  private final List<Lens> lenses = new ArrayList<>();

  /**
   * Insert a lens in the box, or replace an existing lens with the same label.
   *
   * @param lens The lens to insert
   */
  public void insert(Lens lens) {
    int existingIndex = findIndexOf(lens.getLabel());
    if (existingIndex >= 0) {
      lenses.get(existingIndex).setFocalLength(lens.getFocalLength());
    } else {
      lenses.add(lens);
    }
  }

  private int findIndexOf(String label) {
    boolean found = false;
    int i = 0;
    while (!found && i < lenses.size()) {
      Lens l = lenses.get(i);
      if (l.getLabel().equals(label)) {
        found = true;
      } else {
        i++;
      }
    }
    return found ? i : -1;
  }

  /**
   * Remove a lens with the given label. If no lens has the given label, no changes are made.
   *
   * @param label The label of the lens to remove
   */
  public void remove(String label) {
    int index = findIndexOf(label);
    if (index >= 0) {
      lenses.remove(index);
    }
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (Lens lens : lenses) {
      sb.append(lens);
      sb.append(" ");
    }
    return sb.toString();
  }

  /**
   * Get the total focusing power of all lenses within this box.
   *
   * @return The focusing power, must be multiplied with the box index!
   */
  public long getFocusingPower() {
    long power = 0;
    long lensIndex = 1;
    for (Lens lens : lenses) {
      power += lensIndex * lens.getFocalLength();
      lensIndex++;
    }
    return power;
  }
}
