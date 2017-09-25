package assignment2017;

import assignment2017.codeprovided.ColumnFullException;
import assignment2017.codeprovided.Connect4GameState;
import assignment2017.codeprovided.IllegalColumnException;
import assignment2017.codeprovided.IllegalRowException;

/**
 * A class containing the current state of a Connect 4 game. The class contains
 * information about the board, the number of turns, and whether someone has won
 * the game or not.
 * 
 * @author Daniel Marshall
 */
public class MyGameState extends Connect4GameState {

  private int numTurns;
  private int[][] board;

  /**
   * Initialises the game, setting the turn to the Red player and filling the
   * board with empty cells.
   */
  @Override
  public void startGame() {

    numTurns = 1;

    for (int i = 0; i < board.length; i++) {

      for (int j = 0; j < board[i].length; j++) {
        board[i][j] = EMPTY;
      }

    }

  }

  /**
   * Is called to make a move, dropping a counter into a column, unless the
   * column is full or an illegal number.
   * 
   * @param col
   *          The column that the counter is being dropped into.
   * @throws ColumnFullException
   *           An exception thrown if the column is full.
   * @throws IllegalColumnException
   *           An exception thrown for an illegal column.
   */
  @Override
  public void move(int col) throws ColumnFullException, IllegalColumnException {

    if (col < 0 || col >= NUM_COLS) {
      throw new IllegalColumnException(col);
    } else {

      int counter = whoseTurn();
      int currentValue = EMPTY;
      int i = 0;

      if (isColumnFull(col)) {
        throw new ColumnFullException(col);
      } else {

        do {
          currentValue = board[col][i];
          i++;
        } while (currentValue != EMPTY);

      }

      board[col][i - 1] = counter;
      numTurns++;

    }
  }

  /**
   * Returns the player whose turn it currently is.
   * 
   * @return The current player.
   */
  @Override
  public int whoseTurn() {

    if (numTurns % 2 == 1) {
      return RED;
    } else {
      return YELLOW;
    }

  }

  /**
   * Returns the counter at a specific position in the board.
   * 
   * @param col
   *          The column of the counter required.
   * @param row
   *          The row of the counter required.
   * @return The counter at the specified position.
   * @throws IllegalColumnException
   *           An exception thrown for an illegal column.
   * @throws IllegalRowException
   *           An exception thrown for an illegal row.
   */
  @Override
  public int getCounterAt(int col, int row) throws IllegalColumnException, IllegalRowException {

    if (col < 0 || col > NUM_COLS) {
      throw new IllegalColumnException(col);
    } else if (row < 0 || row > NUM_ROWS) {
      throw new IllegalRowException(row);
    } else {
      return board[col][row];
    }
  }

  /**
   * Returns whether the board is full or not.
   * 
   * @return True if the board is full, false if it is not.
   */
  @Override
  public boolean isBoardFull() {

    for (int i = 0; i < NUM_COLS; i++) {

      if (!isColumnFull(i)) {
        return false;
      }

    }

    return true;

  }

  /**
   * Returns whether a column is full or not.
   * 
   * @param col
   *          The column that is required.
   * @return True if the column is full, false if it is not.
   * @throws IllegalColumnException
   *           An exception thrown for an illegal column.
   */
  @Override
  public boolean isColumnFull(int col) throws IllegalColumnException {

    if (col < 0 || col > NUM_COLS) {
      throw new IllegalColumnException(col);
    } else {

      for (int i = 0; i < NUM_ROWS; i++) {

        if (board[col][i] == EMPTY) {
          return false;
        }

      }

      return true;

    }
  }

  /**
   * Returns the winner of the game if someone has won.
   * 
   * @return Red if red has won, yellow if yellow has won, empty otherwise.
   */
  @Override
  public int getWinner() {

    boolean won = true;
    int[][] possibleWays = { { 1, 0 }, { 0, 1 }, { 1, 1 }, { 1, -1 } };

    for (int[] i : possibleWays) {

      int nextX = i[0];
      int nextY = i[1];

      for (int x = 0; x < NUM_COLS; x++) {
        for (int y = 0; y < NUM_ROWS; y++) {

          int endY = y + (NUM_IN_A_ROW_TO_WIN - 1) * nextY;
          int endX = x + (NUM_IN_A_ROW_TO_WIN - 1) * nextX;

          if (endY >= 0 && endY < NUM_ROWS && endX >= 0 && endX < NUM_COLS) {

            won = true;

            if (board[x][y] != EMPTY) {

              for (int n = 1; n < NUM_IN_A_ROW_TO_WIN; n++) {

                if (board[x + n * nextX][y + n * nextY] != board[x][y]) {
                  won = false;
                }

              }

            } else {
              won = false;
            }

            if (won) {
              return board[x][y];
            }

          }
        }
      }

    }

    return EMPTY;
  }

  /**
   * Returns whether the game is over or not, i.e. if the board is full or
   * someone has won.
   * 
   * @return True if the game is over, false if not.
   */
  @Override
  public boolean gameOver() {

    if (isBoardFull()) {
      return true;
    } else if (getWinner() != EMPTY) {
      return true;
    } else {
      return false;
    }

  }

  /**
   * Deep copies the gamestate and returns the copy.
   * 
   * @return A deep copy of the current gamestate.
   */
  @Override
  public Connect4GameState copy() {

    int[][] copyBoard = new int[NUM_COLS][NUM_ROWS];

    for (int i = 0; i < NUM_COLS; i++) {
      for (int j = 0; j < NUM_ROWS; j++) {

        copyBoard[i][j] = this.getCounterAt(i, j);

      }
    }

    return new MyGameState(this.numTurns, copyBoard);

  }

  /**
   * No-arg constructor creating a default Connect 4 state.
   */
  public MyGameState() {

    numTurns = 1;
    board = new int[NUM_COLS][NUM_ROWS];

  }

  /**
   * Constructor creating a specified Connect 4 state.
   * 
   * @param n
   *          The number of turns that has passed.
   * @param b
   *          The current state of the board.
   */
  public MyGameState(int n, int[][] b) {

    numTurns = n;
    board = b;

  }

}
