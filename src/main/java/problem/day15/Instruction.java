package problem.day15;

/**
 * A single instruction for updating the focal hash map.
 *
 * @param operation   The operation to perform
 * @param label       Label of the instruction
 * @param focalLength Focal length of the instruction, use zero if not applicable.
 */
public record Instruction(Operation operation, String label, int focalLength) {
  public static final char REMOVE = '-';
  public static final char INSERT = '=';

  public boolean isInsert() {
    return operation == Operation.INSERT;
  }
}
