package assignment2017;

import assignment2017.codeprovided.Connect4Displayable;
import assignment2017.codeprovided.Connect4GameState;

/**
 * A class which displays the board state of a Connect 4 game in the terminal.
 * 
 * @author Daniel Marshall
 */
public class Connect4ConsoleDisplay implements Connect4Displayable {

  private final Connect4GameState displayGamestate;

  /**
   * Displays the current state of the Connect 4 board in the terminal.
   */
  @Override
  public void displayBoard() {

    for (int j = MyGameState.NUM_ROWS - 1; j >= 0; j--) {

      System.out.print("| ");

      for (int i = 0; i < MyGameState.NUM_COLS; i++) {

        switch (displayGamestate.getCounterAt(i, j)) {

        case MyGameState.RED:
          System.out.print("R ");
          break;
        case MyGameState.YELLOW:
          System.out.print("Y ");
          break;
        default:
          System.out.print("  ");
          break;

        }

      }

      System.out.println(" |");
    }

    System.out.print(" ");

    for (int i = 0; i <= MyGameState.NUM_COLS; i++) {
      System.out.print("--");
    }

    System.out.println(" ");

    System.out.print(" ");

    for (int i = 0; i < MyGameState.NUM_COLS; i++) {
      System.out.print(" " + i);
    }

    System.out.println("");
  }

  /**
   * Constructor for a console display with a specified gamestate.
   * 
   * @param gamestate
   *          The current state of the game.
   */
  public Connect4ConsoleDisplay(Connect4GameState gamestate) {
    displayGamestate = gamestate;
  }

}
