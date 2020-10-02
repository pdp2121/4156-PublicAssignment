package hw1;

import static org.junit.jupiter.api.Assertions.assertEquals;

import models.GameBoard;
import org.junit.jupiter.api.Test;

class UnitTest {
  
  GameBoard gameboard = new GameBoard();
  
  @Test
  //Test first condition of isValid: make a move before game started.
  void testValidBeforeStartMove() {
    boolean beforeStartMove = gameboard.isValid(0, 1, 1);
    assertEquals(false, beforeStartMove);
  }

  @Test
  //Test second condition of isValid: make a move on an occupied position.
  void testValidUnoccuppiedMove() {
    char[][] board = {{'\u0000', '\u0000', '\u0000'},
        {'\u0000', 'X', '\u0000'},
        {'\u0000', '\u0000', 'O'}};
    gameboard.setBoardState(board);
    gameboard.setGameStarted(true);
    boolean correctMove = gameboard.isValid(0, 1, 1);
    boolean incorrectMove = gameboard.isValid(1, 1, 1);
    assertEquals(true, correctMove);
    assertEquals(false, incorrectMove);
  }
  
  @Test
  //Test third condition of isValid: make a move when not on turn.
  void testValidPlayerMove() {
    gameboard.setGameStarted(true);
    boolean incorrectPlayerMove = gameboard.isValid(0, 1, 2);
    assertEquals(false, incorrectPlayerMove);
  }
  
  @Test
  //Test message for first condition of messageInvalid: make a move before game started.
  void testValidMsgBeforeStartMove() {
    String incorrectPlayerMsg = gameboard.messageInvalid(0, 1, 1);
    gameboard.setGameStarted(true);
    String correctPlayerMsg = gameboard.messageInvalid(0, 1, 1);
    assertEquals("", correctPlayerMsg);
    assertEquals("Please wait for both players to join!", incorrectPlayerMsg);
  }
  
  @Test
  //Test message for second condition of messageInvalid: make a move on an occupied position.
  void testValidMsgUnoccuppiedMove() {
    char[][] board = {{'\u0000', '\u0000', '\u0000'},
        {'\u0000', 'X', '\u0000'},
        {'\u0000', '\u0000', 'O'}};
    gameboard.setBoardState(board);
    gameboard.setGameStarted(true);
    String correctMsg = gameboard.messageInvalid(0, 1, 1);
    String incorrectMsg = gameboard.messageInvalid(1, 1, 1);
    assertEquals("", correctMsg);
    assertEquals("Please select a valid entry!", incorrectMsg);
  }
  
  @Test
  //Test message for third condition of messageInvalid: make a move when not on turn.
  void testValidMsgPlayerMove() {
    gameboard.setTurn(1);
    gameboard.setGameStarted(true);
    String incorrectPlayerMsg = gameboard.messageInvalid(0, 1, 2);
    String correctPlayerMsg = gameboard.messageInvalid(0, 1, 1);
    assertEquals("", correctPlayerMsg);
    assertEquals("Please wait for your turn!", incorrectPlayerMsg);
  }
  
  @Test
  // Test for first condition of checkWin: row
  void checkWinRow() {
    char[][] board = {{'\u0000', '\u0000', '\u0000'},
        {'X', 'X', 'X'},
        {'\u0000', 'O', 'O'}};
    gameboard.setBoardState(board);
    gameboard.checkWin(1, 1, 1);
    int winnerId = gameboard.getWinner();
    assertEquals(1, winnerId);
  }
  
  @Test
  //Test for second condition of checkWin: column.
  void checkWinColumn() {
    char[][] board = {{'\u0000', 'O', '\u0000'},
        {'X', 'O', 'X'},
        {'\u0000', 'O', 'X'}};
    gameboard.setBoardState(board);
    gameboard.checkWin(0, 1, 2);
    int winnerId = gameboard.getWinner();
    assertEquals(2, winnerId);
  }
  
  @Test
  //Test for third condition of checkWin: diagonal.
  void checkWinDiagonal() {
    char[][] board = {{'X', '\u0000', '\u0000'},
        {'O', 'X', '\u0000'},
        {'\u0000', 'O', 'X'}};
    gameboard.setBoardState(board);
    gameboard.checkWin(0, 0, 1);
    int winnerId = gameboard.getWinner();
    assertEquals(1, winnerId);
  }
  
  @Test
  //Test for forth condition of checkWin: reverse diagonal.
  void checkWinReverseDiagonal() {
    char[][] board = {{'X', '\u0000', 'O'},
        {'X', 'O', '\u0000'},
        {'O', '\u0000', 'X'}};
    gameboard.setBoardState(board);
    gameboard.checkWin(2, 0, 2);
    int winnerId = gameboard.getWinner();
    assertEquals(2, winnerId);
  }
  
  @Test
  //Test for first condition of checkDraw: no player has won.
  void checkDrawGameWon() {
    char[][] board = {{'X', '\u0000', 'O'},
        {'X', 'O', '\u0000'},
        {'O', '\u0000', 'X'}};
    gameboard.setBoardState(board);
    gameboard.checkWin(2, 0, 2);
    boolean gameWonDraw = gameboard.checkDraw();
    assertEquals(false, gameWonDraw);
  }
  
  @Test
  //Test for second condition of checkDraw: no more move could be made.
  void checkDrawBlank() {
    char[][] board = {{'O', 'X', 'O'},
        {'X', 'O', '\u0000'},
        {'X', 'O', 'X'}};
    gameboard.setBoardState(board);
    gameboard.checkWin(0, 0, 2);
    boolean blankDraw = gameboard.checkDraw();
    assertEquals(false, blankDraw);
  }
  
  @Test
  void checkDrawNoBlank() {
    char[][] board = {{'O', 'X', 'O'},
        {'X', 'O', 'X'},
        {'X', 'O', 'X'}};
    gameboard.setBoardState(board);
    gameboard.checkWin(1, 2, 1);
    boolean noBlankDraw = gameboard.checkDraw();
    assertEquals(true, noBlankDraw);
  }

}
