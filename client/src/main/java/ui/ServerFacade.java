package ui;

import chess.ChessBoard;
import chess.ChessMove;
import com.google.gson.Gson;
import model.UserData;

import java.io.*;
import java.net.*;
import java.util.List;
import java.util.Scanner;

import model.*;
import server.Server;
import websocket.commands.ConnectCommand;
import websocket.commands.LeaveCommand;
import websocket.commands.MakeMoveCommand;

public class ServerFacade {

    private final String serverUrl;
    private ClientCommunicator clientCommunicator = new ClientCommunicator();
    private ServerMessageObserver observer;

    public ServerFacade(String url, ServerMessageObserver serverMessageObserver) {
        serverUrl = url;
        this.observer = serverMessageObserver;
    }

    public ClearResult clearAllData() throws ResponseException {
        var path = "/db";
        ClearResult clearResult = clientCommunicator.makeRequest("DELETE", path, "", ClearResult.class, serverUrl, null);
        return clearResult;
    }

    public LoginResult register(UserData newUser) throws ResponseException {
        var path = "/user";
        LoginResult loginResult = clientCommunicator.makeRequest("POST", path, newUser, LoginResult.class, serverUrl, null);
        return loginResult;
    }

    public LoginResult logIn(LoginRequest loginRequest) throws ResponseException {
        var path = "/session";
        LoginResult loginResult = clientCommunicator.makeRequest("POST", path, loginRequest, LoginResult.class, serverUrl, null);
        return loginResult;
    }

    public CreateGameResult createGame(CreateGameRequest createGameRequest, String authToken) throws ResponseException {
        var path = "/game";
        CreateGameResult createGameResult = clientCommunicator.makeRequest("POST", path, createGameRequest, CreateGameResult.class, serverUrl, authToken);
        return createGameResult;
    }

    public LogoutResult logOut(String authToken) throws ResponseException {
        var path = "/session";
        return clientCommunicator.makeRequest("DELETE", path, new LogoutRequest(authToken), LogoutResult.class, serverUrl, authToken);
    }

    public ListGamesResult listGames(String authToken) throws ResponseException {
        var path = "/game";
        ListGamesRequest listGamesRequest = new ListGamesRequest(authToken);
        return clientCommunicator.makeRequest("GET", path, listGamesRequest, ListGamesResult.class, serverUrl, authToken);
    }

    public JoinGameResult joinGame(JoinGameRequest joinGameRequest, String authToken) throws ResponseException {
        var path = "/game";
        return clientCommunicator.makeRequest("PUT", path, joinGameRequest, JoinGameResult.class, serverUrl, authToken);
    }

    public void joinGameWebsocket(String authToken, int gameID){
        var ws = new WebsocketCommunicator(observer);

        ws.send(new ConnectCommand(authToken, gameID));
    }

    public void leaveGameWebsocket(String authToken, int gameID){
        var ws = new WebsocketCommunicator(observer);

        ws.send(new LeaveCommand(authToken, gameID));
    }

    public void makeMoveWebsocket(String authToken, int gameID, ChessMove move){
        var ws = new WebsocketCommunicator(observer);

        ws.send(new MakeMoveCommand(authToken, gameID, move));
    }

    public ChessBoard getGameBoard(GetBoardRequest getBoardRequest) throws ResponseException {
        var path = "/game/board";
        return clientCommunicator.makeRequest("PUT", path, getBoardRequest, ChessBoard.class, serverUrl, null);
    }

}