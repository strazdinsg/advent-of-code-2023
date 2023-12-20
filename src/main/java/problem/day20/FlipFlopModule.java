package problem.day20;

import static problem.day20.Signal.HIGH;
import static problem.day20.Signal.LOW;
import static problem.day20.Signal.NONE;

public class FlipFlopModule extends RadioModule {
  private boolean on = false;
  private boolean flipped = false;

  public FlipFlopModule(String name, String[] receivers) {
    super(name, receivers);
  }

  @Override
  public void receiveInput(String sender, Signal input) {
    flipped = input == LOW;
    if (flipped) {
      on = !on;
    }
  }

  @Override
  public Signal process() {
    Signal output = flipped ? (on ? HIGH : LOW) : NONE;
    flipped = false;
    return output;
  }
}
