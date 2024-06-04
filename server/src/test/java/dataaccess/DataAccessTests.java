package dataaccess;

import chess.ChessGame;
import com.google.gson.Gson;
import dataaccess.*;
import model.AuthData;
import model.UserData;
import model.GameData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mindrot.jbcrypt.BCrypt;
import service.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

public class DataAccessTests {

    @BeforeEach
    public void setUp() throws DataAccessException {
        ClearService clearService = new ClearService();
        GameService gameService = new GameService();
        UserService userService = new UserService();
        SQLAuthDAO authDAO = new SQLAuthDAO();
        SQLUserDAO userDAO = new SQLUserDAO();
        SQLGameDAO gameDAO = new SQLGameDAO();
        Object expected = null;
        clearService.clearAllData(gameDAO, userDAO, authDAO );
    }

    @Test
    public void posDeleteAllAuthData() throws DataAccessException {
        ClearService clearService = new ClearService();
        GameService gameService = new GameService();
        UserService userService = new UserService();
        SQLAuthDAO authDAO = new SQLAuthDAO();
        SQLUserDAO userDAO = new SQLUserDAO();
        SQLGameDAO gameDAO = new SQLGameDAO();

        var expected = new AuthData (null, null);
        var actual = new AuthData(null, null);
        //------------------------------------------------
        String authToken = "auth123";
        AuthData authData = new AuthData(authToken, "username");
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "insert into auth_table (auth_token, username) values (?, ?)";
            try (var ps = conn.prepareStatement(statement)) {
                ps.setString(1, authData.authToken());
                ps.setString(2, authData.username());
                if(ps.executeUpdate() == 1) {
                    // Do Nothing
                } else {
                    System.out.println("Failed to insert authData");
                }
            }
        } catch (Exception e) {
            throw new DataAccessException (String.format("Unable to read data: %s", e.getMessage()));
        }

        authDAO.deleteAllAuthData();

        try (var conn = DatabaseManager.getConnection()) {
            var statement = "SELECT auth_token, username FROM auth_table WHERE auth_token=?";
            try (var ps = conn.prepareStatement(statement)) {
                ps.setString(1, "auth123");
                try (var rs = ps.executeQuery()) {
                    if (rs.next()) {
                        actual = readAuth(rs);
                    }
                }
            }
        } catch (Exception e) {
            throw new DataAccessException (String.format("Unable to read data: %s", e.getMessage()));
        }

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void posDeleteAllGames() throws DataAccessException {
        ClearService clearService = new ClearService();
        GameService gameService = new GameService();
        UserService userService = new UserService();
        SQLAuthDAO authDAO = new SQLAuthDAO();
        SQLUserDAO userDAO = new SQLUserDAO();
        SQLGameDAO gameDAO = new SQLGameDAO();

        var expected = new GameData (0, null, null, null, null);
        var actual = new GameData (0, null, null, null, null);
        //------------------------------------------------
        GameData gameData = new GameData (0, null, null, "gamename", new ChessGame());
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "insert into game_table (game_id, white_username, black_username, game_name, game) values (?, ?, ?, ?, ?)";
            try (var ps = conn.prepareStatement(statement)) {
                ps.setInt(1, gameData.gameID());
                ps.setString(2, null);
                ps.setString(3, null);
                ps.setString(4, gameData.gameName());
                ps.setString(5, "");
                if(ps.executeUpdate() == 1) {
                    // Do Nothing
                } else {
                    System.out.println("Failed to insert game");
                }
            }
        } catch (Exception e) {
            throw new DataAccessException (String.format("Unable to read data: %s", e.getMessage()));
        }

        gameDAO.deleteAllGames();

        try (var conn = DatabaseManager.getConnection()) {
            var statement = "SELECT game_id, white_username, black_username, game_name FROM game_table";
            try (var ps = conn.prepareStatement(statement)) {
                try (var rs = ps.executeQuery()) {
                    if (rs.next()) {
                        actual = readGame(rs);
                    }
                }
            }
        } catch (Exception e) {
            throw new DataAccessException (String.format("Unable to read data: %s", e.getMessage()));
        }

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void posDeleteAllusers() throws DataAccessException {
        ClearService clearService = new ClearService();
        GameService gameService = new GameService();
        UserService userService = new UserService();
        SQLAuthDAO authDAO = new SQLAuthDAO();
        SQLUserDAO userDAO = new SQLUserDAO();
        SQLGameDAO gameDAO = new SQLGameDAO();

        var expected = new UserData (null, null, null);
        var actual = new UserData (null, null, null);
        //------------------------------------------------
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "insert into user_table (username, password, email) values (?, ?, ?)";
            try (var ps = conn.prepareStatement(statement)) {
                ps.setString(1, "username");
                ps.setString(2, "hashedPassword");
                ps.setString(3, "w@gmail.com");
                if(ps.executeUpdate() == 1) {
                    // Do Nothing
                } else {
                    System.out.println("Failed to insert user");
                }
            }
        } catch (Exception e) {
            throw new DataAccessException (String.format("Unable to read data: %s", e.getMessage()));
        }

        userDAO.deleteAllUsers();

        try (var conn = DatabaseManager.getConnection()) {
            var statement = "SELECT username, password, email FROM user_table WHERE username=?";
            try (var ps = conn.prepareStatement(statement)) {
                ps.setString(1, "username");
                try (var rs = ps.executeQuery()) {
                    if (rs.next()) {
                        UserData userData = readUser(rs);
                    }
                }
            }
        } catch (Exception e) {
            throw new DataAccessException (String.format("Unable to read data: %s", e.getMessage()));
        }

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void posCreateAuth() throws DataAccessException {
        ClearService clearService = new ClearService();
        GameService gameService = new GameService();
        UserService userService = new UserService();
        SQLAuthDAO authDAO = new SQLAuthDAO();
        SQLUserDAO userDAO = new SQLUserDAO();
        SQLGameDAO gameDAO = new SQLGameDAO();

        var expected = 1;
        var actual = 0;
        //------------------------------------------------
        AuthData authData = new AuthData("auth123", "username");

        authDAO.createAuth("username");

        try (var conn = DatabaseManager.getConnection()) {
            var statement = "SELECT auth_token, username FROM auth_table WHERE username=?";
            try (var ps = conn.prepareStatement(statement)) {
                ps.setString(1, "username");
                try (var rs = ps.executeQuery()) {
                    if (rs.next()) {
                        actual += 1;
                    }
                }
            }
        } catch (Exception e) {
            throw new DataAccessException (String.format("Unable to read data: %s", e.getMessage()));
        }

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void negCreateAuth() throws DataAccessException {
        ClearService clearService = new ClearService();
        GameService gameService = new GameService();
        UserService userService = new UserService();
        SQLAuthDAO authDAO = new SQLAuthDAO();
        SQLUserDAO userDAO = new SQLUserDAO();
        SQLGameDAO gameDAO = new SQLGameDAO();

        var expected = 0;
        var actual = 0;
        //------------------------------------------------
        AuthData authData = new AuthData("auth123", "username");

        authDAO.createAuth("username");

        try (var conn = DatabaseManager.getConnection()) {
            var statement = "SELECT auth_token, username FROM auth_table WHERE username=?";
            try (var ps = conn.prepareStatement(statement)) {
                ps.setString(1, "username1");
                try (var rs = ps.executeQuery()) {
                    if (rs.next()) {
                        actual += 1;
                    }
                }
            }
        } catch (Exception e) {
            throw new DataAccessException (String.format("Unable to read data: %s", e.getMessage()));
        }

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void posGetAuth() throws DataAccessException {
        ClearService clearService = new ClearService();
        GameService gameService = new GameService();
        UserService userService = new UserService();
        SQLAuthDAO authDAO = new SQLAuthDAO();
        SQLUserDAO userDAO = new SQLUserDAO();
        SQLGameDAO gameDAO = new SQLGameDAO();

        var expected = new GameData (0, null, null, null, null);
        var actual = new GameData (0, null, null, null, null);
        //------------------------------------------------
        GameData gameData = new GameData (0, null, null, "gamename", new ChessGame());
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "insert into game_table (game_id, white_username, black_username, game_name, game) values (?, ?, ?, ?, ?)";
            try (var ps = conn.prepareStatement(statement)) {
                ps.setInt(1, gameData.gameID());
                ps.setString(2, null);
                ps.setString(3, null);
                ps.setString(4, gameData.gameName());
                ps.setString(5, "");
                if(ps.executeUpdate() == 1) {
                    // Do Nothing
                } else {
                    System.out.println("Failed to insert game");
                }
            }
        } catch (Exception e) {
            throw new DataAccessException (String.format("Unable to read data: %s", e.getMessage()));
        }

        gameDAO.deleteAllGames();

        try (var conn = DatabaseManager.getConnection()) {
            var statement = "SELECT game_id, white_username, black_username, game_name FROM game_table";
            try (var ps = conn.prepareStatement(statement)) {
                try (var rs = ps.executeQuery()) {
                    if (rs.next()) {
                        actual = readGame(rs);
                    }
                }
            }
        } catch (Exception e) {
            throw new DataAccessException (String.format("Unable to read data: %s", e.getMessage()));
        }

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void negGetAuth() throws DataAccessException {
        ClearService clearService = new ClearService();
        GameService gameService = new GameService();
        UserService userService = new UserService();
        SQLAuthDAO authDAO = new SQLAuthDAO();
        SQLUserDAO userDAO = new SQLUserDAO();
        SQLGameDAO gameDAO = new SQLGameDAO();

        AuthData expected = null;
        authDAO.createAuth("username");
        //-----------------------------------------------
        AuthData actual = authDAO.getAuth("authToken??");

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void posDeleteAuth() throws DataAccessException {
        ClearService clearService = new ClearService();
        GameService gameService = new GameService();
        UserService userService = new UserService();
        SQLAuthDAO authDAO = new SQLAuthDAO();
        SQLUserDAO userDAO = new SQLUserDAO();
        SQLGameDAO gameDAO = new SQLGameDAO();

        AuthData expected = null;
        AuthData returned = authDAO.createAuth("username");
        //-----------------------------------------------
        authDAO.deleteAuth(returned.authToken());
        AuthData actual = authDAO.getAuth(returned.authToken());

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void negDeleteAuth() throws DataAccessException {
        SQLAuthDAO authDAO = new SQLAuthDAO();

        AuthData expected = authDAO.createAuth("username");
        //-----------------------------------------------
        authDAO.deleteAuth("authToken??");
        AuthData actual = authDAO.getAuth(expected.authToken());

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void posCreateGame() throws DataAccessException {
        SQLAuthDAO authDAO = new SQLAuthDAO();
        SQLUserDAO userDAO = new SQLUserDAO();
        SQLGameDAO gameDAO = new SQLGameDAO();

        var expected = new GameData (0, null, null, null, null);
        var actual = new GameData (0, null, null, null, null);
        //------------------------------------------------
        GameData gameData = new GameData (0, null, null, "gamename", new ChessGame());
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "insert into game_table (game_id, white_username, black_username, game_name, game) values (?, ?, ?, ?, ?)";
            try (var ps = conn.prepareStatement(statement)) {
                ps.setInt(1, gameData.gameID());
                ps.setString(2, null);
                ps.setString(3, null);
                ps.setString(4, gameData.gameName());
                ps.setString(5, "");
                if(ps.executeUpdate() == 1) {
                    // Do Nothing
                } else {
                    System.out.println("Failed to insert game");
                }
            }
        } catch (Exception e) {
            throw new DataAccessException (String.format("Unable to read data: %s", e.getMessage()));
        }

        gameDAO.deleteAllGames();

        try (var conn = DatabaseManager.getConnection()) {
            var statement = "SELECT game_id, white_username, black_username, game_name FROM game_table";
            try (var ps = conn.prepareStatement(statement)) {
                try (var rs = ps.executeQuery()) {
                    if (rs.next()) {
                        actual = readGame(rs);
                    }
                }
            }
        } catch (Exception e) {
            throw new DataAccessException (String.format("Unable to read data: %s", e.getMessage()));
        }

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void negCreateGame() throws DataAccessException {
        ClearService clearService = new ClearService();
        GameService gameService = new GameService();
        UserService userService = new UserService();
        SQLAuthDAO authDAO = new SQLAuthDAO();
        SQLUserDAO userDAO = new SQLUserDAO();
        SQLGameDAO gameDAO = new SQLGameDAO();

        ArrayList<GameData> gamesToList = new ArrayList<>();

        CreateGameResult expected = new CreateGameResult("Error: unauthorized", null);

        UserData userData = new UserData("username", "password", "email");
        userService.register(gameDAO, userDAO, authDAO, userData);
        LoginRequest loginRequest = new LoginRequest("username", "password");
        LoginResult loginResult = userService.login(gameDAO, userDAO, authDAO, loginRequest);
        UserData userData1 = new UserData("username1", "password", "email");
        LoginResult loginResult1 = userService.register(gameDAO, userDAO, authDAO, userData);

        CreateGameRequest createGameRequest = new CreateGameRequest("gameName");
        CreateGameResult actual = gameService.createGame(gameDAO, userDAO, authDAO, loginResult1.authToken(), createGameRequest);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void posJoinGame() throws DataAccessException {
        ClearService clearService = new ClearService();
        GameService gameService = new GameService();
        UserService userService = new UserService();
        SQLAuthDAO authDAO = new SQLAuthDAO();
        SQLUserDAO userDAO = new SQLUserDAO();
        SQLGameDAO gameDAO = new SQLGameDAO();

        ArrayList<GameData> gamesToList = new ArrayList<>();

        JoinGameResult expected = new JoinGameResult(null);

        UserData userData = new UserData("username", "password", "email");
        userService.register(gameDAO, userDAO, authDAO, userData);
        LoginRequest loginRequest = new LoginRequest("username", "password");
        LoginResult loginResult = userService.login(gameDAO, userDAO, authDAO, loginRequest);

        CreateGameRequest createGameRequest = new CreateGameRequest("gameName");
        CreateGameResult createGameResult = gameService.createGame(gameDAO, userDAO, authDAO, loginResult.authToken(), createGameRequest);
        JoinGameRequest joinGameRequest = new JoinGameRequest("WHITE", 1);
        JoinGameResult actual = gameService.joinGame(gameDAO, userDAO, authDAO, loginResult.authToken(), joinGameRequest);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void negJoinGame() throws DataAccessException {
        ClearService clearService = new ClearService();
        GameService gameService = new GameService();
        UserService userService = new UserService();
        SQLAuthDAO authDAO = new SQLAuthDAO();
        SQLUserDAO userDAO = new SQLUserDAO();
        SQLGameDAO gameDAO = new SQLGameDAO();

        ArrayList<GameData> gamesToList = new ArrayList<>();

        JoinGameResult expected = new JoinGameResult("Error: unauthorized");

        UserData userData = new UserData("username", "password", "email");
        userService.register(gameDAO, userDAO, authDAO, userData);
        LoginRequest loginRequest = new LoginRequest("username", "password");
        LoginResult loginResult = userService.login(gameDAO, userDAO, authDAO, loginRequest);
        UserData userData1 = new UserData("username1", "password", "email");
        LoginResult loginResult1 = userService.register(gameDAO, userDAO, authDAO, userData);

        CreateGameRequest createGameRequest = new CreateGameRequest("gameName");
        CreateGameResult createGameResult = gameService.createGame(gameDAO, userDAO, authDAO, loginResult.authToken(), createGameRequest);
        JoinGameRequest joinGameRequest = new JoinGameRequest("WHITE", 1);
        JoinGameResult actual = gameService.joinGame(gameDAO, userDAO, authDAO, loginResult1.authToken(), joinGameRequest);

        Assertions.assertEquals(expected, actual);
    }

    private AuthData readAuth(ResultSet rs) throws SQLException {
        return new AuthData(rs.getString("auth_token"), rs.getString("username"));
    }

    private GameData readGame(ResultSet rs) throws SQLException {
        String chessGameJson = rs.getString("game");
        ChessGame chessGame = new Gson().fromJson(chessGameJson, ChessGame.class);
        return new GameData(rs.getInt("game_id"), rs.getString("white_username"), rs.getString("black_username"), rs.getString("game_name"), chessGame);
    }

    private UserData readUser(ResultSet rs) throws SQLException {
        return new UserData(rs.getString("username"), rs.getString("password"), rs.getString("email"));
    }

}
