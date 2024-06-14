package server;

import chess.ChessGame;
import chess.ChessMove;
import com.google.gson.Gson;
import dataaccess.SQLAuthDAO;
import dataaccess.SQLGameDAO;
import model.GameData;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import websocket.commands.*;
import websocket.messages.*;

import java.util.*;

@WebSocket
public class WebsocketRequestHandler {

    SQLAuthDAO sqlAuthDAO;
    SQLGameDAO sqlGameDAO;

    ChessGame.TeamColor playerColor = null;
    ChessGame.TeamColor oponentColor = null;
    String oponentUsername = null;

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
                    notifyOtherSessions(session, command.getGameID(), username, stringToSend, "NOTIFICATION");
                    break;
                case MAKE_MOVE:
                    MakeMoveCommand makeMoveCommand = new Gson().fromJson(msg, MakeMoveCommand.class);
                    GameData gameData = sqlGameDAO.getGame(makeMoveCommand.getGameID());
                    findColor(command.getGameID(), username, session);
                    if (!verifyTurn(gameData.game())){
                        sendMessage(session, new ErrorMessage("Error: Not Your Turn!"));
                        break;
                    }
                    // If the move is valid make the move and check for endgame conditions. If any endgame conditions, notify other sessions
                    if (verifyMove(makeMoveCommand.getChessMove(), gameData.game())){
                        ChessGame updatedGame = makeMove(gameData.game(), makeMoveCommand.getChessMove(), session, makeMoveCommand.getGameID());
                        sendMessage(session, new LoadGameMessage("game"));
                        stringToSend = username + " made the move " + makeMoveCommand.getChessMove().toString();
                        notifyOtherSessions(session, makeMoveCommand.getGameID(), username, stringToSend, "LOADGAME");
                        if (updatedGame.isInCheck(oponentColor)){
                            stringToSend = oponentUsername + " is in check!";
                            sendMessage(session, new NotificationMessage(stringToSend));
                            notifyOtherSessions(session, makeMoveCommand.getGameID(), username, stringToSend, "NOTIFICATION");
                        }
                        if (updatedGame.isInCheckmate(oponentColor)){
                            stringToSend = oponentUsername + " is checkmated! " + username + " wins!";
                            sendMessage(session, new NotificationMessage(stringToSend));
                            notifyOtherSessions(session, makeMoveCommand.getGameID(), username, stringToSend, "NOTIFICATION");
                            sendMessage(session, new NotificationMessage("The game has ended."));
                            notifyOtherSessions(session, makeMoveCommand.getGameID(), username, "The game has ended.", "NOTIFICATION");
                        }
                        if (updatedGame.isInStalemate(oponentColor)){
                            stringToSend = "The game is in stalemate. It's a tie!";
                            sendMessage(session, new NotificationMessage(stringToSend));
                            notifyOtherSessions(session, makeMoveCommand.getGameID(), username, stringToSend, "NOTIFICATION");
                            sendMessage(session, new NotificationMessage("The game has ended."));
                            notifyOtherSessions(session, makeMoveCommand.getGameID(), username, "The game has ended.", "NOTIFICATION");
                        }
                    } else {
                        sendMessage(session, new ErrorMessage("Error: INVALID MOVE"));
                        break;
                    }
                    break;
                case LEAVE:
                    LeaveCommand leaveCommand = new Gson().fromJson(msg, LeaveCommand.class);
                    color = findColor(leaveCommand.getGameID(), username, session);
                    leaveGame(leaveCommand.getGameID(), username, color, session);
                    stringToSend = username + " left the game";
                    notifyOtherSessions(session, leaveCommand.getGameID(), username, stringToSend, "NOTIFICATION");
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
                if (gameData.whiteUsername().equals(username)){
                    color = "white";
                    playerColor = ChessGame.TeamColor.WHITE;
                    oponentColor = ChessGame.TeamColor.BLACK;
                    oponentUsername = gameData.blackUsername();
                }
                if (gameData.blackUsername().equals(username)){
                    color = "black";
                    playerColor = ChessGame.TeamColor.BLACK;
                    oponentColor = ChessGame.TeamColor.WHITE;
                    oponentUsername = gameData.whiteUsername();
                }
        } catch (Exception ex) {
            ex.printStackTrace();
            sendMessage(session, new ErrorMessage("Error: " + ex.getMessage()));
        }
        return color;
    }

    private boolean verifyTurn(ChessGame chessGame){
        if (chessGame.getTeamTurn() == playerColor){
            return true;
        }
        return false;
    }


    private boolean verifyMove(ChessMove chessMove, ChessGame chessGame){
        Collection<ChessMove> validMoves = chessGame.validMoves(chessMove.getStartPosition());
        if (validMoves.contains(chessMove)){
            return true;
        }
        return false;
    }

    private ChessGame makeMove(ChessGame chessGame, ChessMove chessMove, Session session, int gameID){
        try{
            chessGame.makeMove(chessMove);
            sqlGameDAO.updateGame(chessGame, gameID);
        } catch (Exception ex) {
            ex.printStackTrace();
            sendMessage(session, new ErrorMessage("Error Making Move on Server Side: " + ex.getMessage()));
        }
        return chessGame;
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

    void notifyOtherSessions(Session session, Integer gameID, String username, String stringToSend, String messageType){

        // Cycle through the maps and send notifications to other sessions in game.
        for (Map.Entry<Integer, Set<Session>> entry : sessionMap.entrySet()) {
            if (entry.getKey().equals(gameID)) {
                Set<Session> sessionsInGame = entry.getValue();
                for (Session otherSession : sessionsInGame){
                    String otherUsername = usernameMap.get(otherSession);
                    if(!otherUsername.equals(username)){
                        if (messageType.equals("NOTIFICATION")){
                            sendMessage(otherSession, new NotificationMessage(stringToSend));
                        } else if (messageType.equals("LOADGAME")){
                            sendMessage(otherSession, new LoadGameMessage("game"));
                            sendMessage(otherSession, new NotificationMessage(stringToSend));
                        }

                    }
                }
            }
        }
    }


}
