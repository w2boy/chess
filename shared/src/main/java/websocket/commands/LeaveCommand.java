package websocket.commands;

public class LeaveCommand extends UserGameCommand {

    CommandType type = CommandType.LEAVE;

    public LeaveCommand(String authToken, int gameID){
        super(authToken, gameID);
    }
}
