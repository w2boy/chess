package dataaccess;

import model.AuthData;
import model.GameData;
import service.CreateGameResult;
import service.JoinGameResult;
import service.ListGamesResult;

public interface GameDAO {

    void deleteAllGames() throws DataAccessException;
    ListGamesResult getListOfGames() throws DataAccessException;
    CreateGameResult createGame() throws DataAccessException;
    JoinGameResult joinGame() throws DataAccessException;
}
