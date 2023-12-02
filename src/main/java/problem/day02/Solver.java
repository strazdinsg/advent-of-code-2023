package problem.day02;

import java.util.List;
import tools.InputFile;
import tools.IntegerOrEmpty;
import tools.Logger;

/**
 * Solution for the problem of Day 02
 * See description here: https://adventofcode.com/2023/day/2
 * The main ideas: interpret the strings as games. Part 1: check which ones have higher
 * number of balls than available in the spec.
 */
public class Solver {
  private static final int RED_BALL_COUNT = 12;
  private static final int GREEN_BALL_COUNT = 13;
  private static final int BLUE_BALL_COUNT = 14;

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
    InputFile inputFile = new InputFile("problem02.input");
    if (!inputFile.exists()) {
      Logger.error("Input file not found");
      return;
    }

    long sum = 0;
    List<String> lines = inputFile.readLinesUntilEmptyLine();
    for (String line : lines) {
      Game game = parseGame(line);
      if (game.isPossibleWith(RED_BALL_COUNT, GREEN_BALL_COUNT, BLUE_BALL_COUNT)) {
        sum += game.getId();
      }
    }

    Logger.info("The sum of possible games IDs " + sum);
  }

  private Game parseGame(String line) {
    Game game = new Game(extractGameId(line));
    String tryData = extractTryData(line);
    String[] tries = tryData.split("; ");
    for (String oneTry : tries) {
      BallCounts ballCounts = parseBallCounts(oneTry);
      game.addTry(ballCounts);
    }
    return game;
  }

  private Long extractGameId(String line) {
    int colonPosition = line.indexOf(":");
    if (colonPosition < 0) {
      throw new IllegalArgumentException("Line does not contain game ID: " + line);
    }
    String gameIdString = line.substring(5, colonPosition);

    return IntegerOrEmpty.fromString(gameIdString).getValue();
  }

  private String extractTryData(String line) {
    int headerSeparatorPosition = line.indexOf(": ");

    if (headerSeparatorPosition < 0 || line.length() < headerSeparatorPosition + 7) {
      throw new IllegalArgumentException("Line does not contain game tries: " + line);
    }

    return line.substring(headerSeparatorPosition + 2);
  }

  private BallCounts parseBallCounts(String oneTry) {
    String[] parts = oneTry.split(", ");

    Long redCount = 0L;
    Long greenCount = 0L;
    Long blueCount = 0L;
    for (String part : parts) {
      String[] countAndColor = part.split(" ");
      if (countAndColor.length != 2) {
        throw new IllegalArgumentException("Invalid format for count-and-color part: " + part);
      }
      String countString = countAndColor[0];
      IntegerOrEmpty count = IntegerOrEmpty.fromString(countString);
      if (!count.isNumber()) {
        throw new IllegalArgumentException("Invalid ball count: " + countString);
      }
      String color = countAndColor[1];
      switch (color) {
        case "red":
          redCount = count.getValue();
          break;
        case "green":
          greenCount = count.getValue();
          break;
        case "blue":
          blueCount = count.getValue();
          break;
        default:
          throw new IllegalArgumentException("Invalid color" + color);
      }
    }

    if (redCount == null || greenCount == null || blueCount == null) {
      throw new IllegalArgumentException("Some color(s) are missing: " + oneTry);
    }

    return new BallCounts(redCount, greenCount, blueCount);
  }

}
