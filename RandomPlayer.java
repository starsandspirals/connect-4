package assignment2017;

import assignment2017.codeprovided.ColumnFullException;
import assignment2017.codeprovided.Connect4GameState;
import assignment2017.codeprovided.Connect4Player;
import assignment2017.codeprovided.IllegalColumnException;

/**
 * An AI player of Connect 4, making random moves on the board. Should be easy
 * to beat!
 * 
 * @author Daniel Marshall
 */
public class RandomPlayer extends Connect4Player {

  /**
   * Allows the AI player to submit a move, checking it is valid and then
   * carrying it out in the game.
   * 
   * @param gamestate
   *          The current state of the game.
   */
  @Override
  public void makeMove(Connect4GameState gamestate) {

    boolean valid = true;
    int entry = 0;

    do {

      valid = true;
      entry = (int) (Math.random() * Connect4GameState.NUM_COLS);

      try {
        gamestate.move(entry);
      } catch (IllegalColumnException exception) {
        valid = false;
      } catch (ColumnFullException exception) {
        valid = false;
      }

    } while (!valid);

    System.out.println("Computer dropped counter in column " + entry);

  }

}
