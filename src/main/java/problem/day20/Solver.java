package problem.day20;

import static problem.day20.ModuleMachine.BROADCASTER;
import static problem.day20.ModuleMachine.CONJUNCTION;
import static problem.day20.ModuleMachine.FLIP_FLOP;

import java.util.List;
import tools.InputFile;
import tools.Logger;

/**
 * Solution for the problem of Day 20
 * See description here: https://adventofcode.com/2023/day/2
 */
public class Solver {
  private final ModuleMachine moduleMachine = new ModuleMachine();

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
    InputFile inputFile = new InputFile("problem20.input");
    if (!inputFile.exists()) {
      Logger.error("Input file not found");
      return;
    }

    List<String> lines = inputFile.readLinesUntilEmptyLine();
    for (String line : lines) {
      moduleMachine.addModule(parseModule(line));
    }
    moduleMachine.registerSenders();

    part1(moduleMachine);
    // part2();
  }

  private void part2() {
    long buttonPresses = 0;
    while (!moduleMachine.isFinalModuleActivated()) {
      moduleMachine.pushButton();
      buttonPresses++;
      if (buttonPresses % 1000000 == 0) {
        Logger.info(buttonPresses + " button presses, still working...");
      }
    }
    Logger.info("Final module activated after " + buttonPresses + " button presses");
  }

  private static void part1(ModuleMachine moduleMachine) {
    for (int i = 0; i < 1000; ++i) {
      moduleMachine.pushButton();
    }
    long lowSignals = moduleMachine.getLowSignalCount();
    long highSignals = moduleMachine.getHighSignalCount();
    Logger.info("Signal multiplication: " + lowSignals * highSignals);
  }

  private RadioModule parseModule(String line) {
    String[] parts = line.split(" -> ");
    if (parts.length != 2 || parts[0].length() < 2 || parts[1].isEmpty()) {
      throw new IllegalArgumentException("Invalid line format: " + line);
    }

    String typeAndName = parts[0];
    String[] receivers = parts[1].split(", ");
    RadioModule module;
    if (typeAndName.equals(BROADCASTER)) {
      module = new BroadcasterModule(typeAndName, receivers);
    } else {
      char type = typeAndName.charAt(0);
      String name = typeAndName.substring(1);
      switch (type) {
        case FLIP_FLOP -> module = new FlipFlopModule(name, receivers);
        case CONJUNCTION -> module = new ConjunctionModule(name, receivers);
        default -> throw new IllegalArgumentException("Invalid module type: " + type);
      }
    }

    return module;
  }

}


