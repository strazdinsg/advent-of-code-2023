package problem.day20;

/**
 * An emitted signal.
 *
 * @param sender    The sender of the signal
 * @param recipient The receiver of the signal
 * @param signal    The signal that was sent
 */
public record EmittedSignal(String sender, String recipient, Signal signal) {
  @Override
  public String toString() {
    return sender + " -" + signal + "-> " + recipient;
  }
}
