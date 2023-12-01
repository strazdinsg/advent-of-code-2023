package problem.day01;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import tools.InputFile;
import tools.Logger;

/**
 * Solution for the problem of Day 01
 * See description here: https://adventofcode.com/2023/day/1
 * The main idea: find the first and last digit on every line, form a two-digit number.
 * Then sum all the two-digit numbers.
 */
public class Solver {
  Map<String, Integer> digits = new HashMap<>();

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
    InputFile inputFile = new InputFile("problem01.input");
    if (!inputFile.exists()) {
      Logger.error("Input file not found");
      return;
    }

    initializeDigits();
    long sum = 0;

    List<String> lines = inputFile.readLinesUntilEmptyLine();
    for (String line : lines) {
      int value = findCalibrationValue(line);
      Logger.info(line + " -> " + value);
      sum += value;
    }

    Logger.info("Sum of calibration values: " + sum);
  }

  private void initializeDigits() {
    digits.put("1", 1);
    digits.put("2", 2);
    digits.put("3", 3);
    digits.put("4", 4);
    digits.put("5", 5);
    digits.put("6", 6);
    digits.put("7", 7);
    digits.put("8", 8);
    digits.put("9", 9);
    digits.put("one", 1);
    digits.put("two", 2);
    digits.put("three", 3);
    digits.put("four", 4);
    digits.put("five", 5);
    digits.put("six", 6);
    digits.put("seven", 7);
    digits.put("eight", 8);
    digits.put("nine", 9);
  }

  private int findCalibrationValue(String line) {
    Integer firstDigit = null;
    Integer lastDigit = null;
    int firstDigitPosition = Integer.MAX_VALUE;
    int lastDigitPosition = Integer.MIN_VALUE;

    // Find occurrence of each digit or word representing a digit. If it is more to the left than
    // the current "most left" digit, remember it as the first digit.
    // Do the same thing for the last digit.
    // Hint: we do this weird search (instead of replacing `eight` with `8`, because otherwise
    // this would not work correctly: 1eightwo
    for (Map.Entry<String, Integer> entry : digits.entrySet()) {
      String digit = entry.getKey();
      Integer value = entry.getValue();
      int firstPos = line.indexOf(digit);
      int lastPos = line.lastIndexOf(digit);
      if (firstPos >= 0 && firstPos < firstDigitPosition) {
        firstDigit = value;
        firstDigitPosition = firstPos;
      }
      if (lastPos >= 0 && lastPos > lastDigitPosition) {
        lastDigit = value;
        lastDigitPosition = lastPos;
      }
    }

    if (firstDigit == null || lastDigit == null) {
      throw new IllegalArgumentException("Line does not contain two digits: " + line);
    }

    return firstDigit * 10 + lastDigit;
  }
}
