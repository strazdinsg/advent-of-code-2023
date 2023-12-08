package problem.day07;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * A class representing one Camel Card game.
 */
public class Game implements Comparable<Game> {
  private static final int FIVE_OF_A_KIND = 6;
  private static final int FOUR_OF_A_KIND = 5;
  private static final int FULL_HOUSE = 4;
  private static final int THREE_OF_A_KIND = 3;
  private static final int TWO_PAIR = 2;
  private static final int ONE_PAIR = 1;
  private static final char JOKER = 'J';

  private final long bid;
  private int handType;
  private final boolean useJokers;
  private int jokerCount = 0;
  private final int[] cardStrengths;
  private final Map<Character, Integer> cardCounts = new HashMap<>();

  /**
   * Create a new game.
   *
   * @param cards     The cards for this game
   * @param bid       The bid for this game
   * @param useJokers When true, interpret J as jokers; interpret those as Jacks when false
   */
  public Game(String cards, long bid, boolean useJokers) {
    this.useJokers = useJokers;
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
      case 'J' -> useJokers ? 1 : 11;
      case 'T' -> 10;
      default -> Integer.parseInt("" + card);
    };
  }

  private void addCard(char c) {
    if (useJokers && c == JOKER) {
      addJoker();
    } else {
      int count = 1;
      if (cardCounts.containsKey(c)) {
        count = cardCounts.get(c) + 1;
      }
      cardCounts.put(c, count);
    }
  }

  private void addJoker() {
    jokerCount++;
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
    if (useJokers) {
      improveHandTypeWithJokers();
    }
  }

  private void improveHandTypeWithJokers() {
    if (jokerCount > 0) {
      if (handType == FOUR_OF_A_KIND && jokerCount == 1) {
        handType = FIVE_OF_A_KIND;
      } else if (handType == THREE_OF_A_KIND) {
        if (jokerCount == 2) {
          handType = FIVE_OF_A_KIND;
        } else {
          handType = FOUR_OF_A_KIND;
        }
      } else if (handType == TWO_PAIR && jokerCount == 1) {
        handType = FULL_HOUSE;
      } else if (handType == ONE_PAIR) {
        handType = switch (jokerCount) {
          case 1 -> THREE_OF_A_KIND;
          case 2 -> FOUR_OF_A_KIND;
          case 3 -> FIVE_OF_A_KIND;
          default -> throw new IllegalStateException("Can't have that many jokers");
        };
      } else {
        // Nothing on hand, jokers improve it all
        handType = switch (jokerCount) {
          case 1 -> ONE_PAIR;
          case 2 -> THREE_OF_A_KIND;
          case 3 -> FOUR_OF_A_KIND;
          case 4 -> FIVE_OF_A_KIND;
          case 5 -> FIVE_OF_A_KIND;
          default -> throw new IllegalStateException("Can't have that many jokers");
        };
      }
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
    for (int cardStrength : cardStrengths) {
      sb.append(cardStrength);
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
    sb.append("    ");
    sb.append(jokerCount);
    return sb.toString();
  }

  @Override
  public int compareTo(Game g) {
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

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof Game g)) {
      throw new IllegalArgumentException("Can't compare game to a " + obj);
    }
    return compareTo(g) == 0;
  }

  @Override
  public int hashCode() {
    int result = Objects.hash(bid, handType, useJokers, jokerCount, cardCounts);
    result = 31 * result + Arrays.hashCode(cardStrengths);
    return result;
  }
}
