package problem.day05;


import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class IntegerRangeMap {
  private IntegerRangeMap linkedMap = null;
  private List<IntegerRangePair> pairs = new LinkedList<>();

  public void setLinkedMap(IntegerRangeMap linkedMap) {
    this.linkedMap = linkedMap;
  }

  public void addRange(long sourceStart, long destStart, long length) {
    pairs.add(new IntegerRangePair(sourceStart, destStart, length));
  }

  public long findFinalMappingFor(long seed) {
    long mappedValue = findMappingFor(seed);
    if (linkedMap != null) {
      mappedValue = linkedMap.findFinalMappingFor(mappedValue);
    }
    return mappedValue;
  }

  private long findMappingFor(long seed) {
    long mapping = -1;
    Iterator<IntegerRangePair> it = pairs.iterator();
    while (mapping < 0 && it.hasNext()) {
      IntegerRangePair pair = it.next();
      mapping = pair.getMappingFor(seed);
    }
    if (mapping == -1) {
      mapping = seed;
    }
    return mapping;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (IntegerRangePair pair : pairs) {
      sb.append(pair.toString());
      sb.append("    ");
    }
    return sb.toString();
  }
}
