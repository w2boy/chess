package websocket.commands;

public class ConnectCommand extends UserGameCommand {

    CommandType type = CommandType.CONNECT;

    public ConnectCommand(String authToken, int gameID){
        super(authToken, gameID);
    }
}
