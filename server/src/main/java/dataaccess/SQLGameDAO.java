package dataaccess;

import chess.ChessBoard;
import chess.ChessGame;
import com.google.gson.Gson;
import model.GameData;
import model.UserData;
import org.mindrot.jbcrypt.BCrypt;
import service.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

public class SQLGameDAO {
    Integer numGames = 0;

    public void deleteAllGames() throws DataAccessException{
        Connection connection = null;

        try (var conn = DatabaseManager.getConnection()) {

            connection = conn;

            String sql = "delete from game_table";

            try(PreparedStatement stmt = connection.prepareStatement(sql)) {

                int count = stmt.executeUpdate();

                sql = "ALTER TABLE game_table AUTO_INCREMENT = 1";
                try(PreparedStatement resetAutoIncrementStatement = connection.prepareStatement(sql)) {
                    resetAutoIncrementStatement.executeUpdate();
                }

            }

        } catch (SQLException e) {
            throw new DataAccessException("Error while deleting all game data");
        }
        numGames = 0;
    }

    public ListGamesResult getListOfGames() throws DataAccessException{
        ArrayList<GameData> gamesToList = new ArrayList<>();

        try (var conn = DatabaseManager.getConnection()) {
            var statement = "SELECT game_id, white_username, black_username, game_name FROM game_table";
            try (var ps = conn.prepareStatement(statement)) {
                try (var rs = ps.executeQuery()) {
                    while (rs.next()) {

                        gamesToList.add(new GameData(rs.getInt("game_id"), rs.getString("white_username"), rs.getString("black_username"), rs.getString("game_name"), null));
                    }
                }
            }
        } catch (Exception e) {
            throw new DataAccessException (String.format("Unable to read data: %s", e.getMessage()));
        }
        ListGamesResult listGamesResult = new ListGamesResult(null, gamesToList);
        return listGamesResult;
    }

    public CreateGameResult createGame(CreateGameRequest createGameRequest) throws DataAccessException {
        if (createGameRequest.gameName().length() > 255){
            return new CreateGameResult("Game Name Too Long", -1);
        }
        int gameID = 0;
        ChessGame chessGame = new ChessGame();
        GameData gameData = new GameData (0, null, null, createGameRequest.gameName(), chessGame);
        String chessGameJson = new Gson().toJson(chessGame, ChessGame.class);

        try (var conn = DatabaseManager.getConnection()) {
            var statement = "insert into game_table ( white_username, black_username, game_name, game) values (?, ?, ?, ?)";
            try (var ps = conn.prepareStatement(statement)) {
//                ps.setInt(1, gameData.gameID());
                ps.setString(1, null);
                ps.setString(2, null);
                ps.setString(3, gameData.gameName());
                ps.setString(4, chessGameJson);
                if(ps.executeUpdate() == 1) {
                    // Do Nothing
                } else {
                    System.out.println("Failed to insert game");
                    return null;
                }
            }
        } catch (Exception e) {
            throw new DataAccessException (String.format("Unable to read data: %s", e.getMessage()));
        }

        try (var conn = DatabaseManager.getConnection()) {
            var statement = "SELECT game_id FROM game_table WHERE game_name=?";
            try (var ps = conn.prepareStatement(statement)) {
                ps.setString(1, createGameRequest.gameName());
                try (var rs = ps.executeQuery()) {
                    while (rs.next()) {

                        gameID = rs.getInt("game_id");
                    }
                }
            }
        } catch (Exception e) {
            throw new DataAccessException (String.format("Unable to read data: %s", e.getMessage()));
        }

        return new CreateGameResult(null, gameID);
    }

    public JoinGameResult joinGame(JoinGameRequest joinGameRequest, String username) throws DataAccessException {
        String statement = "SELECT game_id, white_username, black_username, game_name, game FROM game_table WHERE game_id=?";
        int desiredGameID = joinGameRequest.gameID();

        if (joinGameRequest.playerColor() == null){
            return new JoinGameResult("Error: bad request");
        }

        if (!(joinGameRequest.playerColor().equals("WHITE") || joinGameRequest.playerColor().equals("BLACK"))) {
            return new JoinGameResult("Error: bad request");
        }

        try (var conn = DatabaseManager.getConnection()) {
            try (var ps = conn.prepareStatement(statement)) {
                ps.setInt(1, desiredGameID);
                try (var rs = ps.executeQuery()) {
                    if (rs.next()) {
                        GameData gameData = readGame(rs);
                        if (!(joinGameRequest.playerColor().equals("WHITE") && gameData.whiteUsername() == null) &&
                                !(joinGameRequest.playerColor().equals("BLACK") && gameData.blackUsername() == null)){
                            return new JoinGameResult("Error: already taken");
                        }
                    }
                }
            }
            if (joinGameRequest.playerColor().equals("WHITE")){
                statement = "update game_table " +
                        "set white_username=?" +
                        "where game_id=?";
            }
            else if (joinGameRequest.playerColor().equals("BLACK")){
                statement = "UPDATE game_table " +
                        "SET black_username=?" +
                        "WHERE game_id=?";
            }
            try (var ps = conn.prepareStatement(statement)) {
                ps.setString(1, username);
                ps.setInt(2, desiredGameID);
                if(ps.executeUpdate() == 1) {
                    return new JoinGameResult(null);
                }
            }
        } catch (Exception e) {
            throw new DataAccessException (String.format("Unable to read data: %s", e.getMessage()));
        }

        return new JoinGameResult("Error: bad request");
    }

    public ChessBoard getGameBoard(GetBoardRequest getBoardRequest) throws DataAccessException {
        int gameID = getBoardRequest.gameID();
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "SELECT game FROM game_table WHERE game_id=?";
            try (var ps = conn.prepareStatement(statement)) {
                ps.setString(1, Integer.toString(gameID));
                try (var rs = ps.executeQuery()) {
                    while (rs.next()) {
                        String chessGameJson = rs.getString("game");
                        ChessGame game = new Gson().fromJson(chessGameJson, ChessGame.class);
                        return game.getBoard();
                    }
                }
            }
        } catch (Exception e) {
            throw new DataAccessException (String.format("Unable to read data: %s", e.getMessage()));
        }
        return null;
    }

    private GameData readGame(ResultSet rs) throws SQLException {
        String chessGameJson = rs.getString("game");
        ChessGame chessGame = new Gson().fromJson(chessGameJson, ChessGame.class);
        return new GameData(rs.getInt("game_id"), rs.getString("white_username"), rs.getString("black_username"), rs.getString("game_name"), chessGame);
    }

    public GameData getGame(int gameID) throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "SELECT game FROM game_table WHERE game_id=?";
            try (var ps = conn.prepareStatement(statement)) {
                String gID = Integer.toString(gameID);
                ps.setString(1, gID);
                try (var rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return new GameData(rs.getInt("game_id"), rs.getString("white_username"), rs.getString("black_username"), rs.getString("game_name"), null);
                    }
                }
            }
        } catch (Exception e) {
            throw new DataAccessException (String.format("Unable to read data: %s", e.getMessage()));
        }
        return null;
    }
}
