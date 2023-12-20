package problem.day20;

import static problem.day20.EmittedSignal.*;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ConjunctionModule extends RadioModule {
  private final Map<String, Boolean> inputs = new HashMap<>();
  public ConjunctionModule(String name, String[] receivers) {
    super(name, receivers);
  }

  @Override
  public void registerInputModule(String moduleName) {
    inputs.put(moduleName, false);
  }

  @Override
  public void receiveInput(String sender, EmittedSignal input) {
    if (input != NONE) {
      inputs.put(sender, input == HIGH);
    }
  }

  @Override
  public EmittedSignal process() {
    return areAllInputsHigh() ? LOW : HIGH;
  }

  private boolean areAllInputsHigh() {
    boolean high = true;
    Iterator<Boolean> it = inputs.values().iterator();
    while (high && it.hasNext()) {
      high = it.next();
    }
    return high;
  }
}
