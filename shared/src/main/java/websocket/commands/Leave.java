package websocket.commands;

public class Leave extends UserGameCommand {

    CommandType type = CommandType.LEAVE;

    public Leave(String authToken, int gameID){
        super(authToken, gameID);
    }
}
