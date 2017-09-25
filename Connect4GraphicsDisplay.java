package assignment2017;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import assignment2017.codeprovided.Connect4Displayable;
import assignment2017.codeprovided.Connect4GameState;

/**
 * A class which displays the current state of a Connect 4 game in a graphical
 * user interface, using various Swing components.
 * 
 * @author Daniel Marshall
 */
public class Connect4GraphicsDisplay extends JFrame implements Connect4Displayable {

  private Connect4GameState displayGamestate;
  private GraphicsCounter[][] graphicsBoard;
  private Graphics graphics;
  private Dimension screenDimensions;
  private Container contentPane;
  private int currentMove = -1;
  private boolean newGame = false;

  /**
   * Constructor for a graphical display with a specified gamestate.
   * 
   * @param gamestate
   *          The current state of the game.
   */
  public Connect4GraphicsDisplay(Connect4GameState gamestate) {
    displayGamestate = gamestate;
    graphicsBoard = new GraphicsCounter[Connect4GameState.NUM_COLS][Connect4GameState.NUM_ROWS];

    setTitle("Connect 4");

    Toolkit toolkit = Toolkit.getDefaultToolkit();

    screenDimensions = toolkit.getScreenSize();
    setSize(screenDimensions.width / 3 + 108, screenDimensions.height / 3 + 64);
    setLocation(new Point(screenDimensions.width / 4, screenDimensions.height / 3));

    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    contentPane = getContentPane();
    contentPane.setLayout(new BorderLayout());

    JPanel board = new JPanel();
    board.setSize(new Dimension(screenDimensions.width / 3, screenDimensions.height / 3));
    board.setLayout(new GridLayout(Connect4GameState.NUM_ROWS, Connect4GameState.NUM_COLS));

    for (int i = 0; i < graphicsBoard.length; i++) {
      for (int j = 0; j < graphicsBoard[i].length; j++) {
        graphicsBoard[i][j] = new GraphicsCounter();
        contentPane.add(graphicsBoard[i][j]);
      }
    }

    for (int i = 0; i < graphicsBoard[0].length; i++) {
      for (int j = 0; j < graphicsBoard.length; j++) {
        board.add(graphicsBoard[j][Connect4GameState.NUM_ROWS - 1 - i]);
      }
    }

    JPanel buttons = new JPanel();
    buttons.setLayout(new GridLayout(1, 0));
    JButton[] moveButtons = new JButton[Connect4GameState.NUM_COLS];

    JPanel game = new JPanel();
    game.setLayout(new BorderLayout());

    for (int i = 0; i < moveButtons.length; i++) {
      moveButtons[i] = new JButton("" + i);

      final Integer innerI = new Integer(i);

      moveButtons[i].addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          currentMove = innerI;
        }
      });

      buttons.add(moveButtons[i]);
    }

    game.add(buttons, BorderLayout.SOUTH);
    game.add(board, BorderLayout.CENTER);
    contentPane.add(game, BorderLayout.CENTER);

    JPanel options = new JPanel();
    options.setLayout(new GridLayout(0, 1));

    String[] items = { "Human", "Random", "Intelligent" };

    JLabel labelRed = new JLabel("Red");
    JComboBox selectRed = new JComboBox(items);
    JLabel labelYellow = new JLabel("Yellow");
    JComboBox selectYellow = new JComboBox(items);
    JButton newGame = new JButton("New Game");

    labelRed.setHorizontalAlignment(JLabel.CENTER);
    labelRed.setVerticalAlignment(JLabel.CENTER);
    labelYellow.setHorizontalAlignment(JLabel.CENTER);
    labelYellow.setVerticalAlignment(JLabel.CENTER);

    selectRed.setSelectedIndex(2);

    newGame.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        String red = selectRed.getSelectedItem().toString();
        String yellow = selectYellow.getSelectedItem().toString();

        Connect4.newGame(gamestate, red, yellow);
      }
    });

    options.add(labelRed);
    options.add(selectRed);
    options.add(labelYellow);
    options.add(selectYellow);
    options.add(newGame);

    contentPane.add(options, BorderLayout.EAST);

    setVisible(true);
  }

  /**
   * A counter object used to store the state of each position in the board and
   * display it to the GUI.
   * 
   * @author Daniel Marshall
   *
   */
  public class GraphicsCounter extends JPanel {

    private int counterColor;

    /**
     * Sets the colour of the specified counter.
     * 
     * @param g
     *          The graphics interface.
     * @param c
     *          The colour of the counter.
     */
    protected void drawCounter(Graphics g, int c) {
      counterColor = c;
    }

    /**
     * Paints the specified counter onto the GUI.
     * 
     * @param g
     *          The graphics interface.
     */
    @Override
    protected void paintComponent(Graphics g) {
      int h = this.getHeight();
      int w = this.getWidth();
      super.paintComponent(g);

      this.setBackground(Color.BLUE);

      switch (counterColor) {
      case Connect4GameState.RED:
        g.setColor(Color.RED);
        break;
      case Connect4GameState.YELLOW:
        g.setColor(Color.YELLOW);
        break;
      case Connect4GameState.EMPTY:
        g.setColor(Color.WHITE);
      }

      g.fillOval(w / 4, h / 4, w / 2, h / 2);
    }
  }

  /**
   * Displays the board state on the screen.
   * 
   */
  public void displayBoard() {

    graphics = this.getGraphics();

    for (int j = 0; j < Connect4GameState.NUM_ROWS; j++) {
      for (int i = 0; i < Connect4GameState.NUM_COLS; i++) {

        int color = displayGamestate.getCounterAt(i, j);

        graphicsBoard[i][j].drawCounter(graphics, color);
        graphicsBoard[i][j].repaint();

      }
    }
  }

  /**
   * Gets the current move selected using the buttons on the GUI.
   * 
   * @return The move selected.
   */
  public int getMove() {

    while (currentMove == -1 && Connect4.newGame == false) {
      try {
        Thread.sleep(200);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }

    int move = currentMove;
    currentMove = -1;
    return move;

  }
}
