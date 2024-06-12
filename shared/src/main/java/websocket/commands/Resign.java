package websocket.commands;

public class Resign extends UserGameCommand {

    CommandType type = CommandType.RESIGN;

    public Resign(String authToken, int gameID){
        super(authToken, gameID);
    }
}
