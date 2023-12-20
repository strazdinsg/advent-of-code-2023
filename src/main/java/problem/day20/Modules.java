package problem.day20;

import static problem.day20.Signal.HIGH;
import static problem.day20.Signal.LOW;
import static problem.day20.Signal.NONE;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import tools.Logger;

/**
 * A machine of modules.
 */
public class Modules {
  public static final String BROADCASTER = "broadcaster";
  public static final char FLIP_FLOP = '%';
  public static final char CONJUNCTION = '&';
  private static final String OUTPUT = "output";
  private static final String BUTTON = "BUTTON";
  private static final String FINAL_MODULE_NAME = "rx";
  private final Map<String, RadioModule> modules = new HashMap<>();
  private final Queue<EmittedSignal> emittedSignals = new ArrayDeque<>();
  private boolean finalModuleActivated = false;

  private long lowSignalCount = 0;
  private long highSignalCount = 0;

  public void addModule(RadioModule radioModule) {
    modules.put(radioModule.getName(), radioModule);
  }

  /**
   * Simulate a button push with all the consequences.
   */
  public void pushButton() {
    sendLowSignalFromButtonToBroadcaster();

    while (!emittedSignals.isEmpty()) {
      EmittedSignal signal = emittedSignals.poll();
      RadioModule module = modules.get(signal.recipient());
      if (module == null) {
        if (signal.recipient().equals(FINAL_MODULE_NAME) && signal.signal() == LOW) {
          Logger.info("Final module activated!");
          finalModuleActivated = true;
        }
        continue;
      }

      module.receiveInput(signal.sender(), signal.signal());
      Signal output = module.process();
      // Logger.info(module.getName() + " - " + output + " -> " + module.getRecipientNameString());
      rememberSignalCount(output, module);
      if (output != NONE) {
        enqueueSignals(module, output);
      }
    }
  }

  private void sendLowSignalFromButtonToBroadcaster() {
    RadioModule broadcaster = modules.get(BROADCASTER);
    if (broadcaster == null) {
      throw new IllegalStateException("Broadcaster not found");
    }
    broadcaster.receiveInput(broadcaster.getName(), LOW);
    emittedSignals.clear();
    emittedSignals.add(new EmittedSignal(BUTTON, BROADCASTER, LOW));
    ++lowSignalCount;
  }

  private void enqueueSignals(RadioModule sender, Signal output) {
    for (String recipientName : sender.getRecipientNames()) {
      emittedSignals.add(new EmittedSignal(sender.getName(), recipientName, output));
    }
  }

  private void rememberSignalCount(Signal output, RadioModule module) {
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

  /**
   * Make sure all the inputs are correctly linked to each module.
   */
  public void registerSenders() {
    for (RadioModule module : modules.values()) {
      String senderName = module.getName();
      for (String recipientName : module.getRecipientNames()) {
        if (!recipientName.equals(OUTPUT)) {
          RadioModule recipient = modules.get(recipientName);
          if (recipient != null) {
            recipient.registerInputModule(senderName);
          } else {
            Logger.error("Invalid recipient module: " + recipientName);
          }
        }
      }
    }
  }

  public boolean isFinalModuleActivated() {
    return finalModuleActivated;
  }
}
