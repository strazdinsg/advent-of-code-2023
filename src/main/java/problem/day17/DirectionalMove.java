package problem.day17;

import tools.Direction;

public record DirectionalMove(Direction direction, int moves) {
  private static final int MAX_MOVES_IN_ONE_DIRECTION = 3;

  public DirectionalMove step(Direction direction) {
    DirectionalMove nextMove = null;
    if (direction != this.direction) {
      if (!direction.isOppositeTo(this.direction)) {
        nextMove = new DirectionalMove(direction, 1);
      }
    } else if (moves < MAX_MOVES_IN_ONE_DIRECTION) {
      nextMove = new DirectionalMove(direction, moves + 1);
    }
    return nextMove;
  }

  @Override
  public String toString() {
    return moves + " x " + direction;
  }
}
