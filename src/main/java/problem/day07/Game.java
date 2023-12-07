package problem.day07;

import java.util.HashMap;
import java.util.Map;

public class Game implements Comparable {
  private final static int FIVE_OF_A_KIND = 6;
  private final static int FOUR_OF_A_KIND = 5;
  private final static int FULL_HOUSE = 4;
  private final static int THREE_OF_A_KIND = 3;
  private final static int TWO_PAIR = 2;
  private final static int ONE_PAIR = 1;

  private final long bid;
  private int handType;
  private final int[] cardStrengths;
  private final Map<Character, Integer> cardCounts = new HashMap<>();

  public Game(String cards, long bid) {
    this.cardStrengths = new int[cards.length()];
    parseCards(cards);
    this.bid = bid;
    calculateHandType();
  }

  private void parseCards(String cards) {
    for (int i = 0; i < cards.length(); ++i) {
      char card = cards.charAt(i);
      addCard(card);
      cardStrengths[i] = getCardStrength(card);
    }
  }

  private int getCardStrength(char card) {
    return switch (card) {
      case 'A' -> 14;
      case 'K' -> 13;
      case 'Q' -> 12;
      case 'J' -> 11;
      case 'T' -> 10;
      default -> Integer.parseInt("" + card);
    };
  }

  private void addCard(char c) {
    int count = 1;
    if (cardCounts.containsKey(c)) {
      count = cardCounts.get(c) + 1;
    }
    cardCounts.put(c, count);
  }

  public long getBid() {
    return bid;
  }

  private void calculateHandType() {
    if (getNumberOfKind(5) == 1) {
      handType = FIVE_OF_A_KIND;
    } else if (getNumberOfKind(4) == 1) {
      handType = FOUR_OF_A_KIND;
    } else if (getNumberOfKind(3) == 1) {
      if (getNumberOfKind(2) == 1) {
        handType = FULL_HOUSE;
      } else {
        handType = THREE_OF_A_KIND;
      }
    } else if (getNumberOfKind(2) == 2) {
      handType = TWO_PAIR;
    } else if (getNumberOfKind(2) == 1) {
      handType = ONE_PAIR;
    } else {
      handType = 0;
    }
  }

  private int getNumberOfKind(int count) {
    int n = 0;
    for (Integer c : cardCounts.values()) {
      if (c == count) {
        n++;
      }
    }
    return n;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("[");
    for (int i = 0; i < cardStrengths.length; ++i) {
      sb.append(cardStrengths[i]);
      sb.append(" ");
    }
    sb.append("]  ");
    for (Map.Entry<Character, Integer> e : cardCounts.entrySet()) {
      int count = e.getValue();
      if (count > 1) {
        sb.append(count);
        sb.append("x");
      }
      sb.append(e.getKey());
      sb.append(" ");
    }
    sb.append(" ");
    sb.append(handType);
    return sb.toString();
  }

  @Override
  public int compareTo(Object o) {
    if (!(o instanceof Game g)) {
      throw new IllegalArgumentException("Can't compare game to " + o.getClass());
    }
    int comparison;
    if (g.handType > this.handType) {
      comparison = -1;
    } else if (g.handType < this.handType) {
      comparison = 1;
    } else {
      comparison = compareCardByCard(g);
    }
    return comparison;
  }

  private int compareCardByCard(Game g) {
    int comparison = 0;
    int i = 0;
    while (i < cardStrengths.length && comparison == 0) {
      if (g.cardStrengths[i] > this.cardStrengths[i]) {
        comparison = -1;
      } else if (g.cardStrengths[i] < this.cardStrengths[i]) {
        comparison = 1;
      }
      i++;
    }
    return comparison;
  }
}
