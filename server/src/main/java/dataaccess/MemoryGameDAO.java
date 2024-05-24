package dataaccess;

import model.GameData;
import service.ListGamesResult;

import java.util.ArrayList;
import java.util.List;

public class MemoryGameDAO {
    private ArrayList<GameData> games = new ArrayList<>();

    public void deleteAllGames() throws DataAccessException{
        games.clear();
    }

    public ListGamesResult getListOfGames() throws DataAccessException{
        ListGamesResult listGamesResult = new ListGamesResult(null, games);
        return listGamesResult;
    }
}
