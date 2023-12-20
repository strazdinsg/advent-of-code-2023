package problem.day20;


import static problem.day20.Signal.LOW;

/**
 * A module which broadcasts a low signal to all it's inputs.
 */
public class BroadcasterModule extends RadioModule {
  public BroadcasterModule(String name, String[] receivers) {
    super(name, receivers);
  }

  @Override
  public void receiveInput(String sender, Signal input) {
    if (input != LOW) {
      throw new IllegalArgumentException("Only low pulse is allowed as broadcaster input");
    }
  }

  @Override
  public Signal process() {
    return LOW;
  }
}
