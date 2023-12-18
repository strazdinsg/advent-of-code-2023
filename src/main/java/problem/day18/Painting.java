package problem.day18;

import tools.Logger;
import java.util.*;

public class Painting {
  private final PriorityQueue<HorizontalEdge>  sortedEdges = new PriorityQueue<>();

  public void add(List<Edge> edges) {
    for (Edge edge : edges) {
      if (edge.isHorizontal()) {
        sortedEdges.add(HorizontalEdge.createFrom(edge));
      }
    }
  }

  public long findInteriorArea() {
    long area = 0;
    printEdges();
    while (!sortedEdges.isEmpty()) {
      HorizontalEdge topEdge = sortedEdges.poll();
      Logger.info("Top " + topEdge.toString());
      HorizontalEdge bottomEdge = findBottomEdge(topEdge);
      Logger.info("  bottom " + bottomEdge);
      HorizontalEdge projectedBottomEdge = bottomEdge.getIntersectedPart(topEdge);
      Logger.info("  intersect " + projectedBottomEdge);
      area += getArea(topEdge, projectedBottomEdge);
      addNewEdges(topEdge.minus(projectedBottomEdge));
      addNewEdges(bottomEdge.minus(projectedBottomEdge));
    }
    return area;
  }

  private void addNewEdges(List<HorizontalEdge> newEdges) {
    for (HorizontalEdge edge : newEdges) {
      Logger.info("  adding " + edge);
    }
    sortedEdges.addAll(newEdges);
  }

  private long getArea(HorizontalEdge topEdge, HorizontalEdge projectedBottomEdge) {
    long width = projectedBottomEdge.width();
    long height = projectedBottomEdge.y() - topEdge.y() + 1;
    return width * height;
  }

  private void printEdges() {
    for (HorizontalEdge edge : sortedEdges) {
      Logger.info(edge.toString());
    }
  }

  private HorizontalEdge findBottomEdge(HorizontalEdge topEdge) {
    HorizontalEdge bottomEdge = null;
    Iterator<HorizontalEdge> it = sortedEdges.iterator();
    while (bottomEdge == null && it.hasNext()) {
      bottomEdge = it.next();
      if (bottomEdge == topEdge || !bottomEdge.projectionIntersects(topEdge)) {
        bottomEdge = null;
      }
    }
    if (bottomEdge == null) {
      throw new IllegalStateException("Bottom edge not found!");
    }
    sortedEdges.remove(bottomEdge);
    return bottomEdge;
  }

}
