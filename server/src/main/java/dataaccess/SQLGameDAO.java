package dataaccess;

import chess.ChessGame;
import model.GameData;
import service.*;

import java.util.ArrayList;

public class SQLGameDAO {
    private ArrayList<GameData> games = new ArrayList<>();

    public void deleteAllGames() throws DataAccessException{

    }

    public ListGamesResult getListOfGames() throws DataAccessException{
        return null;
    }

    public CreateGameResult createGame(CreateGameRequest createGameRequest) throws DataAccessException {
        return null;
    }

    public JoinGameResult joinGame(JoinGameRequest joinGameRequest, String username) throws DataAccessException {
        return null;
    }
}
