package websocket.commands;

public class ResignCommand extends UserGameCommand {

    CommandType type = CommandType.RESIGN;

    public ResignCommand(String authToken, int gameID){
        super(authToken, gameID);
    }
}
