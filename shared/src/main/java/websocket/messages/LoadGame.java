package websocket.messages;

public class LoadGame extends ServerMessage {

    String game;

    public LoadGame(String gameVar){
        super(ServerMessageType.LOAD_GAME);
        game = gameVar;
    }
}
