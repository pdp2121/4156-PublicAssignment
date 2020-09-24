package controllers;

import com.google.gson.Gson;
import io.javalin.Javalin;
import java.io.IOException;
import java.util.Queue;
import models.GameBoard;
import models.Message;
import models.Player;
import org.eclipse.jetty.websocket.api.Session;

class PlayGame {

  private static final int PORT_NUMBER = 8080;

  private static Javalin app;
  
  private static GameBoard gameboard = new GameBoard();
  private static Gson gsonLib = new Gson();
  

  /** Main method of the application.
   * @param args Command line arguments
   */
  public static void main(final String[] args) {

    app = Javalin.create(config -> {
      config.addStaticFiles("/public");
    }).start(PORT_NUMBER);

    // Test Echo Server
    app.post("/echo", ctx -> {
      ctx.result(ctx.body());
    });
    
    // newgame end points
    app.get("/newgame", ctx -> {
      ctx.redirect("tictactoe.html");
      //reset the gameboard if needed.
      gameboard = new GameBoard();
    });
    
    // startgame end points
    app.post("/startgame", ctx -> {
      //initialize player1
      Player player1 = new Player();
      player1.setId(1);
      
      //retrieve player1's type
      String requestBody = ctx.body();
      String[] tokens = requestBody.split("=");
      player1.setType(tokens[1].charAt(0));
      
      // send player1 to gameboard
      gameboard.setP1(player1);
      
      //send gameboard to UI
      String jsonGameBoard = gsonLib.toJson(gameboard);
      ctx.result(jsonGameBoard);
      sendGameBoardToAllPlayers(jsonGameBoard);
      
    });
    
    //joingame end points
    app.get("/joingame", ctx -> {
      //redirect to player2's html page
      ctx.redirect("tictactoe.html?p=2");
      
      //initialize player2
      Player player2 = new Player();
      player2.setId(2);
      
      //retrieve player1's type
      char player1Type = gameboard.getP1().getType();
      
      //retrieve player2's type based on player1's type
      if (player1Type == 'X') {
        player2.setType('O');
      } else {
        player2.setType('X');
      }
      
      //send player2 to gameboard and startgame
      gameboard.setP2(player2);
      gameboard.setGameStarted(true);
      String jsonGameBoard = gsonLib.toJson(gameboard);
      ctx.result(jsonGameBoard);
      sendGameBoardToAllPlayers(jsonGameBoard);
    });
    
    //move end points
    app.post("move/:playerId", ctx -> {
      //retrieve x and y coordinates of the current move
      String requestBody = ctx.body();
      String[] tokens = requestBody.split("&");
      int xcord = Character.getNumericValue(tokens[0].charAt(2));
      int ycord = Character.getNumericValue(tokens[1].charAt(2));
      
      //retrieve playerId based on path parameter
      int playerId = Integer.parseInt(ctx.pathParam("playerId"));
      
      //initialize current current player's type
      char type = 'X';
      
      //initialize response message
      Message response = new Message();
      
      //update the gameboard if move is valid
      if (gameboard.isValid(xcord, ycord, playerId)) {
        //set next turn to other player
        if (playerId == 1) {
          type = gameboard.getP1().getType();
          gameboard.setTurn(2);
        } else {
          type = gameboard.getP2().getType();
          gameboard.setTurn(1);
        }

        //fill in the gameboard
        char[][] board = gameboard.getBoardState();
        board[xcord][ycord] = type;
        gameboard.setBoardState(board);
        
        //check for winner and draw
        gameboard.checkWin(xcord, ycord, playerId);
        gameboard.setDraw(gameboard.checkDraw());
        
        //send message and gameboard to UI
        String jsonMessage = gsonLib.toJson(response);
        ctx.result(jsonMessage);
        String jsonGameBoard = gsonLib.toJson(gameboard);
        sendGameBoardToAllPlayers(jsonGameBoard);
      } else {
        //send message to UI for corresponding invalid move
        response.setCode(404);
        response.setMessage(gameboard.messageInvalid(xcord, ycord, playerId));
        response.setMoveValidity(false);
        String jsonMessage = gsonLib.toJson(response);
        ctx.result(jsonMessage);
      }
    });

    // Web sockets - DO NOT DELETE or CHANGE
    app.ws("/gameboard", new UiWebSocket());
  }

  /** Send message to all players.
   * @param gameBoardJson Gameboard JSON
   * @throws IOException Websocket message send IO Exception
   */
  private static void sendGameBoardToAllPlayers(final String gameBoardJson) {
    Queue<Session> sessions = UiWebSocket.getSessions();
    for (Session sessionPlayer : sessions) {
      try {
        sessionPlayer.getRemote().sendString(gameBoardJson);
      } catch (IOException e) {
        // Add logger here
      }
    }
  }

  public static void stop() {
    app.stop();
  }
}
