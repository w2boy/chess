package websocket.messages;

public class LoadGameMessage extends ServerMessage {

    String game;

    public LoadGameMessage(String gameVar){
        super(ServerMessageType.LOAD_GAME);
        game = gameVar;
    }
}
