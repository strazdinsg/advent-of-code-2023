package problem.day15;

/**
 * A lens.
 */
public final class Lens {
  private final String label;
  private int focalLength;

  /**
   * Create a lens.
   *
   * @param label       Label of the lens
   * @param focalLength The focal length of the lens
   */
  public Lens(String label, int focalLength) {
    this.label = label;
    this.focalLength = focalLength;
  }

  @Override
  public String toString() {
    return label + "=" + focalLength;
  }

  public String getLabel() {
    return label;
  }

  public int getFocalLength() {
    return focalLength;
  }

  public void setFocalLength(int focalLength) {
    this.focalLength = focalLength;
  }

}
