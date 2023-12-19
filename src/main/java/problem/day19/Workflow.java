package problem.day19;

import java.util.Iterator;
import java.util.List;

public record Workflow(String name, List<Operation> operations) implements Iterable<Operation> {
  @Override
  public String toString() {
    return name + ": " + operations;
  }

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
