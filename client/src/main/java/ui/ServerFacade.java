package ui;

import com.google.gson.Gson;
import model.UserData;
import service.CreateGameRequest;
import service.CreateGameResult;
import service.LoginRequest;
import service.LoginResult;

import java.io.*;
import java.net.*;

public class ServerFacade {

    private final String serverUrl;
    private ClientCommunicator clientCommunicator = new ClientCommunicator();

    public ServerFacade(String url) {
        serverUrl = url;
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
//
//    public void deletePet(int id) throws ResponseException {
//        var path = String.format("/pet/%s", id);
//        this.makeRequest("DELETE", path, null, null);
//    }
//
//    public void deleteAllPets() throws ResponseException {
//        var path = "/pet";
//        this.makeRequest("DELETE", path, null, null);
//    }
//
//    public Pet[] listPets() throws ResponseException {
//        var path = "/pet";
//        record listPetResponse(Pet[] pet) {
//        }
//        var response = this.makeRequest("GET", path, null, listPetResponse.class);
//        return response.pet();
//    }


}