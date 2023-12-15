package problem.day15;


import static problem.day15.Operation.INSERT;
import static problem.day15.Operation.REMOVE;

import tools.InputFile;
import tools.Logger;

/**
 * Solution for the problem of Day 15
 * See description here: https://adventofcode.com/2023/day/15
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
    InputFile inputFile = new InputFile("problem15.input");
    if (!inputFile.exists()) {
      Logger.error("Input file not found");
      return;
    }

    String longLine = inputFile.readLine();
    String[] instructions = longLine.split(",");
    long sum = 0;
    FocalHashMap map = new FocalHashMap();
    for (String instructionString : instructions) {
      Instruction instruction = parseInstruction(instructionString);
      map.process(instruction);
      int hash = FocalHashMap.calculateHash(instructionString);
      Logger.info(instructionString + " = " + hash);
      sum += hash;
    }
    Logger.info("Hash sum: " + sum);
    Logger.info("Focusing power: " + map.getFocusingPower());
  }

  private Instruction parseInstruction(String instructionString) {
    Instruction instruction;
    int n = instructionString.length();
    if (instructionString.charAt(n - 1) == Instruction.REMOVE) {
      String label = instructionString.substring(0, n - 1);
      instruction = new Instruction(REMOVE, label, 0);
    } else {
      if (instructionString.charAt(n - 2) != Instruction.INSERT) {
        throw new IllegalArgumentException("Invalid instruction format: " + instructionString);
      }
      String label = instructionString.substring(0, n - 2);
      int focalLength = Integer.parseInt(instructionString.substring(n - 1));
      instruction = new Instruction(INSERT, label, focalLength);
    }
    return instruction;
  }

}


