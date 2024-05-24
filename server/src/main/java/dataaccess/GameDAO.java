package dataaccess;

import model.GameData;

public interface GameDAO {

    void deleteAllGames() throws DataAccessException;
    GameData[] listGames() throws DataAccessException;
}
