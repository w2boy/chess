package websocket.commands;

import websocket.messages.ServerMessage;

public class Connect extends UserGameCommand {

    CommandType type = CommandType.CONNECT;

    public Connect(String authToken, int gameID){
        super(authToken, gameID);
    }
}
