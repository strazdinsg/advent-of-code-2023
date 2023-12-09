package problem.day09;

import static tools.Algos.listToArray;

import java.util.List;
import java.util.Stack;
import tools.InputFile;
import tools.Logger;

/**
 * Solution for the problem of Day 09
 * See description here: https://adventofcode.com/2023/day/9
 * Idea: we find the next row in the predictions. The result (of part 1) is the sum of last
 * elements of all the prediction rows.
 */
public class Solver {
  Stack<Long> firstNumbers = new Stack<>(); // History of first numbers
  long firstNumberSum = 0;

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
    InputFile inputFile = new InputFile("problem09.input");
    if (!inputFile.exists()) {
      Logger.error("Input file not found");
      return;
    }

    long sum = 0;
    List<Long> values = inputFile.readSpacedIntegerLine("");
    while (!values.isEmpty()) {
      sum += predictNextValue(values);
      values = inputFile.readSpacedIntegerLine("");
    }

    Logger.info("Sum of predictions: " + sum);
    Logger.info("Sum of first number predictions: " + firstNumberSum);
  }

  private long predictNextValue(List<Long> valueList) {
    long[] values = listToArray(valueList);
    int valueCount = values.length;
    long sumOfLastValues = values[valueCount - 1];
    firstNumbers.clear();

    boolean allZeroes = false;
    while (!allZeroes) {
      allZeroes = replaceValuesWithDifferences(values, valueCount);
      valueCount--;
      sumOfLastValues += values[valueCount - 1];
    }

    calculateFirstNumberPrediction();

    return sumOfLastValues;
  }

  private boolean replaceValuesWithDifferences(long[] values, int valueCount) {
    rememberFirstNumber(values[0]);
    boolean onlyZeroes = true;
    for (int i = 1; i < valueCount; ++i) {
      values[i - 1] = values[i] - values[i - 1];
      onlyZeroes = onlyZeroes && (values[i - 1] == 0);
    }
    return onlyZeroes;
  }

  private void rememberFirstNumber(long firstNumber) {
    firstNumbers.add(firstNumber);
  }

  private void calculateFirstNumberPrediction() {
    long firstPrediction = 0;
    while (!firstNumbers.isEmpty()) {
      firstPrediction = firstNumbers.pop() - firstPrediction;
    }
    firstNumberSum += firstPrediction;
  }

}


