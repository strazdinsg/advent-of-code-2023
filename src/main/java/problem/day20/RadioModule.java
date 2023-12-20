package problem.day20;

public abstract class RadioModule {
  private final String name;
  protected final String[] recipientNames;

  public RadioModule(String name, String[] recipientNames) {
    this.name = name;
    this.recipientNames = recipientNames;
  }

  /**
   * Register that this module has a specific other module as an input.
   *
   * @param moduleName The input module name
   */
  public void registerInputModule(String moduleName) {
  }

  public abstract void receiveInput(String sender, Signal input);

  public String getName() {
    return name;
  }

  /**
   * Process the received input signals.
   *
   * @return The emitted signal, if any
   */
  public abstract Signal process();

  public String[] getRecipientNames() {
    return recipientNames;
  }

  public long getOutputCount() {
    return recipientNames.length;
  }

  public String getRecipientNameString() {
    return String.join(", ", recipientNames);
  }
}
