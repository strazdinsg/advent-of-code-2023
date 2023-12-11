package tools;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * A utility for writing to a file.
 */
public class OutputFile {
  PrintWriter writer;

  /**
   * Create an output file.
   *
   * @param filename The name of the file.
   * @throws IllegalArgumentException When not possible to open the file for writing
   */
  public OutputFile(String filename) throws IllegalArgumentException {
    try {
      writer = new PrintWriter(new FileWriter(filename));
    } catch (IOException e) {
      throw new IllegalArgumentException("Can't open the file for writing: " + filename);
    }
  }

  /**
   * Write a string grid to the file.
   *
   * @param grid A string grid to write
   */
  public void writeGrid(StringGrid grid) {
    for (int row = 0; row < grid.getRowCount(); ++row) {
      writer.println(grid.getRow(row));
    }
  }

  public void close() {
    writer.close();
  }
}
