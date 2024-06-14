package server;

import com.google.gson.Gson;
import dataaccess.SQLAuthDAO;
import dataaccess.SQLGameDAO;
import model.GameData;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import websocket.commands.*;
import websocket.messages.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@WebSocket
public class WebsocketRequestHandler {

    SQLAuthDAO sqlAuthDAO;
    SQLGameDAO sqlGameDAO;

    Map<Integer, Set<Session>> sessionMap = new HashMap<>();
    Map<Session, String> usernameMap = new HashMap<>();

    WebsocketRequestHandler() {}

    public void setGameDAO(SQLGameDAO gameDAO){
        this.sqlGameDAO = gameDAO;
    }

    public void setAuthDAO(SQLAuthDAO authDAO){
        this.sqlAuthDAO = authDAO;
    }

    @OnWebSocketMessage
    public void onMessage(Session session, String msg) {
        // System.out.println(msg);
        String color = null;
        try {
            UserGameCommand command = new Gson().fromJson(msg, UserGameCommand.class);

            String username = sqlAuthDAO.getUsername(command.getAuthString());

            switch (command.getCommandType()) {
                case CONNECT:
                    ConnectCommand connectCommand = new Gson().fromJson(msg, ConnectCommand.class);
                    // Associate the username with a gameID and a session.
                    saveSession(command.getGameID(), session);
                    saveUsernameToGame(username, session);
                    color = findColor(command.getGameID(), username, session);

                    sendMessage(session, new LoadGameMessage("game"));
                    String stringToSend = username + " joined the game as " + color;
                    notifyOtherSessions(session, command.getGameID(), username, stringToSend);
                    break;
//                case MAKE_MOVE:
//                    makeMove(session, username, (MakeMoveCommand) command);
//                    break;
                case LEAVE:
                    LeaveCommand leaveCommand = new Gson().fromJson(msg, LeaveCommand.class);
                    color = findColor(command.getGameID(), username, session);
                    leaveGame(command.getGameID(), username, color, session);
                    stringToSend = username + " left the game";
                    notifyOtherSessions(session, command.getGameID(), username, stringToSend);
                    break;
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
        String stringServerMessage = new Gson().toJson(serverMessage);
        try {
            session.getRemote().sendString(stringServerMessage);
        } catch (Exception ex) {
//            ex.printStackTrace();
//            sendMessage(session, new ErrorMessage("Error: " + ex.getMessage()));
        }

    }

    void saveSession(int gID, Session session){
        Integer gameID = gID;
        if (sessionMap.get(gameID) == null){
            sessionMap.put(gameID, new HashSet<Session>());
        }
        sessionMap.get(gameID).add(session);
    }

    void saveUsernameToGame(String username, Session session){
        usernameMap.put(session, username);
    }

    String findColor(int gameID, String username, Session session){
        String color = "observer";
        try {
            GameData gameData = sqlGameDAO.getGame(gameID);
            if (gameData.whiteUsername() != null){
                if (gameData.whiteUsername().equals(username)){
                    color = "white";
                }
            }
            else if (gameData.blackUsername() != null){
                if (gameData.blackUsername().equals(username)){
                    color = "black";
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            sendMessage(session, new ErrorMessage("Error: " + ex.getMessage()));
        }
        return color;
    }

    void leaveGame(int gameID, String username, String color, Session session){
        try{
            if (!color.equals("observer")){
                sqlGameDAO.leaveGame(gameID, username, color);
            }
            sessionMap.get(gameID).remove(session);
        } catch (Exception ex){
            ex.printStackTrace();
            sendMessage(session, new ErrorMessage("Error leaving game on Server Side: " + ex.getMessage()));
        }
    }

    void notifyOtherSessions(Session session, Integer gameID, String username, String stringToSend){

        // Cycle through the maps and send notifications to other sessions in game.
        for (Map.Entry<Integer, Set<Session>> entry : sessionMap.entrySet()) {
            if (entry.getKey().equals(gameID)) {
                Set<Session> sessionsInGame = entry.getValue();
                for (Session otherSession : sessionsInGame){
                    String otherUsername = usernameMap.get(otherSession);
                    if(!otherUsername.equals(username)){
                        sendMessage(otherSession, new NotificationMessage(stringToSend));
                    }
                }
            }
        }
    }


}
