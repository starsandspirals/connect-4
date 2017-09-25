package assignment2017;

import assignment2017.codeprovided.Connect4Displayable;
import assignment2017.codeprovided.Connect4GameState;
import assignment2017.codeprovided.Connect4Player;

/**
 * The class containing the main logic allowing a game of Connect 4 to progress.
 * 
 * @author Daniel Marshall
 */
public class Connect4 {

  private final Connect4GameState gamestate;
  private int turn;
  private boolean finished;
  private boolean quit;
  private static Connect4Player player1;
  private static Connect4Player player2;
  private static Connect4Displayable display;

  public static boolean newGame;

  /**
   * Constructor for a game of Connect 4, with specified objects.
   * 
   * @param g
   *          The current state of the game.
   * @param p1
   *          The first player of Connect 4, who will play red.
   * @param p2
   *          The second player of Connect 4, who will play yellow.
   * @param d
   *          The display interface for the game.
   */
  public Connect4(Connect4GameState g, Connect4Player p1, Connect4Player p2,
      Connect4Displayable d) {

    gamestate = g;
    player1 = p1;
    player2 = p2;
    display = d;
    newGame = true;
    finished = false;
    quit = false;
    turn = 0;

  }

  /**
   * A method for starting a new game using the New Game button in the GUI.
   * 
   * @param g
   *          The current state of the game.
   * @param r
   *          The new player who will play Red.
   * @param y
   *          The new player who will play Yellow.
   */
  public static void newGame(Connect4GameState g, String r, String y) {

    switch (r) {
    case "Human":
      player1 = new GraphicsPlayer((Connect4GraphicsDisplay) display);
      break;
    case "Random":
      player1 = new RandomPlayer();
      break;
    case "Intelligent":
      player1 = new IntelligentPlayer();
      break;
    }

    switch (y) {
    case "Human":
      player2 = new GraphicsPlayer((Connect4GraphicsDisplay) display);
      break;
    case "Random":
      player2 = new RandomPlayer();
      break;
    case "Intelligent":
      player2 = new IntelligentPlayer();
      break;
    }

    newGame = true;
  }

  /**
   * The method containing logic for playing a game of Connect 4.
   */
  public void play() {

    gamestate.startGame();

    while (!quit) {

      if (newGame) {
        gamestate.startGame();
        turn = 0;
        finished = true;
        newGame = false;
      }

      while (!gamestate.gameOver()) {

        if (newGame) {
          break;
        }

        turn++;
        display.displayBoard();

        if (turn % 2 == 1) {
          player1.makeMove(gamestate);
        } else {
          player2.makeMove(gamestate);
        }

      }

      if (finished) {

        display.displayBoard();

        switch (gamestate.getWinner()) {
        case Connect4GameState.RED:
          System.out.println("R wins");
          break;
        case Connect4GameState.YELLOW:
          System.out.println("Y wins");
          break;
        default:
          System.out.println("No one wins");
        }

        finished = false;

      }

      if (display instanceof Connect4ConsoleDisplay) {
        quit = true;
      }

    }

  }

}
