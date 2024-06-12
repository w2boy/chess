package websocket.messages;

public class Notification extends ServerMessage {

    String message;

    public Notification(String msg){
        super(ServerMessageType.NOTIFICATION);
        message = msg;
    }
}
