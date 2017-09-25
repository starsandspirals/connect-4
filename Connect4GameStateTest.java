package assignment2017;

import org.junit.*;

import assignment2017.codeprovided.ColumnFullException;
import assignment2017.codeprovided.Connect4GameState;
import assignment2017.codeprovided.IllegalColumnException;

import static assignment2017.codeprovided.Connect4GameState.*;
import static org.junit.Assert.*;

/**
 * 
 * A JUnit test class that tests for basic functionality of the MyGameState
 * class, which should be part of your assignment submission.
 */

public class Connect4GameStateTest {

  Connect4GameState gs;

  // set up MyGameState object and start connect4 game
  @Before
  public void constructGameState() {
    gs = new MyGameState();
    gs.startGame();
  }

  // test for correct initialisation with all board positions empty
  // and Red given first turn
  @Test
  public void initialisation() {
    for (int col = 0; col < NUM_COLS; col++) {
      for (int row = 0; row < NUM_ROWS; row++) {
        assertEquals("During initialisation - not all board positions initialised to empty",
            Connect4GameState.EMPTY, gs.getCounterAt(col, row));
      }
    }

    assertEquals("After initialisation - first turn is not red's", gs.whoseTurn(), RED);
  }

  // test for correct initial moves
  @Test
  public void initialMoves() {
    String checkMoveMsg = "During initial moves";

    checkMove(checkMoveMsg, gs, 0, 0, RED);
    checkMove(checkMoveMsg, gs, 0, 1, YELLOW);
  }

  // test for correct use of ColumnFullException
  @Test(expected = ColumnFullException.class)
  public void columnFull() throws ColumnFullException {
    String columnFullMsg = "While testing for a full column";

    int col = 0;
    int counter = RED;

    for (int row = 0; row < NUM_ROWS; row++) {
      checkMove(columnFullMsg, gs, col, row, counter);
      counter = counter == RED ? YELLOW : RED;
    }

    assertTrue(columnFullMsg + "Column not full", gs.isColumnFull(col));
    assertFalse("Board is full, but shouldn't be", gs.isBoardFull());

    gs.move(col);
  }

  // test for correct use of BoardFullException
  @Test
  public void boardFull() {
    String boardFullMsg = "While testing for a full board";

    // fill up cols 0-5
    for (int col = 0; col < NUM_COLS - 1; col += 2) {
      int nextCol = col + 1;

      for (int row = 0; row < 3; row++) {
        checkMove(boardFullMsg, gs, col, row, RED);
        checkMove(boardFullMsg, gs, nextCol, row, YELLOW);
      }
      for (int row = 3; row < NUM_ROWS; row++) {
        checkMove(boardFullMsg, gs, nextCol, row, RED);
        checkMove(boardFullMsg, gs, col, row, YELLOW);
      }

      assertTrue(boardFullMsg + "Column not full", gs.isColumnFull(col));
      assertTrue(boardFullMsg + "Column not full", gs.isColumnFull(nextCol));
    }

    // fill up final col
    int col = 6;
    int counter = RED;

    for (int row = 0; row < NUM_ROWS; row++) {
      checkMove(boardFullMsg, gs, col, row, counter);
      counter = counter == RED ? YELLOW : RED;
    }
    assertTrue(boardFullMsg + "Column not full", gs.isColumnFull(col));
    assertTrue(boardFullMsg + "Board not full", gs.isBoardFull());
    assertTrue(boardFullMsg + "Game not over", gs.gameOver());
  }

  // test for different categories of win
  @Test
  public void horizontalWin() {
    String horizontalWinMsg = "While testing for a horizontal win";

    int redRow = 0;
    int yellowRow = 1;

    for (int col = 0; col < NUM_IN_A_ROW_TO_WIN; col++) {
      checkMove(horizontalWinMsg, gs, col, redRow, RED);

      if (col < NUM_IN_A_ROW_TO_WIN - 1) {
        checkMove(horizontalWinMsg, gs, col, yellowRow, YELLOW);
      }
    }

    assertTrue(horizontalWinMsg + "Game not over", gs.gameOver());
    assertEquals(horizontalWinMsg + "Winner not red", RED, gs.getWinner());
  }

  @Test
  public void verticalWin() {
    String verticalWinMsg = "While testing for a vertical win";

    int redCol = 0;
    int yellowCol = 1;

    for (int row = 0; row < NUM_IN_A_ROW_TO_WIN; row++) {
      checkMove(verticalWinMsg, gs, redCol, row, RED);

      if (row < NUM_IN_A_ROW_TO_WIN - 1) {
        checkMove(verticalWinMsg, gs, yellowCol, row, YELLOW);
      }
    }

    assertTrue(verticalWinMsg + "Game not over", gs.gameOver());
    assertEquals(verticalWinMsg + "Winner not red", RED, gs.getWinner());
  }

  @Test
  public void backslashWin() {
    String backslashWinMsg = "While testing for a backslash win";

    checkMove(backslashWinMsg, gs, 5, 0, RED);
    checkMove(backslashWinMsg, gs, 4, 0, YELLOW);
    checkMove(backslashWinMsg, gs, 3, 0, RED);
    checkMove(backslashWinMsg, gs, 3, 1, YELLOW);
    checkMove(backslashWinMsg, gs, 2, 0, RED);
    checkMove(backslashWinMsg, gs, 2, 1, YELLOW);
    checkMove(backslashWinMsg, gs, 1, 0, RED);
    checkMove(backslashWinMsg, gs, 2, 2, YELLOW);
    checkMove(backslashWinMsg, gs, 1, 1, RED);
    checkMove(backslashWinMsg, gs, 1, 2, YELLOW);
    checkMove(backslashWinMsg, gs, 5, 1, RED);
    checkMove(backslashWinMsg, gs, 1, 3, YELLOW);

    assertTrue(backslashWinMsg + "Game not over", gs.gameOver());
    assertEquals(backslashWinMsg + "Winner not yellow", YELLOW, gs.getWinner());
  }

  @Test
  public void backslashWinUpperRight() {
    String backslashWinMsg = "While testing for a backslash win (upper right)";

    checkMove(backslashWinMsg, gs, 3, 0, RED);
    checkMove(backslashWinMsg, gs, 4, 0, YELLOW);
    checkMove(backslashWinMsg, gs, 5, 0, RED);
    checkMove(backslashWinMsg, gs, 3, 1, YELLOW);
    checkMove(backslashWinMsg, gs, 4, 1, RED);
    checkMove(backslashWinMsg, gs, 5, 1, YELLOW);
    checkMove(backslashWinMsg, gs, 6, 0, RED);
    checkMove(backslashWinMsg, gs, 6, 1, YELLOW);
    checkMove(backslashWinMsg, gs, 4, 2, RED);
    checkMove(backslashWinMsg, gs, 5, 2, YELLOW);
    checkMove(backslashWinMsg, gs, 3, 2, RED);
    checkMove(backslashWinMsg, gs, 4, 3, YELLOW);
    checkMove(backslashWinMsg, gs, 3, 3, RED);
    checkMove(backslashWinMsg, gs, 3, 4, YELLOW);

    assertTrue(backslashWinMsg + "Game not over", gs.gameOver());
    assertEquals(backslashWinMsg + "Winner not yellow", YELLOW, gs.getWinner());
  }

  @Test
  public void forwardslashWin() {
    String forwardSlashWinMsg = "While testing for a forwardslash win";

    checkMove(forwardSlashWinMsg, gs, 0, 0, RED);
    checkMove(forwardSlashWinMsg, gs, 1, 0, YELLOW);
    checkMove(forwardSlashWinMsg, gs, 1, 1, RED);
    checkMove(forwardSlashWinMsg, gs, 2, 0, YELLOW);
    checkMove(forwardSlashWinMsg, gs, 2, 1, RED);
    checkMove(forwardSlashWinMsg, gs, 3, 0, YELLOW);
    checkMove(forwardSlashWinMsg, gs, 2, 2, RED);
    checkMove(forwardSlashWinMsg, gs, 3, 1, YELLOW);
    checkMove(forwardSlashWinMsg, gs, 3, 2, RED);
    checkMove(forwardSlashWinMsg, gs, 0, 1, YELLOW);
    checkMove(forwardSlashWinMsg, gs, 3, 3, RED);

    assertTrue(forwardSlashWinMsg + "Game not over", gs.gameOver());
    assertEquals(forwardSlashWinMsg + "Winner not red", RED, gs.getWinner());
  }

  @Test
  public void forwardslashWinUpperLeft() {
    String forwardSlashWinMsg = "While testing for a forwardslash win";

    checkMove(forwardSlashWinMsg, gs, 0, 0, RED);
    checkMove(forwardSlashWinMsg, gs, 1, 0, YELLOW);
    checkMove(forwardSlashWinMsg, gs, 2, 0, RED);
    checkMove(forwardSlashWinMsg, gs, 3, 0, YELLOW);
    checkMove(forwardSlashWinMsg, gs, 0, 1, RED);
    checkMove(forwardSlashWinMsg, gs, 1, 1, YELLOW);
    checkMove(forwardSlashWinMsg, gs, 1, 2, RED);
    checkMove(forwardSlashWinMsg, gs, 2, 1, YELLOW);
    checkMove(forwardSlashWinMsg, gs, 2, 2, RED);
    checkMove(forwardSlashWinMsg, gs, 3, 1, YELLOW);
    checkMove(forwardSlashWinMsg, gs, 2, 3, RED);
    checkMove(forwardSlashWinMsg, gs, 3, 2, YELLOW);
    checkMove(forwardSlashWinMsg, gs, 3, 3, RED);
    checkMove(forwardSlashWinMsg, gs, 0, 2, YELLOW);
    checkMove(forwardSlashWinMsg, gs, 3, 4, RED);

    assertTrue(forwardSlashWinMsg + "Game not over", gs.gameOver());
    assertEquals(forwardSlashWinMsg + "Winner not red", RED, gs.getWinner());
  }

  @Test
  public void copy() {
    String copyMsg = "While testing the copy method";

    checkMove(copyMsg, gs, 2, 0, RED);
    checkMove(copyMsg, gs, 2, 1, YELLOW);
    checkMove(copyMsg, gs, 3, 0, RED);

    Connect4GameState copy = gs.copy();

    assertEquals(copyMsg + "checking position (2,0)", RED, copy.getCounterAt(2, 0));
    assertEquals(copyMsg + "checking position (2,1)", YELLOW, copy.getCounterAt(2, 1));
    assertEquals(copyMsg + "checking position (3,0)", RED, copy.getCounterAt(3, 0));
    assertEquals(copyMsg + "checking turn", YELLOW, copy.whoseTurn());

    checkMove(copyMsg, gs, 4, 0, YELLOW);
    assertEquals(copyMsg + "checking deep copy - move", EMPTY, copy.getCounterAt(4, 0));
    assertEquals(copyMsg + "checking deep copy - turn", YELLOW, copy.whoseTurn());
  }

  // method to enable testing of moves using the Conntect4GameState object
  private void checkMove(String msg, Connect4GameState gs, int col, int row, int counter) {
    msg += " - ";

    try {
      gs.move(col);
      assertEquals(msg + "move made incorrectly - checking (" + col + "," + row + ")", counter,
          gs.getCounterAt(col, row));
      assertEquals(msg + "next turn counter not changed correctly", counter == RED ? YELLOW : RED,
          gs.whoseTurn());
    } catch (ColumnFullException e) {
      fail(msg + "ColumnFullException raised when column not full");
    } catch (IllegalColumnException e) {
      fail(msg + "IllegalColumnException raised on legal column");
    }
  }
}
