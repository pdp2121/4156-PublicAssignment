package models;

public class Message {

  private boolean moveValidity;

  private int code;

  private String message;
  
  /**
   * Constructor for Message
   */
  public Message() {
    moveValidity = true;
    code = 100;
    message = "";
  }

  /**
   * @return the moveValidity
   */
  public boolean isMoveValidity() {
    return moveValidity;
  }

  /**
   * @param moveValidity the moveValidity to set
   */
  public void setMoveValidity(boolean moveValidity) {
    this.moveValidity = moveValidity;
  }

  /**
   * @return the code
   */
  public int getCode() {
    return code;
  }

  /**
   * @param code the code to set
   */
  public void setCode(int code) {
    this.code = code;
  }

  /**
   * @return the message
   */
  public String getMessage() {
    return message;
  }

  /**
   * @param message the message to set
   */
  public void setMessage(String message) {
    this.message = message;
  }

}
