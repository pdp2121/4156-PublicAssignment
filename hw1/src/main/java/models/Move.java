package models;

public class Move {

  private Player player;

  private int moveX;

  private int moveY;

  /**
   * @return the player
   */
  public Player getPlayer() {
    return player;
  }

  /**
   * @param player the player to set
   */
  public void setPlayer(Player player) {
    this.player = player;
  }

  /**
   * @return the moveX
   */
  public int getMoveX() {
    return moveX;
  }

  /**
   * @param moveX the moveX to set
   */
  public void setMoveX(int moveX) {
    this.moveX = moveX;
  }

  /**
   * @return the moveY
   */
  public int getMoveY() {
    return moveY;
  }

  /**
   * @param moveY the moveY to set
   */
  public void setMoveY(int moveY) {
    this.moveY = moveY;
  }

}
