package problem.day07;

import java.util.Arrays;
import java.util.List;
import tools.InputFile;
import tools.Logger;

/**
 * Solution for the problem of Day 07
 * See description here: https://adventofcode.com/2023/day/7
 */
public class Solver {
  private static final boolean USE_JOKERS = true;

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
    InputFile inputFile = new InputFile("problem07.input");
    if (!inputFile.exists()) {
      Logger.error("Input file not found");
      return;
    }

    List<String> lines = inputFile.readLinesUntilEmptyLine();
    int handCount = lines.size();
    Game[] games = new Game[handCount];
    for (int i = 0; i < handCount; ++i) {
      games[i] = parseGame(lines.get(i));
    }

    Arrays.sort(games);

    long totalWinnings = 0;
    for (int rank = 1; rank <= handCount; ++rank) {
      totalWinnings += games[rank - 1].getBid() * rank;
    }

    Logger.info("Total winnings: " + totalWinnings);
  }

  private Game parseGame(String line) {
    String[] parts = line.split(" ");
    if (parts.length != 2 || parts[0].length() != 5) {
      throw new IllegalArgumentException("Invalid line format: " + line);
    }

    String cards = parts[0];
    long bid = Long.parseLong(parts[1]);

    return new Game(cards, bid, USE_JOKERS);
  }
}


