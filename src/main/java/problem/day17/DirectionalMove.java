package problem.day17;

import tools.Direction;

/**
 * A straight move a specific direction.
 *
 * @param direction The direction of the move
 * @param moves     The length of the move - how many blocks we are moving in this direction
 */
public record DirectionalMove(Direction direction, int moves) {
  public static int minMovesInOneDirection = 1;
  public static int maxMovesInOneDirection = 3;

  /**
   * Move one step in the given direction.
   *
   * @param direction The direction to take
   * @return A new move or null if it is not allowed to move in the given direction considering
   *     the historical moves so far.
   */
  public DirectionalMove step(Direction direction) {
    DirectionalMove nextMove = null;
    if (direction != this.direction) {
      if (moves >= minMovesInOneDirection && direction != this.direction.getOpposite()) {
        nextMove = new DirectionalMove(direction, 1);
      }
    } else if (moves < maxMovesInOneDirection) {
      nextMove = new DirectionalMove(direction, moves + 1);
    }
    return nextMove;
  }

  @Override
  public String toString() {
    return moves + " x " + direction;
  }

  public boolean isValid() {
    return moves >= minMovesInOneDirection && moves <= maxMovesInOneDirection;
  }
}
