package problem.day19;

import java.util.HashMap;
import java.util.Map;

/**
 * A system of part processing workflows.
 */
public class WorkflowSystem {
  private static final String START_WORKFLOW_NAME = "in";
  private static final String REJECT_WORKFLOW_NAME = "R";
  private static final String ACCEPT_WORKFLOW_NAME = "A";
  Map<String, Workflow> workflows = new HashMap<>();

  public void add(Workflow workflow) {
    workflows.put(workflow.name(), workflow);
  }

  /**
   * Process a part through all the workflows until it is either accepted or rejected.
   *
   * @param part The part to process
   * @return True when the part is accepted, false when rejected
   */
  public boolean process(Part part) {
    String workflowName = START_WORKFLOW_NAME;
    while (!workflowName.equals(ACCEPT_WORKFLOW_NAME)
        && !workflowName.equals(REJECT_WORKFLOW_NAME)) {
      Workflow workflow = workflows.get(workflowName);
      if (workflow == null) {
        throw new IllegalStateException("Non-existing workflow: " + workflowName);
      }
      workflowName = workflow.process(part);
    }
    return workflowName.equals(ACCEPT_WORKFLOW_NAME);
  }

  public long calculateAcceptedCombinationCount() {
    Workflow startWorkflow = workflows.get(START_WORKFLOW_NAME);
    return getAcceptedCombinations(Combinations.all(), startWorkflow);
  }

  /**
   * Recursively find all the combinations leading to an accepted branch in the decision tree.
   *
   * @param combinations The combinations to check
   * @param workflow     The workflow to consider
   * @return The number of combinations which get accepted down the decision tree under
   *     the given workflow
   */
  private long getAcceptedCombinations(Combinations combinations, Workflow workflow) {
    long accepted = 0;
    for (Operation operation : workflow) {
      if (!operation.destination().equals(REJECT_WORKFLOW_NAME)) {
        Combinations matchedCombinations = combinations.apply(operation.condition());
        if (!matchedCombinations.hasSomeEmptyRanges()) {
          if (operation.destination().equals(ACCEPT_WORKFLOW_NAME)) {
            accepted += matchedCombinations.count();
          } else {
            Workflow nextWorkflow = workflows.get(operation.destination());
            long acceptedInBranch = getAcceptedCombinations(
                matchedCombinations, nextWorkflow);
            accepted += acceptedInBranch;
          }
        }
      }
      combinations = combinations.applyReverse(operation.condition());
    }
    return accepted;
  }
}
