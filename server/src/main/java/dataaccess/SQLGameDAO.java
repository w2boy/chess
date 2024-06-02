package dataaccess;

import chess.ChessGame;
import com.google.gson.Gson;
import model.GameData;
import service.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class SQLGameDAO {
    private ArrayList<GameData> games = new ArrayList<>();

    public void deleteAllGames() throws DataAccessException{
        Connection connection = null;

        try (var conn = DatabaseManager.getConnection()) {

            connection = conn;

            String sql = "delete from game_table";

            try(PreparedStatement stmt = connection.prepareStatement(sql)) {

                int count = stmt.executeUpdate();

                // System.out.printf("Deleted %d authTokens\n", count);
            }

        } catch (SQLException e) {
            throw new DataAccessException("Error while deleting all game data");
        }
    }

    public ListGamesResult getListOfGames() throws DataAccessException{
        return null;
    }

    public CreateGameResult createGame(CreateGameRequest createGameRequest) throws DataAccessException {
        int gameID = games.size() + 1;
        ChessGame chessGame = new ChessGame();
        GameData gameData = new GameData (gameID, null, null, createGameRequest.gameName(), chessGame);
        String chessGameJson = new Gson().toJson(chessGame, ChessGame.class);

        try (var conn = DatabaseManager.getConnection()) {
            var statement = "insert into game_table (game_id, white_username, black_username, game_name, game) values (?, ?, ?, ?, ?)";
            try (var ps = conn.prepareStatement(statement)) {
                ps.setInt(1, gameData.gameID());
                ps.setString(2, null);
                ps.setString(3, null);
                ps.setString(4, gameData.gameName());
                ps.setString(5, chessGameJson);
                if(ps.executeUpdate() == 1) {
                    // Do Nothing
                } else {
                    System.out.println("Failed to insert game");
                }
            }
        } catch (Exception e) {
            throw new DataAccessException (String.format("Unable to read data: %s", e.getMessage()));
        }

        return new CreateGameResult(null, gameData.gameID());
    }

    public JoinGameResult joinGame(JoinGameRequest joinGameRequest, String username) throws DataAccessException {
        return null;
    }
}
