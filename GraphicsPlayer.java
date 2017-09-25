package assignment2017;

import assignment2017.codeprovided.ColumnFullException;
import assignment2017.codeprovided.Connect4Displayable;
import assignment2017.codeprovided.Connect4GameState;
import assignment2017.codeprovided.Connect4Player;
import assignment2017.codeprovided.IllegalColumnException;

/**
 * A human controlled player for Connect 4, with moves entered using the GUI.
 * 
 * @author Daniel Marshall
 *
 */
public class GraphicsPlayer extends Connect4Player {

  private Connect4Displayable display;

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

      try {

        valid = true;
        int entry = ((Connect4GraphicsDisplay) display).getMove();

        if (Connect4.newGame == false) {
          gamestate.move(entry);
        }

      } catch (IllegalColumnException exception) {
        valid = false;
      } catch (ColumnFullException exception) {
        valid = false;
      }

    } while (valid == false);

  }

  /** The constructor for a graphical player.
   * 
   * @param d The display interface.
   */
  public GraphicsPlayer(Connect4Displayable d) {
    display = d;
  }

}
