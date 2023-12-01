package tools;

/**
 * Writes log messages to the default system output (console).
 */
public class Logger {
  /**
   * Print an information message to the system output (console).
   *
   * @param message The message to print.
   */
  public static void info(String message) {
    // Sorry, this still IS the easiest method to log to the console, fine for these problems ;)
    System.out.println(message);
  }

  /**
   * Print an error message to the system output (console).
   *
   * @param message The message to print.
   */
  public static void error(String message) {
    System.err.println(message);
  }

  // Not supposed to create an instance of this!
  private Logger() {
  }
}
