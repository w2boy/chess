package ui;

import websocket.messages.ErrorMessage;
import websocket.messages.ServerMessage;

import javax.websocket.*;
import java.net.URI;
import java.util.Scanner;
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
        } catch(Exception ex) {

        }
    }

//    public static void main(String[] args) throws Exception {
//        var ws = new WebsocketCommunicator(this);
//        Scanner scanner = new Scanner(System.in);
//
//        System.out.println("Enter a message you want to echo");
//        while (true) ws.send(scanner.nextLine());
//    }

    public void send(String msg) throws Exception {
        this.session.getBasicRemote().sendText(msg);
    }

    public void onOpen(Session session, EndpointConfig endpointConfig) {
    }

    public void function(){
        observer.receiveNotification(new ServerMessage(ServerMessage.ServerMessageType.NOTIFICATION));
    }

}
