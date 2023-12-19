package problem.day19;

import java.util.HashMap;
import java.util.Map;

public class WorkflowSystem {
  private static final String START_WORKFLOW_NAME = "in";
  private static final String REJECT_WORKFLOW_NAME = "R";
  private static final String ACCEPT_WORKFLOW_NAME = "A";
  Map<String, Workflow> workflows = new HashMap<>();

  public void add(Workflow workflow) {
    workflows.put(workflow.name(), workflow);
  }

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

}
