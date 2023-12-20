package problem.day20;

public record EmittedSignal(String sender, String recipient, Signal signal) {
  @Override
  public String toString() {
    return sender + " -" + signal + "-> " + recipient;
  }
}
