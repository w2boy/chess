package ui;

import websocket.messages.ServerMessage;

public interface ServerMessageObserver {
    public void receiveNotification(ServerMessage serverMessage);
}
