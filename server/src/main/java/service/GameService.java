package service;

import dataaccess.*;
import model.AuthData;
import model.GameData;

import java.util.List;

public class GameService {
    public ListGamesResult listGames(MemoryGameDAO gameDAO, MemoryUserDAO userDAO, MemoryAuthDAO authDAO, String authToken) throws DataAccessException {
        AuthData authData = authDAO.getAuth(authToken);
        if (authData != null){
            return gameDAO.getListOfGames();
        }
        return new ListGamesResult("Error: unauthorized", null);
    }
    public CreateGameResult createGame(MemoryGameDAO gameDAO, MemoryUserDAO userDAO, MemoryAuthDAO authDAO, String authToken, CreateGameRequest createGameRequest) throws DataAccessException {
        AuthData authData = authDAO.getAuth(authToken);
        if (authData != null){
            return gameDAO.createGame(createGameRequest);
        }
        return new CreateGameResult("Error: unauthorized", null);
    }
    public JoinGameResult joinGame(MemoryGameDAO gameDAO, MemoryUserDAO userDAO, MemoryAuthDAO authDAO, String authToken, JoinGameRequest joinGameRequest) throws DataAccessException {
        AuthData authData = authDAO.getAuth(authToken);
        if (authData != null){
            return gameDAO.joinGame(joinGameRequest, authData.username());
        }
        return new JoinGameResult("Error: unauthorized");
    }
}
