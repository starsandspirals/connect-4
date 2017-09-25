package assignment2017;

import assignment2017.codeprovided.Connect4Displayable;
import assignment2017.codeprovided.Connect4GameState;
import assignment2017.codeprovided.Connect4Player;

/**
 * The entry point of the program, which creates all the required objects to
 * play a game of Connect 4.
 * 
 * @author Daniel Marshall
 */
public class PlayConnect4 {

  public static void main(String[] args) {

    Connect4GameState gameState = new MyGameState();

    Connect4Displayable display;
    Connect4Player red;
    Connect4Player yellow;

    if (args[0].equals("gui")) {
      display = new Connect4GraphicsDisplay(gameState);
      red = new IntelligentPlayer();
      yellow = new GraphicsPlayer(display);
    } else {
      display = new Connect4ConsoleDisplay(gameState);
      red = new RandomPlayer();
      yellow = new KeyboardPlayer();
    }

    Connect4 game = new Connect4(gameState, red, yellow, display);

    game.play();

  }

}
