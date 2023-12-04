package problem.day04;

import tools.InputFile;
import tools.Logger;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Solution for the problem of Day 04
 * See description here: https://adventofcode.com/2023/day/4
 */
public class Solver {
  int numberStartPosition;
  int numberSeparatorPosition;

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
    InputFile inputFile = new InputFile("problem04.input");
    if (!inputFile.exists()) {
      Logger.error("Input file not found");
      return;
    }

    List<String> lines = inputFile.readLinesUntilEmptyLine();
    initializeSeparators(lines.get(0));

    long points = 0;
    for (String line : lines) {
      points += calculatePoints(line);
    }
    Logger.info("Total points: " + points);
  }

  private void initializeSeparators(String line) {
    numberStartPosition = line.indexOf(": ") + 2;
    if (numberStartPosition < 8) {
      throw new IllegalStateException("Wrong card format: " + line);
    }
    numberSeparatorPosition = line.indexOf("| ");
    if (numberSeparatorPosition < 10) {
      throw new IllegalStateException("Wrong card format, no separator: " + line);
    }
  }

  private long calculatePoints(String line) {
    long points = 0;
    Set<Integer> winning = getWinningNumbers(line);
    Set<Integer> numbersGot = getNumbersGot(line);

    for (Integer n : numbersGot) {
      if (winning.contains(n)) {
        if (points == 0) {
          points = 1;
        } else {
          points *= 2;
        }
      }
    }

    return points;
  }

  private Set<Integer> getWinningNumbers(String line) {
    return parseNumbers(line.substring(numberStartPosition, numberSeparatorPosition - 1));
  }

  private Set<Integer> getNumbersGot(String line) {
    return parseNumbers(line.substring(numberSeparatorPosition + 2));
  }

  private Set<Integer> parseNumbers(String s) {
    Set<Integer> numbers = new HashSet<>();
    Logger.info("Parsing " + s);
    while (!s.isEmpty()) {
      s = pickNumberFrom(s, numbers);
    }

    return numbers;
  }

  private String pickNumberFrom(String s, Set<Integer> numbers) {
    if (s.length() < 2) {
      throw new IllegalStateException("Invalid number string: " + s);
    }
    String numberString = s.substring(0, 2);
    if (numberString.substring(0, 1).equals(" ")) {
      numberString = numberString.substring(1);
    }
    numbers.add(Integer.parseInt(numberString));
    return s.length() >= 3 ? s.substring(3) : "";
  }
}
