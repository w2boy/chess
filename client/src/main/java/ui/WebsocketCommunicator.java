package ui;

import websocket.commands.*;
import websocket.messages.ErrorMessage;
import websocket.messages.ServerMessage;

import javax.websocket.*;
import java.net.URI;
import com.google.gson.Gson;

public class WebsocketCommunicator extends Endpoint {

    ServerMessageObserver observer;
    public Session session;

    public WebsocketCommunicator(ServerMessageObserver serverMessageObserver) {
        try {
            observer = serverMessageObserver;
            URI uri = new URI("ws://localhost:8080/ws");
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            this.session = container.connectToServer(this, uri);

            this.session.addMessageHandler(new MessageHandler.Whole<String>() {
                public void onMessage(String message) {
                    try {
                        ServerMessage serverMessage = new Gson().fromJson(message, ServerMessage.class);
                        observer.receiveNotification(serverMessage);
                    } catch(Exception ex) {
                        observer.receiveNotification(new ErrorMessage(ex.getMessage()));
                    }
                }
            });
        } catch (Exception ex) {

        }
    }

    public void send(UserGameCommand userGameCommand) {
        try {
            String msg = null;
            switch (userGameCommand.getCommandType()) {
                case CONNECT:
                    msg = new Gson().toJson(userGameCommand);
                    break;
                case LEAVE:
                    msg = new Gson().toJson(userGameCommand);
                    break;
                case MAKE_MOVE:
                    msg = new Gson().toJson(userGameCommand);
                    break;
                case RESIGN:
                    msg = new Gson().toJson(userGameCommand);
                    break;
            }
            this.session.getBasicRemote().sendText(msg);
        } catch (Exception ex) {
            observer.receiveNotification(new ErrorMessage(ex.getMessage()));
        }

    }

    public void onOpen(Session session, EndpointConfig endpointConfig) {
    }

}
