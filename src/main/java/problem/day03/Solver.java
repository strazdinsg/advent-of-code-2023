package problem.day03;

import static java.lang.Character.isDigit;

import java.util.LinkedList;
import java.util.List;
import tools.InputFile;
import tools.IntegerOrEmpty;
import tools.Logger;
import tools.Rectangle;
import tools.StringGrid;

/**
 * Solution for the problem of Day 03
 * See description here: https://adventofcode.com/2023/day/3
 */
public class Solver {
  private static final char GEAR_SYMBOL = '*';
  private final List<PartNumber> partNumbers = new LinkedList<>();
  private StringGrid grid;

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
    InputFile inputFile = new InputFile("problem03.input");
    if (!inputFile.exists()) {
      Logger.error("Input file not found");
      return;
    }

    grid = inputFile.readAllIntoGridBuffer();
    findPartNumbers();
    long partNumberSum = 0;
    for (PartNumber partNumber : partNumbers) {
      partNumberSum += partNumber.number();
    }
    long gearRatioSum = findGearRatioSum();

    Logger.info("The sum of part numbers is " + partNumberSum);
    Logger.info("The sum of gear ratios is " + gearRatioSum);
  }

  private void findPartNumbers() {
    for (int rowIndex = 0; rowIndex < grid.getRowCount(); ++rowIndex) {
      findPartNumbersInRow(rowIndex);
    }
  }

  private void findPartNumbersInRow(int rowIndex) {
    int columnIndex = 0;
    while (columnIndex < grid.getColumnCount()) {
      PartNumber potentialNumber = readNumberAt(rowIndex, columnIndex);
      if (potentialNumber != null) {
        if (foundCharacterAround(potentialNumber)) {
          partNumbers.add(potentialNumber);
        }
        columnIndex = potentialNumber.position().getMaxX();
      }
      columnIndex++;
    }
  }

  private IntegerOrEmpty getDigitAt(int rowIndex, int columnIndex) {
    char c = grid.getCharacter(rowIndex, columnIndex);
    return IntegerOrEmpty.fromString("" + c);
  }

  private PartNumber readNumberAt(int rowIndex, int columnIndex) {
    PartNumber partNumber = null;

    int leftPosition = columnIndex;
    long number = 0;
    IntegerOrEmpty digit = getDigitAt(rowIndex, columnIndex);

    while (digit.isNumber()) {
      number = number * 10 + digit.getValue();
      columnIndex++;
      if (columnIndex < grid.getColumnCount()) {
        digit = getDigitAt(rowIndex, columnIndex);
      } else {
        digit = IntegerOrEmpty.empty;
      }
    }

    if (number > 0) {
      Rectangle boundingBox = new Rectangle(leftPosition, rowIndex, columnIndex - 1, rowIndex);
      partNumber = new PartNumber(number, boundingBox);
    }

    return partNumber;
  }

  private boolean foundCharacterAround(PartNumber number) {
    return foundCharacterOnSides(number) || foundCharacterOnTopOrBottom(number);
  }

  private boolean foundCharacterOnSides(PartNumber number) {
    return isCharacterAt(number.getRowIndex(), number.getStartColumn() - 1)
        || isCharacterAt(number.getRowIndex(), number.getEndColumn() + 1);
  }

  private boolean foundCharacterOnTopOrBottom(PartNumber number) {
    boolean found = false;
    int rowIndex = number.getRowIndex();
    int columnIndex = number.getStartColumn() - 1;
    while (!found && columnIndex <= number.getEndColumn() + 1) {
      found = isCharacterAt(rowIndex - 1, columnIndex) || isCharacterAt(rowIndex + 1, columnIndex);
      columnIndex++;
    }
    return found;
  }

  private boolean isCharacterAt(int rowIndex, int columnIndex) {
    boolean isCharacter = false;
    if (rowIndex >= 0 && rowIndex < grid.getRowCount()
        && columnIndex >= 0 && columnIndex < grid.getColumnCount()) {
      isCharacter = isSchematicCharacter(grid.getCharacter(rowIndex, columnIndex));
    }
    return isCharacter;
  }

  private boolean isSchematicCharacter(char c) {
    return !isDigit(c) && c != '.';
  }

  private long findGearRatioSum() {
    long sum = 0;
    for (int rowIndex = 0; rowIndex < grid.getRowCount(); ++rowIndex) {
      for (int columnIndex = 0; columnIndex < grid.getColumnCount(); ++columnIndex) {
        if (grid.getCharacter(rowIndex, columnIndex) == GEAR_SYMBOL) {
          sum += getGearRatio(rowIndex, columnIndex);
        }
      }
    }
    return sum;
  }

  private long getGearRatio(int rowIndex, int columnIndex) {
    long gearRatio = 0;
    List<PartNumber> numbers = getNumbersTouching(rowIndex, columnIndex);
    if (numbers.size() == 2) {
      gearRatio = numbers.get(0).number() * numbers.get(1).number();
    }

    return gearRatio;
  }

  private List<PartNumber> getNumbersTouching(int rowIndex, int columnIndex) {
    List<PartNumber> numbers = new LinkedList<>();
    for (PartNumber partNumber : partNumbers) {
      if (isTouching(partNumber, rowIndex, columnIndex)) {
        numbers.add(partNumber);
      }
    }
    return numbers;
  }

  private boolean isTouching(PartNumber partNumber, int rowIndex, int columnIndex) {
    Rectangle around = new Rectangle(partNumber.getStartColumn() - 1, partNumber.getRowIndex() - 1,
        partNumber.getEndColumn() + 1, partNumber.getRowIndex() + 1);
    return around.isWithin(columnIndex, rowIndex);
  }

}
