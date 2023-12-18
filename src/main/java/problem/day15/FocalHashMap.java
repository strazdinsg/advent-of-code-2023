package problem.day15;

/**
 * A map containing boxes with lens.
 */
public class FocalHashMap {
  private final LensBox[] boxes;

  /**
   * Create a (hash) map.
   */
  public FocalHashMap() {
    boxes = new LensBox[256];
    for (int i = 0; i < boxes.length; ++i) {
      boxes[i] = new LensBox();
    }
  }

  /**
   * Process one instruction - either insert or remove.
   *
   * @param instruction The instruction to process
   */
  public void process(Instruction instruction) {
    if (instruction.isInsert()) {
      insert(instruction.label(), instruction.focalLength());
    } else {
      remove(instruction.label());
    }
  }

  private void insert(String label, int focalLength) {
    boxes[getBoxNumber(label)].insert(new Lens(label, focalLength));
  }

  private int getBoxNumber(String label) {
    return calculateHash(label);
  }

  private void remove(String label) {
    boxes[getBoxNumber(label)].remove(label);
  }

  /**
   * Get the total focusing power of the whole map.
   *
   * @return The focusing power
   */
  public long getFocusingPower() {
    long focusingPower = 0;
    for (int i = 0; i < boxes.length; ++i) {
      focusingPower += (i + 1) * boxes[i].getFocusingPower();
    }
    return focusingPower;
  }

  /**
   * Calculate a hash for a string, character by character.
   *
   * @param s The string to process
   * @return hash of the string
   */
  public static int calculateHash(String s) {
    int hash = 0;
    for (int i = 0; i < s.length(); ++i) {
      int asciiCode = s.charAt(i);
      hash = ((hash + asciiCode) * 17) % 256;
    }
    return hash;
  }
}
