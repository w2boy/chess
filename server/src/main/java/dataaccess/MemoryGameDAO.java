package dataaccess;

import chess.ChessGame;
import model.GameData;
import service.CreateGameRequest;
import service.CreateGameResult;
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

    public CreateGameResult createGame(CreateGameRequest createGameRequest) throws DataAccessException {
        int gameID = games.size();
        ChessGame chessGame = new ChessGame();
        GameData gameData = new GameData (gameID, null, null, createGameRequest.gameName(), chessGame);
        return new CreateGameResult(null, gameData.gameID());
    }
}
