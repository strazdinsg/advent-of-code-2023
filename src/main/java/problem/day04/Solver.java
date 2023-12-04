package problem.day04;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import tools.InputFile;
import tools.Logger;

/**
 * Solution for the problem of Day 04
 * See description here: https://adventofcode.com/2023/day/4
 */
public class Solver {
  int numberStartPosition;
  int numberSeparatorPosition;
  List<Integer> winningNumberCounts = new ArrayList<>();
  List<Long> points = new ArrayList<>();

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
    calculatePoints(lines);
    calculateFinalCardCount(lines);
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

  private void calculatePoints(List<String> lines) {
    long totalPoints = 0;
    for (String line : lines) {
      totalPoints += calculatePointsForCard(line);
    }
    Logger.info("Total points: " + totalPoints);
  }

  private void calculateFinalCardCount(List<String> lines) {
    long[] finalCardCounts = new long[lines.size()];
    long cardCount = 0;

    // Look at the cards backwards, because card n is dependent on cards n+1, n+2, ..., n+w,
    // where w is the number of winning numbers in card n
    for (int i = finalCardCounts.length - 1; i >= 0; --i) {
      finalCardCounts[i] = 1;
      int w = winningNumberCounts.get(i);
      for (int j = i + 1; j <= i + w && j < finalCardCounts.length; ++j) {
        finalCardCounts[i] += finalCardCounts[j];
      }
      cardCount += finalCardCounts[i];
    }

    Logger.info("Total number of cards in the end: " + cardCount);
  }

  private long calculatePointsForCard(String line) {
    long pointsForThisCard = 0;
    int winningNumberCount = 0;
    Set<Integer> winning = getWinningNumbers(line);
    Set<Integer> numbersGot = getNumbersGot(line);

    for (Integer n : numbersGot) {
      if (winning.contains(n)) {
        winningNumberCount++;
        if (pointsForThisCard == 0) {
          pointsForThisCard = 1;
        } else {
          pointsForThisCard <<= 1;
        }
      }
    }

    if (winningNumberCount == 0) {
      pointsForThisCard = 0;
    }

    winningNumberCounts.add(winningNumberCount);
    points.add(pointsForThisCard);

    return pointsForThisCard;
  }

  private Set<Integer> getWinningNumbers(String line) {
    return parseNumbers(line.substring(numberStartPosition, numberSeparatorPosition - 1));
  }

  private Set<Integer> getNumbersGot(String line) {
    return parseNumbers(line.substring(numberSeparatorPosition + 2));
  }

  private Set<Integer> parseNumbers(String s) {
    Set<Integer> numbers = new HashSet<>();
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
    if (numberString.charAt(0) == ' ') {
      numberString = numberString.substring(1);
    }
    numbers.add(Integer.parseInt(numberString));
    return s.length() >= 3 ? s.substring(3) : "";
  }
}
