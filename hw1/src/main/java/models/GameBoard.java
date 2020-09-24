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
   * Constructor for GameBoard.
   */
  public GameBoard() {
    gameStarted = false;
    turn = 1;
    boardState = new char[3][3];
    winner = 0;
    isDraw = false;
  }

  /**
   * Getter for p1.
   * @return the p1
   */
  public Player getP1() {
    return p1;
  }

  /**
   * Setter for p1.
   * @param p1 the p1 to set
   */
  public void setP1(Player p1) {
    this.p1 = p1;
  }

  /**
   * Getter for p2.
   * @return the p2
   */
  public Player getP2() {
    return p2;
  }

  /**
   * Setter for p2.
   * @param p2 the p2 to set
   */
  public void setP2(Player p2) {
    this.p2 = p2;
  }

  /**
   * Getter for gameStarted.
   * @return the gameStarted
   */
  public boolean isGameStarted() {
    return gameStarted;
  }

  /**
   * Setter for gameStarted.
   * @param gameStarted the gameStarted to set
   */
  public void setGameStarted(boolean gameStarted) {
    this.gameStarted = gameStarted;
  }

  /**
   * Getter for turn.
   * @return the turn
   */
  public int getTurn() {
    return turn;
  }

  /**
   * Setter for turn.
   * @param turn the turn to set
   */
  public void setTurn(int turn) {
    this.turn = turn;
  }

  /**
   * Getter for boardState.
   * @return the boardState
   */
  public char[][] getBoardState() {
    return boardState;
  }

  /**
   * Setter for boardState.
   * @param boardState the boardState to set
   */
  public void setBoardState(char[][] boardState) {
    this.boardState = boardState;
  }

  /**
   * Getter for winner.
   * @return the winner
   */
  public int getWinner() {
    return winner;
  }

  /**
   * Setter for winner.
   * @param winner the winner to set
   */
  public void setWinner(int winner) {
    this.winner = winner;
  }

  /**
   * Getter for isDraw.
   * @return the isDraw
   */
  public boolean isDraw() {
    return isDraw;
  }

  /**
   * Setter for isDraw.
   * @param isDraw the isDraw to set
   */
  public void setDraw(boolean isDraw) {
    this.isDraw = isDraw;
  }

  
  /**
   * Decide whether a move is valid in current state.
   * @param x coordinate and y coordinate of a player's move and the player's Id.
   * @return boolean whether the move is valid or not.
   * @apiNote check move validity.
   */
  public boolean isValid(int x, int y, int playerId) {
    
    if (this.boardState[x][y] == '\u0000' && playerId == this.turn) {
      return true;
    }
    return false;
  }
  
  /**
   * Return a message for invalid move.
   * @param x coordinate and y coordinate of a player's move and the player's Id.
   * @return String message.
   * @apiNote 
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
   * Check for winner for each valid move.
   * @param x coordinate and y coordinate of a player's move and the player's Id.
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
  
  /** Check for draw at every move.
   * @return boolean whether the game is a draw.
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
