package problem.day20;

import static problem.day20.EmittedSignal.LOW;

public class BroadcasterModule extends RadioModule {
  public BroadcasterModule(String name, String[] receivers) {
    super(name, receivers);
  }

  @Override
  public void receiveInput(String sender, EmittedSignal input) {
    if (input != LOW) {
      throw new IllegalArgumentException("Only low pulse is allowed as broadcaster input");
    }
  }

  @Override
  public EmittedSignal process() {
    return LOW;
  }
}
