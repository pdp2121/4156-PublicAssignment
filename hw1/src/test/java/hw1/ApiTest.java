package hw1;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.google.gson.Gson;
import controllers.PlayGame;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import kong.unirest.json.JSONObject;
import models.GameBoard;
import models.Player;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

public class ApiTest {
  /**
  * Runs only once before the testing starts.
  */
  @BeforeAll
  public static void init() {
    // Start Server
    PlayGame.main(null);
    System.out.println("Before All");
  }
  
  /**
  * This method starts a new game before every test run. It will run every time before a test.
  */
  @BeforeEach
  public void startNewGame() {
    // Test if server is running. You need to have an endpoint /
    // If you do not wish to have this end point, it is okay to not have anything in this method.
    HttpResponse<String> response = Unirest.get("http://localhost:8080/").asString();
    int restStatus = response.getStatus();

    System.out.println("Before Each");
  }
   

  /**
   * This is a test case to evaluate the newgame endpoint.
   */
  @Test
  @Order(1)
  public void newGameTest() {

    // Create HTTP request and get response
    HttpResponse<String> response = Unirest.get("http://localhost:8080/newgame").asString();
    int restStatus = response.getStatus();
    String restBody = response.getBody();
    System.out.println(restBody);

    // Check assert statement (New Game has started)
    assertEquals(restStatus, 200);
    System.out.println("Test New Game");
  }

  /**
   * This is a test case to evaluate the startgame endpoint.
   */
  @Test
  @Order(2)
  public void startGameTest() {

    // Create a POST request to startgame endpoint and get the body
    HttpResponse<String> responseNew = Unirest.get("http://localhost:8080/newgame").asString();
    HttpResponse<String> response = Unirest.post("http://localhost:8080/startgame").body("type=X").asString();
    String responseBody = response.getBody();

    // --------------------------- JSONObject Parsing ----------------------------------

    System.out.println("Start Game Response: " + responseBody);

    // Parse the response to JSON object
    JSONObject jsonObject = new JSONObject(responseBody);

    // Check if game started after player 1 joins: Game should not start at this point
    assertEquals(false, jsonObject.get("gameStarted"));

    // ---------------------------- GSON Parsing -------------------------

    // GSON use to parse data to object
    Gson gson = new Gson();
    GameBoard gameBoard = gson.fromJson(jsonObject.toString(), GameBoard.class);
    Player player1 = gameBoard.getP1();

    // Check if player type is correct
    assertEquals('X', player1.getType());

    System.out.println("Test Start Game");
  }
  
  /**
   * This is a test case to evaluate the joingame endpoint.
   */
  @Test
  @Order(3)
  public void joinGameTest() {
    HttpResponse<String> responseStart = Unirest.post("http://localhost:8080/startgame").body("type=X").asString();
    HttpResponse<String> response = Unirest.get("http://localhost:8080/joingame").asString();
    int restStatus = response.getStatus();
    assertEquals(restStatus, 200);
    System.out.println("Test Join Game");
  }
  
  /**
   * This is a test case to evaluate the Test Case 1:
   * A player cannot make a move until both players have joined.
   */
  @Test
  @Order(4)
  public void moveBeforeStartTest() {
    //Start a new game
    HttpResponse<String> responseNew = Unirest.get("http://localhost:8080/newgame").asString();
    
    //A player makes a move before the other joins.
    HttpResponse<String> responseMoveIncorrect = Unirest.post("http://localhost:8080/move/1").body("X=0&Y=0").asString();
    String responseMoveIncorrectBody = responseMoveIncorrect.getBody();
    System.out.println("Incorrect Move Response: " + responseMoveIncorrectBody);
    JSONObject jsonObjectIncorrect = new JSONObject(responseMoveIncorrectBody);
    
    //Expect moveValidity to be false.
    assertEquals(false, jsonObjectIncorrect.get("moveValidity"));
    
    //Now the other player joins.
    HttpResponse<String> responseStart = Unirest.post("http://localhost:8080/startgame").body("type=O").asString();
    HttpResponse<String> responseJoin = Unirest.get("http://localhost:8080/joingame").asString();
    
    //The player now make a move.
    HttpResponse<String> responseMoveCorrect = Unirest.post("http://localhost:8080/move/1").body("X=0&Y=0").asString();
    String responseMoveCorrectBody = responseMoveCorrect.getBody();
    System.out.println("Incorrect Move Response: " + responseMoveCorrectBody);
    JSONObject jsonObjectCorrect = new JSONObject(responseMoveCorrectBody);
    
    //Expect moveValidity to be true.
    assertEquals(true, jsonObjectCorrect.get("moveValidity"));
    
    
    System.out.println("Test Case 1: A player cannot make a move until both players have joined.");
  }
  
  /**
   * This is a test case to evaluate the Test Case 2:
   * After game has started Player 1 always makes the first move.
   */
  @Test
  @Order(5)
  
  public void movePlayer1FirstTest() {
    //Start the game.
    HttpResponse<String> responseNew = Unirest.get("http://localhost:8080/newgame").asString();
    HttpResponse<String> responseStart = Unirest.post("http://localhost:8080/startgame").body("type=X").asString();
    HttpResponse<String> responseJoin = Unirest.get("http://localhost:8080/joingame").asString();
    
    //Player2 tries to make the first move.
    HttpResponse<String> responseMoveIncorrect = Unirest.post("http://localhost:8080/move/2").body("X=0&Y=0").asString();
    String responseMoveIncorrectBody = responseMoveIncorrect.getBody();
    System.out.println("Incorrect Move Response: " + responseMoveIncorrectBody);
    JSONObject jsonObjectIncorrect = new JSONObject(responseMoveIncorrectBody);
    
    //Expect moveValidity to be false.
    assertEquals(false, jsonObjectIncorrect.get("moveValidity"));
    
    //Player1 tries to make the first move.
    HttpResponse<String> responseMoveCorrect = Unirest.post("http://localhost:8080/move/1").body("X=0&Y=0").asString();
    String responseMoveCorrectBody = responseMoveCorrect.getBody();
    System.out.println("Correct Move Response: " + responseMoveCorrectBody);
    JSONObject jsonObjectCorrect = new JSONObject(responseMoveCorrectBody);
    
    //Expect moveValidity to be false.
    assertEquals(true, jsonObjectCorrect.get("moveValidity"));
    
    System.out.println("Test Case 2: After game has started Player 1 always makes the first move.");
  }
  
  /**
   * This is a test case to evaluate the Test Case 3:
   * A player cannot make two moves in their turn.
   */
  @Test
  @Order(6)
  
  public void moveNoDupTest() {
    //Start a new game.
    HttpResponse<String> responseNew = Unirest.get("http://localhost:8080/newgame").asString();
    HttpResponse<String> responseStart = Unirest.post("http://localhost:8080/startgame").body("type=X").asString();
    HttpResponse<String> responseJoin = Unirest.get("http://localhost:8080/joingame").asString();
    
    //Player1 makes the first move.
    HttpResponse<String> responseMoveCorrect = Unirest.post("http://localhost:8080/move/1").body("X=1&Y=0").asString();
    String responseMoveCorrectBody = responseMoveCorrect.getBody();
    System.out.println("Correct Move Response: " + responseMoveCorrectBody);
    JSONObject jsonObjectCorrect = new JSONObject(responseMoveCorrectBody);
    
    //Expect moveValidity to be true.
    assertEquals(true, jsonObjectCorrect.get("moveValidity"));
    
    //Player1 tries to make the next consecutive move.
    HttpResponse<String> responseMoveIncorrect = Unirest.post("http://localhost:8080/move/1").body("X=0&Y=0").asString();
    String responseMoveIncorrectBody = responseMoveIncorrect.getBody();
    System.out.println("Incorrect Move Response: " + responseMoveIncorrectBody);
    JSONObject jsonObjectIncorrect = new JSONObject(responseMoveIncorrectBody);
    
    //Expect moveValidity to be false.
    assertEquals(false, jsonObjectIncorrect.get("moveValidity"));
    
    
    
    System.out.println("Test Case 3: A player cannot make two moves in their turn.");
  }
  
  /**
   * This is a test case to evaluate the joingame endpoint.
   */
  @Test
  @Order(6)
  
  public void moveWinTest() {
    // Start the game.
    HttpResponse<String> responseNew = Unirest.get("http://localhost:8080/newgame").asString();
    HttpResponse<String> responseStart = Unirest.post("http://localhost:8080/startgame").body("type=X").asString();
    HttpResponse<String> responseJoin = Unirest.get("http://localhost:8080/joingame").asString();
    
    // Setup a win for Player1
    HttpResponse<String> moveP1At00 = Unirest.post("http://localhost:8080/move/1").body("X=0&Y=0").asString();
    HttpResponse<String> moveP2At01 = Unirest.post("http://localhost:8080/move/2").body("X=0&Y=1").asString();
    HttpResponse<String> moveP1At10 = Unirest.post("http://localhost:8080/move/1").body("X=1&Y=0").asString();
    HttpResponse<String> moveP2At11 = Unirest.post("http://localhost:8080/move/2").body("X=1&Y=1").asString();
    HttpResponse<String> moveP1At21 = Unirest.post("http://localhost:8080/move/1").body("X=2&Y=0").asString();
    
    // Retrieve the gameboard by using getgameboard endpoints.
    HttpResponse<String> responseGB = Unirest.get("http://localhost:8080/getgameboard").asString();
    String responseGbBody  = responseGB.getBody();
    JSONObject jsonGB = new JSONObject(responseGbBody);
    
    // Expect winner to be 1.
    assertEquals(1, jsonGB.get("winner"));
    System.out.println("Test Case 4: A player can win the game.");
  }
  
  /**
   * This is a test case to evaluate the joingame endpoint.
   */
  @Test
  @Order(7)
  public void moveDrawTest() {
    //Start a game.
    HttpResponse<String> responseNew = Unirest.get("http://localhost:8080/newgame").asString();
    HttpResponse<String> responseStart = Unirest.post("http://localhost:8080/startgame").body("type=X").asString();
    HttpResponse<String> responseJoin = Unirest.get("http://localhost:8080/joingame").asString();
    
    //Setup a draw.
    HttpResponse<String> moveP1At11 = Unirest.post("http://localhost:8080/move/1").body("X=1&Y=1").asString();
    HttpResponse<String> moveP2At00 = Unirest.post("http://localhost:8080/move/2").body("X=0&Y=0").asString();
    HttpResponse<String> moveP1At10 = Unirest.post("http://localhost:8080/move/1").body("X=1&Y=0").asString();
    HttpResponse<String> moveP2At20 = Unirest.post("http://localhost:8080/move/2").body("X=2&Y=0").asString();
    HttpResponse<String> moveP1At02 = Unirest.post("http://localhost:8080/move/1").body("X=0&Y=2").asString();
    HttpResponse<String> moveP2At01 = Unirest.post("http://localhost:8080/move/2").body("X=0&Y=1").asString();
    HttpResponse<String> moveP1At22 = Unirest.post("http://localhost:8080/move/1").body("X=2&Y=2").asString();
    HttpResponse<String> moveP2At12 = Unirest.post("http://localhost:8080/move/2").body("X=1&Y=2").asString();
    HttpResponse<String> moveP1At21 = Unirest.post("http://localhost:8080/move/1").body("X=2&Y=1").asString();
    
    //Retrieve the gameboard.
    HttpResponse<String> responseGB = Unirest.get("http://localhost:8080/getgameboard").asString();
    String responseGbBody  = responseGB.getBody();
    JSONObject jsonGB = new JSONObject(responseGbBody);
    
    //Expect isDraw to be true.
    assertEquals(true, jsonGB.get("isDraw"));
    
    System.out.println("Test Case 5: A draw game.");
  }
  
  @AfterEach
  public void finishGame() {
    System.out.println("After Each");
  }

  /**
   * This method runs only once after all the test cases have been executed.
   */
  @AfterAll
  public static void close() {
    // Stop Server
    PlayGame.stop();
    System.out.println("After All");
  }
}
