package assignment2017;

import assignment2017.codeprovided.ColumnFullException;
import assignment2017.codeprovided.Connect4GameState;
import assignment2017.codeprovided.Connect4Player;
import assignment2017.codeprovided.IllegalColumnException;

/**
 * An AI player of Connect 4, which looks ahead two moves and tries to make the
 * best possible move. Should be more difficult to beat!
 * 
 * @author Daniel Marshall
 */
public class IntelligentPlayer extends Connect4Player {

  /**
   * Allows the AI player to submit a move, checking it is valid and then
   * carrying it out in the game. The AI creates two layers of copies of the
   * current gamestate, and uses them to test each move, checking for wins and
   * three in a rows to see which is the best position.
   * 
   * @param gamestate
   *          The current state of the game.
   */
  @Override
  public void makeMove(Connect4GameState gamestate) {

    boolean valid = true;
    int[] outerArray = new int[Connect4GameState.NUM_COLS];
    int[] innerArray = new int[Connect4GameState.NUM_COLS];

    for (int i = 0; i < Connect4GameState.NUM_COLS; i++) {

      Connect4GameState copy = gamestate.copy();
      try {
        copy.move(i);
      } catch (IllegalColumnException exception) {
        outerArray[i] = 10000;
      } catch (ColumnFullException exception) {
        outerArray[i] = 10000;
      }

      if (outerArray[i] != 10000) {

        for (int x = 0; x < innerArray.length; x++) {
          innerArray[x] = 0;
        }

        for (int j = 0; j < Connect4GameState.NUM_COLS; j++) {

          Connect4GameState secondCopy = copy.copy();

          try {
            secondCopy.move(j);
          } catch (IllegalColumnException exception) {
            innerArray[j] = -10000;
          } catch (ColumnFullException exception) {
            innerArray[j] = -10000;
          }

          if (secondCopy.getWinner() == secondCopy.whoseTurn()) {
            innerArray[j] = -1000;
          } else if (secondCopy.getWinner() == copy.whoseTurn()) {
            innerArray[j] = 1000;
          } else {

            boolean score = true;
            int[][] possibleWays = { { 1, 0 }, { 0, 1 }, { 1, 1 }, { 1, -1 } };

            for (int[] n : possibleWays) {

              int nextX = n[0];
              int nextY = n[1];

              for (int x = 0; x < Connect4GameState.NUM_COLS; x++) {
                for (int y = 0; y < Connect4GameState.NUM_ROWS; y++) {

                  int endY = y + (Connect4GameState.NUM_IN_A_ROW_TO_WIN - 1) * nextY;
                  int endX = x + (Connect4GameState.NUM_IN_A_ROW_TO_WIN - 1) * nextX;

                  if (endY >= 0 && endY < Connect4GameState.NUM_ROWS && endX >= 0
                      && endX < Connect4GameState.NUM_COLS) {

                    score = true;

                    if (secondCopy.getCounterAt(x, y) != Connect4GameState.EMPTY) {

                      for (int m = 1; m < Connect4GameState.NUM_IN_A_ROW_TO_WIN - 1; m++) {

                        if (secondCopy.getCounterAt(x + m * nextX, y + m * nextY) != secondCopy
                            .getCounterAt(x, y)) {
                          score = false;
                        }

                      }

                      try {

                        if (secondCopy.getCounterAt(endX, endY) != Connect4GameState.EMPTY) {
                          score = false;
                        }
                      } catch (ArrayIndexOutOfBoundsException exception) {
                        score = false;

                      }

                    } else {
                      score = false;
                    }

                    if (score) {

                      if (secondCopy.getCounterAt(x, y) == secondCopy.whoseTurn()) {
                        innerArray[j] -= 100;
                      } else {
                        innerArray[j] += 100;
                      }

                    }

                  }
                }
              }

            }

          }

        }

        int max = -2000;

        for (int n : innerArray) {
          // System.out.print(n + " ");
          if (n > max) {
            max = n;
          }

        }

        // System.out.println();
        outerArray[i] = max;

      }
    }

    int minmax = 2000;
    int finalMove = -1;

    for (int i = 0; i < outerArray.length; i++) {
      // System.out.print(outerArray[i] + " ");
      if (outerArray[i] < minmax) {
        minmax = outerArray[i];
        finalMove = i;
      } else if (outerArray[i] == minmax) {

        double rand = Math.random();
        if (rand > 0.5) {
          minmax = outerArray[i];
          finalMove = i;
        }

      }
    }

    // System.out.println();

    valid = true;

    try {
      valid = true;
      gamestate.move(finalMove);
    } catch (IllegalColumnException exception) {
      valid = false;
    } catch (ColumnFullException exception) {
      valid = false;
    }

    // System.out.println("Computer dropped counter in column " + finalMove);

  }

}
