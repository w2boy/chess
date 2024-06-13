package server;

import com.google.gson.Gson;
import dataaccess.SQLAuthDAO;
import dataaccess.SQLGameDAO;
import model.GameData;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import service.LoginRequest;
import spark.Spark;
import websocket.commands.*;
import websocket.messages.*;

import java.util.HashMap;
import java.util.Map;

@WebSocket
public class WebsocketRequestHandler {

    SQLAuthDAO sqlAuthDAO;
    SQLGameDAO sqlGameDAO;

    Map<Integer, Session> sessionMap = new HashMap<>();
    Map<Integer, String> usernameMap = new HashMap<>();

    WebsocketRequestHandler() {}

    public void setGameDAO(SQLGameDAO gameDAO){
        this.sqlGameDAO = gameDAO;
    }

    public void setAuthDAO(SQLAuthDAO authDAO){
        this.sqlAuthDAO = authDAO;
    }

    @OnWebSocketMessage
    public void onMessage(Session session, String msg) {
        String color = null;
        try {
            UserGameCommand command = new Gson().fromJson(msg, UserGameCommand.class);

            UserGameCommand.CommandType commandType =  command.getCommandType();

            String username = sqlAuthDAO.getUsername(command.getAuthString());

            switch (command.getCommandType()) {
                case CONNECT:
                    ConnectCommand connectCommand = new Gson().fromJson(msg, ConnectCommand.class);
                    // Associate the username with a gameID and a session.
                    saveSession(command.getGameID(), session);
                    saveUsernameToGame(command.getGameID(), username);
                    color = findColor(command.getGameID(), username, session);
                    connect(session, command.getGameID(), username, color, connectCommand);
                    break;
//                case MAKE_MOVE:
//                    makeMove(session, username, (MakeMoveCommand) command);
//                    break;
//                case LEAVE:
//                    leaveGame(session, username, (LeaveGameCommand) command);
//                    break;
//                case RESIGN:
//                    resign(session, username, (ResignCommand) command);
//                    break;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            sendMessage(session, new ErrorMessage("Error: " + ex.getMessage()));
        }
    }

    void sendMessage(Session session, ServerMessage serverMessage){
        String stringServerMessage = new Gson().toJson(serverMessage, ErrorMessage.class);
        try {
            session.getRemote().sendString(stringServerMessage);
        } catch (Exception ex) {
//            ex.printStackTrace();
//            sendMessage(session, new ErrorMessage("Error: " + ex.getMessage()));
        }

    }

    void saveSession(int gID, Session session){
        Integer gameID = gID;
        sessionMap.put(gameID, session);
    }

    void saveUsernameToGame(int gID, String username){
        Integer gameID = gID;
        usernameMap.put(gameID, username);
    }

    String findColor(int gameID, String username, Session session){
        String color = "observer";
        try {
            GameData gameData = sqlGameDAO.getGame(gameID);
            if (gameData.whiteUsername().equals(username)){
                color = "white";
            }
            else if (gameData.blackUsername().equals(username)){
                color = "black";
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            sendMessage(session, new ErrorMessage("Error: " + ex.getMessage()));
        }
        return color;
    }

    void connect(Session session, Integer gameID, String username, String color, ConnectCommand connectCommand){
        sendMessage(session, new LoadGameMessage("game"));

        // Cycle through the maps and send notifications to other sessions in game.
        for (Map.Entry<Integer, Session> entry : sessionMap.entrySet()) {
            if (entry.getKey().equals(gameID)) {
                Session otherSession = entry.getValue();
                if(otherSession != session){
                    String stringToSend = username + " joined the game as " + color;
                    sendMessage(session, new NotificationMessage(stringToSend));
                }
            }
        }
    }


}
