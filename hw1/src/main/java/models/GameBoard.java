package models;

public class GameBoard {

  private Player p1;

  private Player p2;

  private boolean gameStarted;

  private int turn;

  private char[][] boardState;

  private int winner;

  private boolean isDraw;

  /**
   * Constructor for GameBoard
   */
  public GameBoard() {
    gameStarted = false;
    turn = 1;
    boardState = new char[3][3];
    winner = 0;
    isDraw = false;
  }

  /**
   * @return the p1
   */
  public Player getP1() {
    return p1;
  }

  /**
   * @param p1 the p1 to set
   */
  public void setP1(Player p1) {
    this.p1 = p1;
  }

  /**
   * @return the p2
   */
  public Player getP2() {
    return p2;
  }

  /**
   * @param p2 the p2 to set
   */
  public void setP2(Player p2) {
    this.p2 = p2;
  }

  /**
   * @return the gameStarted
   */
  public boolean isGameStarted() {
    return gameStarted;
  }

  /**
   * @param gameStarted the gameStarted to set
   */
  public void setGameStarted(boolean gameStarted) {
    this.gameStarted = gameStarted;
  }

  /**
   * @return the turn
   */
  public int getTurn() {
    return turn;
  }

  /**
   * @param turn the turn to set
   */
  public void setTurn(int turn) {
    this.turn = turn;
  }

  /**
   * @return the boardState
   */
  public char[][] getBoardState() {
    return boardState;
  }

  /**
   * @param boardState the boardState to set
   */
  public void setBoardState(char[][] boardState) {
    this.boardState = boardState;
  }

  /**
   * @return the winner
   */
  public int getWinner() {
    return winner;
  }

  /**
   * @param winner the winner to set
   */
  public void setWinner(int winner) {
    this.winner = winner;
  }

  /**
   * @return the isDraw
   */
  public boolean isDraw() {
    return isDraw;
  }

  /**
   * @param isDraw the isDraw to set
   */
  public void setDraw(boolean isDraw) {
    this.isDraw = isDraw;
  }
  /**
   * 
   * @param x
   * @param y
   * @return
   */
  
  /**
   * 
   * @param x
   * @param y
   * @param playerId
   * @return boolean
   * @apiNote check move validity
   */
  public boolean isValid(int x, int y, int playerId) {
    
    if (this.boardState[x][y] == '\u0000' && playerId == this.turn) {
      return true;
    }
    return false;
  }
  
  /**
   * 
   * @param x
   * @param y
   * @param playerId
   * @return String
   * @apiNote return a message for invalid move
   */
  public String messageInvalid(int x, int y, int playerId) {
    String log = "";
    if (this.boardState[x][y] != '\u0000') {
      log = "Please select a valid entry!";
    }
    if (playerId != this.turn) {
      log = "Please wait for your turn!";
    }
    return log;
  }
  
  /**
   * 
   * @param x
   * @param y
   * @param playerId
   * @apiNote check for winner for each valid move
   */
  public void checkWin(int x, int y, int playerId) {
    // check column
    for (int i = 0; i < 3; i++) {
      if (this.boardState[x][i] != this.boardState[x][y]) {
        break;
      }
      if (i == 2) {
        this.winner = playerId;
      }
    }
    
    //check row
    for (int i = 0; i < 3; i++) {
      if (this.boardState[i][y] != this.boardState[x][y]) {
        break;
      }
      if (i == 2) {
        this.winner = playerId;
      }
    }
    
    // check 2 diagonals
    if (x == y) {
      for (int i = 0; i < 3; i++) {
        if (this.boardState[i][i] != this.boardState[x][y]) {
          break;
        }
        if (i == 2) {
          this.winner = playerId;
        }
      }
    }
    
    if (x + y == 2) {
      for (int i = 0; i < 3; i++) {
        if (this.boardState[i][2 - i] != this.boardState[x][y]) {
          break;
        }
        if (i == 2) {
          this.winner = playerId;
        }
      }
    }
  }
  
  /**
   * 
   * @apiNote check for draw
   */
  public boolean checkDraw() {
    //return false if the game has a winner
    if (this.winner != 0) {
      return false;
    }
    //return false if there's an unused entry
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        if (this.boardState[i][j] == '\u0000') {
          return false;
        }
      }
    }
    //otherwise return true
    return true;
  }

}
