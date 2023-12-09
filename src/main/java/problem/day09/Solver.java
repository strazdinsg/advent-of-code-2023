package problem.day09;

import tools.InputFile;
import tools.Logger;
import java.util.List;

/**
 * Solution for the problem of Day 09
 * See description here: https://adventofcode.com/2023/day/9
 * Idea: we find the next row in the predictions. The result (of part 1) is the sum of last
 * elements of all the prediction rows.
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
  }

  private long predictNextValue(List<Long> valueList) {
    long[] values = listToArray(valueList);
    int valueCount = values.length;
    long sumOfLastValues = values[valueCount - 1];

    boolean allZeroes = false;
    while (!allZeroes) {
      allZeroes = replaceValuesWithDifferences(values, valueCount);
      valueCount--;
      sumOfLastValues += values[valueCount - 1];
    }

    return sumOfLastValues;
  }

  private boolean replaceValuesWithDifferences(long[] values, int valueCount) {
    boolean onlyZeroes = true;
    for (int i = 1; i < valueCount; ++i) {
      values[i - 1] = values[i] - values[i - 1];
      onlyZeroes = onlyZeroes && (values[i - 1] == 0);
    }
    return onlyZeroes;
  }

  private long[] listToArray(List<Long> values) {
    int n = values.size();
    long[] v = new long[n];
    for (int i = 0; i < n; ++i) {
      v[i] = values.get(i);
    }
    return v;
  }
}


