package ui;

import websocket.commands.*;
import websocket.messages.ErrorMessage;
import websocket.messages.LoadGameMessage;
import websocket.messages.NotificationMessage;
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
                @Override
                public void onMessage(String message) {
                    try {
                        ServerMessage serverMessage = new Gson().fromJson(message, ServerMessage.class);

                        switch (serverMessage.getServerMessageType()) {
                            case LOAD_GAME:
                                LoadGameMessage loadGameMessage = new Gson().fromJson(message, LoadGameMessage.class);
                                observer.receiveNotification(loadGameMessage);
                                break;
                            case ERROR:
                                ErrorMessage errorMessage = new Gson().fromJson(message, ErrorMessage.class);
                                observer.receiveNotification(errorMessage);
                                break;
                            case NOTIFICATION:
                                NotificationMessage notificationMessage = new Gson().fromJson(message, NotificationMessage.class);
                                observer.receiveNotification(notificationMessage);
                                break;
                        }

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

    @Override
    public void onOpen(Session session, EndpointConfig endpointConfig) {
    }

}
