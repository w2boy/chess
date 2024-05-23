package dataaccess;

import model.GameData;

import java.util.ArrayList;

public class MemoryGameDAO {
    private ArrayList<GameData> games = new ArrayList<>();

    public void deleteAllGames() throws DataAccessException{
        games.clear();
    }
}
