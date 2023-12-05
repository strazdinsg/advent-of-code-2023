package tools;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles input-data files.
 */
public class InputFile {
  private boolean exists;
  private BufferedReader reader;
  private boolean endOfFileReached;

  final char[] charBuffer = new char[1];

  /**
   * Open an input file for reading.
   *
   * @param filename The name of the input file
   */
  public InputFile(String filename) {
    try {
      reader = new BufferedReader(new FileReader(filename));
      exists = true;
    } catch (FileNotFoundException e) {
      exists = false;
    }
  }

  /**
   * Returns the "existence of the file".
   *
   * @return true if the file exists, false if it either is not found or is not readable.
   */
  public boolean exists() {
    return exists;
  }

  /**
   * Read one line of text from the input file, try to interpret it as an integer.
   *
   * @return The number value; or empty value if the line was empty or end of file is reached
   */
  public IntegerOrEmpty readLineAsInteger() {
    String value = readLineAndDetectEnd();
    return value != null ? IntegerOrEmpty.fromString(value) : IntegerOrEmpty.empty;
  }

  /**
   * Read a line from the file, expect that there will be a prefix and the rest of the line
   * will contain a space-separated integers.
   *
   * @param expectedPrefix The expected prefix of the line
   * @return The list of integers on the line
   */
  public List<Long> readSpacedIntegerLine(String expectedPrefix) {
    String line = readLine();
    if (line == null) {
      throw new IllegalStateException("End of line reached");
    }
    if (expectedPrefix != null && !expectedPrefix.isEmpty()) {
      if (!line.startsWith(expectedPrefix)) {
        throw new IllegalStateException("Line does not start with the expected prefix: " + line);
      }
      line = line.substring(expectedPrefix.length());
    }
    line = line.trim();
    List<Long> numbers = new ArrayList<>();
    if (!line.isEmpty()) {
      String[] numberStrings = line.split(" ");
      for (String s : numberStrings) {
        numbers.add(Long.parseLong(s));
      }
    }
    return numbers;
  }

  /**
   * Read one line from the input file.
   *
   * @return The line as a string, null when end is reached.
   */
  public String readLine() {
    return readLineAndDetectEnd();
  }

  /**
   * Read one line of text from the file and detect whether end of file has been reached.
   *
   * @return The line or null if end is reached (or an error happens)
   */
  private String readLineAndDetectEnd() {
    String value = null;
    try {
      value = reader.readLine();
      if (value == null) {
        endOfFileReached = true;
      }
    } catch (IOException e) {
      endOfFileReached = true;
    }
    return value;
  }

  /**
   * Check whether end of file has been reached.
   *
   * @return True when end of file has been reached, false otherwise
   */
  public boolean isEndOfFile() {
    return endOfFileReached;
  }

  /**
   * Read all lines from the input file, store them in a StringGrid structure.
   *
   * @return The file content as a StringGrid.
   */
  public StringGrid readAllIntoGridBuffer() {
    StringGrid buffer = new StringGrid();
    while (!isEndOfFile()) {
      String line = readLineAndDetectEnd();
      if (line != null) {
        buffer.appendRow(line);
      }
    }
    return buffer;
  }

  /**
   * Read one line of input, expect it to be empty. If end of file is reached, do
   * nothing (no exception).
   *
   * @throws IllegalStateException If the line turns out not to be empty
   */
  public void skipEmptyLine() throws IllegalStateException {
    String emptyLine = readLine();
    if (emptyLine != null && !"".equals(emptyLine)) {
      throw new IllegalStateException("Expected empty line but got " + emptyLine);
    }
  }

  /**
   * Read lines from the input file until an empty line (or end of file) is reached.
   *
   * @return The lines as a list, not including the empty line
   */
  public List<String> readLinesUntilEmptyLine() {
    List<String> lines = new ArrayList<>();
    String line = readLine();
    while (!isEndOfFile() && line != null && !"".equals(line)) {
      lines.add(line);
      line = readLine();
    }
    return lines;
  }

  /**
   * Read one character from the file.
   *
   * @return The character or null when end of file is reached
   */
  public Character readOneChar() {
    Character result = null;
    try {
      int n = reader.read(charBuffer, 0, 1);
      if (n == 1) {
        result = charBuffer[0];
      }
    } catch (IOException e) {
      // Will return null
    }
    return result;
  }
}
