package assignment2017;

import java.util.Scanner;

import assignment2017.codeprovided.ColumnFullException;
import assignment2017.codeprovided.Connect4GameState;
import assignment2017.codeprovided.Connect4Player;
import assignment2017.codeprovided.IllegalColumnException;

/**
 * A human controlled player for Connect 4, with moves entered using the
 * keyboard.
 * 
 * @author Daniel Marshall
 */
public class KeyboardPlayer extends Connect4Player {

  private final Scanner input = new Scanner(System.in);

  /**
   * Allows the human player to submit a move, checking it is valid and then
   * carrying it out in the game.
   * 
   * @param gamestate
   *          The current state of the game.
   */
  @Override
  public void makeMove(Connect4GameState gamestate) {

    boolean valid = false;

    do {

      System.out.println("Please enter a column number, 0 to " + ((Connect4GameState.NUM_COLS) - 1)
          + " followed by return.");

      valid = true;

      while (!input.hasNextInt()) {
        System.out.println("Please enter a column number, 0 to "
            + ((Connect4GameState.NUM_COLS) - 1) + " followed by return.");
        input.next();
      }

      int entry = input.nextInt();

      try {
        gamestate.move(entry);
      } catch (IllegalColumnException exception) {
        valid = false;
      } catch (ColumnFullException exception) {
        valid = false;
      }

    } while (!valid);

  }

}
