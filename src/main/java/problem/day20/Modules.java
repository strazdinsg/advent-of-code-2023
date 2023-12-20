package problem.day20;

import static problem.day20.EmittedSignal.*;

import tools.Logger;
import java.util.*;

public class Modules {
  public static final String BROADCASTER_NAME = "broadcaster";
  public static final char FLIP_FLOP = '%';
  public static final char CONJUNCTION = '&';
  private static final String OUTPUT = "output";
  private final Map<String, RadioModule> modules = new HashMap<>();
  private final Queue<RadioModule> toProcess = new ArrayDeque<>();

  private long lowSignalCount = 0;
  private long highSignalCount = 0;

  public void addModule(RadioModule radioModule) {
    modules.put(radioModule.getName(), radioModule);
  }

  public void pushButton() {
    RadioModule broadcaster = modules.get(BROADCASTER_NAME);
    if (broadcaster == null) {
      throw new IllegalStateException("Broadcaster not found");
    }

    broadcaster.receiveInput(broadcaster.getName(), LOW);
    toProcess.clear();
    toProcess.add(broadcaster);

    while (!toProcess.isEmpty()) {
      RadioModule module = toProcess.poll();
      EmittedSignal output = module.process();
      Logger.info(module.getName() + ": " + output);
      rememberSignalCount(output, module);
      if (output != NONE) {
        // TODO - The signals must be queued!
        propagateSignals(module, output);
      }
    }
  }

  private void propagateSignals(RadioModule module, EmittedSignal output) {
    for (String recipientName : module.getRecipientNames()) {
      RadioModule recipient = modules.get(recipientName);
      recipient.receiveInput(module.getName(), output);
      toProcess.add(recipient);
    }
  }

  private void rememberSignalCount(EmittedSignal output, RadioModule module) {
    if (output == HIGH) {
      highSignalCount += module.getOutputCount();
    } else if (output == LOW) {
      lowSignalCount += module.getOutputCount();
    }
  }

  public long getLowSignalCount() {
    return lowSignalCount;
  }

  public long getHighSignalCount() {
    return highSignalCount;
  }

  public void registerSenders() {
    for (RadioModule module : modules.values()) {
      String senderName = module.getName();
      for (String recipientName : module.getRecipientNames()) {
        if (!recipientName.equals(OUTPUT)) {
          RadioModule recipient = modules.get(recipientName);
          if (recipient == null) {
            throw new IllegalStateException("Invalid recipient module: " + recipientName);
          }
          recipient.registerInputModule(senderName);
        }
      }
    }
  }
}
