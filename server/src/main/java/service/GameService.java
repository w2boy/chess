package service;

import dataaccess.*;
import model.AuthData;
import model.GameData;

public class GameService {
    public ListGamesResult listGames(MemoryGameDAO gameDAO, MemoryUserDAO userDAO, MemoryAuthDAO authDAO, String authToken) throws DataAccessException {
        AuthData authData = authDAO.getAuth(authToken);
        if (authData != null){
            return gameDAO.getListOfGames();
        }
        return null;
    }
    public CreateGameResult createGame(MemoryGameDAO gameDAO, MemoryUserDAO userDAO, MemoryAuthDAO authDAO, String authToken, CreateGameRequest createGameRequest) throws DataAccessException {
        AuthData authData = authDAO.getAuth(authToken);
        if (authData != null){
            return gameDAO.createGame(createGameRequest);
        }
        return null;
    }
    public JoinGameResult joinGame(MemoryGameDAO gameDAO, MemoryUserDAO userDAO, MemoryAuthDAO authDAO, GameData gameData, String authToken) throws DataAccessException {return null;}
}
