package problem.day19;

import java.util.Iterator;
import java.util.List;

/**
 * One workflow of part processing.
 *
 * @param name       The name of the workflow
 * @param operations The operations to perform, with conditions
 */
public record Workflow(String name, List<Operation> operations) implements Iterable<Operation> {
  @Override
  public String toString() {
    return name + ": " + operations;
  }

  /**
   * Process a part.
   *
   * @param part The part to process
   * @return The name of the next workflow where the part must be sent
   */
  public String process(Part part) {
    String destination = null;
    Iterator<Operation> it = operations().iterator();
    while (destination == null && it.hasNext()) {
      Operation op = it.next();
      destination = op.process(part);
    }
    return destination;
  }

  @Override
  public Iterator<Operation> iterator() {
    return operations().iterator();
  }
}
