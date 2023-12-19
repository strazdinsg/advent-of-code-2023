package problem.day19;

import java.util.LinkedList;
import java.util.List;
import tools.InputFile;
import tools.Logger;

/**
 * Solution for the problem of Day 19
 * See description here: https://adventofcode.com/2023/day/19
 */
public class Solver {

  /**
   * Run the solver - solve the puzzle.
   *
   * @param args Command line arguments, not used (enforced by Java).
   */
  public static void main(String[] args) {
    Logger.info("Starting...");
    Solver solver = new Solver();
    solver.solve();
  }

  private void solve() {
    InputFile inputFile = new InputFile("problem19.input");
    if (!inputFile.exists()) {
      Logger.error("Input file not found");
      return;
    }

    WorkflowSystem workflows = createWorkflows(inputFile.readLinesUntilEmptyLine());
    List<Part> parts = createParts(inputFile.readLinesUntilEmptyLine());

    long propertySum = 0;
    for (Part part : parts) {
      if (workflows.process(part)) {
        propertySum += part.getPropertySum();
      }
    }
    Logger.info("Property sum (Part 1): " + propertySum);

    Logger.info("Accepted combinations: " + workflows.calculateAcceptedCombinationCount());
  }


  private WorkflowSystem createWorkflows(List<String> lines) {
    WorkflowSystem workflows = new WorkflowSystem();
    for (String line : lines) {
      workflows.add(parseWorkflow(line));
    }
    return workflows;
  }

  private Workflow parseWorkflow(String line) {
    int openBracePos = line.indexOf('{');
    if (openBracePos < 2 || !line.endsWith("}")) {
      throw new IllegalArgumentException("Invalid line format: " + line);
    }

    String name = line.substring(0, openBracePos);
    String opString = line.substring(openBracePos + 1, line.length() - 1);
    List<Operation> operations = parseOperations(opString);

    return new Workflow(name, operations);
  }

  private List<Operation> parseOperations(String opString) {
    List<Operation> operations = new LinkedList<>();
    String[] ops = opString.split(",");
    for (String op : ops) {
      operations.add(parseOperation(op));
    }
    return operations;
  }

  private Operation parseOperation(String opString) {
    String[] parts = opString.split(":");
    String destination;
    Condition condition;
    if (parts.length == 2) {
      destination = parts[1];
      condition = parseCondition(parts[0]);
    } else if (parts.length == 1) {
      condition = null;
      destination = parts[0];
    } else {
      throw new IllegalArgumentException("Invalid op string: " + opString);
    }
    return new Operation(condition, destination);
  }

  private Condition parseCondition(String conditionString) {
    char property = conditionString.charAt(0);
    Comparison comparison = switch (conditionString.charAt(1)) {
      case '>' -> Comparison.GREATER_THAN;
      case '<' -> Comparison.LESS_THAN;
      default -> throw new IllegalArgumentException("Invalid condition string: " + conditionString);
    };
    int threshold = Integer.parseInt(conditionString.substring(2));

    return new Condition(property, comparison, threshold);
  }

  private List<Part> createParts(List<String> lines) {
    List<Part> parts = new LinkedList<>();
    for (String line : lines) {
      parts.add(parsePart(line));
    }
    return parts;
  }

  private Part parsePart(String line) {
    if (line.charAt(0) != '{' || line.charAt(line.length() - 1) != '}') {
      throw new IllegalArgumentException("Invalid part line format: " + line);
    }

    line = line.substring(1, line.length() - 1);
    String[] propStrings = line.split(",");
    if (propStrings.length != 4) {
      throw new IllegalArgumentException("Invalid part line format: " + line);
    }
    int x = Integer.parseInt(propStrings[0].substring(2));
    int m = Integer.parseInt(propStrings[1].substring(2));
    int a = Integer.parseInt(propStrings[2].substring(2));
    int s = Integer.parseInt(propStrings[3].substring(2));

    return new Part(x, m, a, s);
  }
}


