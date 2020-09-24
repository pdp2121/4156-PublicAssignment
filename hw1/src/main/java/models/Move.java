package models;

public class Move {

  private Player player;

  private int moveX;

  private int moveY;

  /**
   * Getter for player.
   * @return the player
   */
  public Player getPlayer() {
    return player;
  }

  /**
   * Setter for player.
   * @param player the player to set
   */
  public void setPlayer(Player player) {
    this.player = player;
  }

  /**
   * Getter for moveX.
   * @return the moveX
   */
  public int getMoveX() {
    return moveX;
  }

  /**
   * Setter for moveX.
   * @param moveX the moveX to set
   */
  public void setMoveX(int moveX) {
    this.moveX = moveX;
  }

  /**
   * Getter for moveY.
   * @return the moveY
   */
  public int getMoveY() {
    return moveY;
  }

  /**
   * Setter for moveY.
   * @param moveY the moveY to set
   */
  public void setMoveY(int moveY) {
    this.moveY = moveY;
  }

}
