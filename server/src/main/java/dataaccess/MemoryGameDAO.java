package dataaccess;

import chess.ChessGame;
import model.GameData;
import service.*;

import java.util.ArrayList;
import java.util.List;

public class MemoryGameDAO {
    private ArrayList<GameData> games = new ArrayList<>();

    public void deleteAllGames() throws DataAccessException{
        games.clear();
    }

    public ListGamesResult getListOfGames() throws DataAccessException{
        ArrayList<GameData> gamesToList = new ArrayList<>();
        for (GameData gameData : games){
            GameData listReadyGameData = new GameData(gameData.gameID(), gameData.whiteUsername(), gameData.blackUsername(), gameData.gameName(), null);
            gamesToList.add(listReadyGameData);
        }
        ListGamesResult listGamesResult = new ListGamesResult(null, gamesToList);
        return listGamesResult;
    }

    public CreateGameResult createGame(CreateGameRequest createGameRequest) throws DataAccessException {
        int gameID = games.size() + 1;
        ChessGame chessGame = new ChessGame();
        GameData gameData = new GameData (gameID, null, null, createGameRequest.gameName(), chessGame);
        games.add(gameData);
        return new CreateGameResult(null, gameData.gameID());
    }

    public JoinGameResult joinGame(JoinGameRequest joinGameRequest, String username) throws DataAccessException {
        if (joinGameRequest.playerColor() == null){
            return new JoinGameResult("Error: bad request");
        }
        int desiredGameID = joinGameRequest.gameID();
        for (GameData gameData : games){
            if (gameData.gameID() == desiredGameID){
                if (joinGameRequest.playerColor().equals("WHITE") && gameData.whiteUsername() == null){
                    GameData desiredGame = new GameData(gameData.gameID(), username, gameData.blackUsername(), gameData.gameName(), gameData.game());
                    games.add(desiredGame);
                }
                else if (joinGameRequest.playerColor().equals("BLACK") && gameData.blackUsername() == null){
                    GameData desiredGame = new GameData(gameData.gameID(), gameData.whiteUsername(), username, gameData.gameName(), gameData.game());
                    games.add(desiredGame);
                }
                else {
                    return new JoinGameResult("Error: already taken");
                }
                games.remove(gameData);
                return new JoinGameResult(null);
            }
        }

        return new JoinGameResult("Error: bad request");
    }
}
