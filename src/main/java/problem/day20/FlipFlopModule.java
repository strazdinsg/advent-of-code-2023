package problem.day20;

import static problem.day20.EmittedSignal.HIGH;
import static problem.day20.EmittedSignal.LOW;
import static problem.day20.EmittedSignal.NONE;

public class FlipFlopModule extends RadioModule {
  private boolean on = false;
  private boolean flipped = false;

  public FlipFlopModule(String name, String[] receivers) {
    super(name, receivers);
  }

  @Override
  public void receiveInput(String sender, EmittedSignal input) {
    flipped = input == LOW;
    if (flipped) {
      on = !on;
    }
  }

  @Override
  public EmittedSignal process() {
    EmittedSignal output = flipped ? (on ? HIGH : LOW) : NONE;
    flipped = false;
    return output;
  }
}
