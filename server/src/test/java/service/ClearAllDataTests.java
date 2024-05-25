package service;

import dataaccess.DataAccessException;
import dataaccess.MemoryAuthDAO;
import dataaccess.MemoryGameDAO;
import dataaccess.MemoryUserDAO;
import model.UserData;
import model.GameData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class ClearAllDataTests {

    @BeforeEach
    public void setUp(){
        GameService gameService = null;
        UserService userService = null;
        ClearService clearService = null;
        MemoryAuthDAO authDAO = null;
        MemoryUserDAO userDAO = null;
        MemoryGameDAO gameDAO = null;
        Object expected = null;
    }

    @Test
    public void posTestClear() throws DataAccessException {
        ClearService clearService = new ClearService();
        GameService gameService = new GameService();
        UserService userService = new UserService();
        MemoryAuthDAO authDAO = new MemoryAuthDAO();
        MemoryUserDAO userDAO = new MemoryUserDAO();
        MemoryGameDAO gameDAO = new MemoryGameDAO();

        ClearResult expected = new ClearResult(null);

        UserData userData = new UserData("username", "password", "email");
        userService.register(gameDAO, userDAO, authDAO, userData);
        ClearResult actual = clearService.clearAllData(gameDAO, userDAO, authDAO);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void posRegister() throws DataAccessException {
        ClearService clearService = new ClearService();
        GameService gameService = new GameService();
        UserService userService = new UserService();
        MemoryAuthDAO authDAO = new MemoryAuthDAO();
        MemoryUserDAO userDAO = new MemoryUserDAO();
        MemoryGameDAO gameDAO = new MemoryGameDAO();

        LoginResult expected = new LoginResult(null, "username", "fac6d656-833c-4e48-870c-f3528160c418");

        UserData userData = new UserData("username", "password", "email");
        LoginResult actual = userService.register(gameDAO, userDAO, authDAO, userData);

        Assertions.assertEquals(expected.username(), actual.username());
    }

    @Test
    public void negRegister() throws DataAccessException {
        ClearService clearService = new ClearService();
        GameService gameService = new GameService();
        UserService userService = new UserService();
        MemoryAuthDAO authDAO = new MemoryAuthDAO();
        MemoryUserDAO userDAO = new MemoryUserDAO();
        MemoryGameDAO gameDAO = new MemoryGameDAO();

        LoginResult expected = new LoginResult("Error: already taken", null, null);


        UserData userData = new UserData("username", "password", "email");
        userService.register(gameDAO, userDAO, authDAO, userData);
        LoginResult actual = userService.register(gameDAO, userDAO, authDAO, userData);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void posLogin() throws DataAccessException {
        ClearService clearService = new ClearService();
        GameService gameService = new GameService();
        UserService userService = new UserService();
        MemoryAuthDAO authDAO = new MemoryAuthDAO();
        MemoryUserDAO userDAO = new MemoryUserDAO();
        MemoryGameDAO gameDAO = new MemoryGameDAO();

        LoginResult expected = new LoginResult(null, "username", "fac6d656-833c-4e48-870c-f3528160c418");

        UserData userData = new UserData("username", "password", "email");
        userService.register(gameDAO, userDAO, authDAO, userData);
        LoginRequest loginRequest = new LoginRequest("username", "password");
        LoginResult actual = userService.login(gameDAO, userDAO, authDAO, loginRequest);

        Assertions.assertEquals(expected.username(), actual.username());
    }

    @Test
    public void negLogin() throws DataAccessException {
        ClearService clearService = new ClearService();
        GameService gameService = new GameService();
        UserService userService = new UserService();
        MemoryAuthDAO authDAO = new MemoryAuthDAO();
        MemoryUserDAO userDAO = new MemoryUserDAO();
        MemoryGameDAO gameDAO = new MemoryGameDAO();

        LoginResult expected = new LoginResult("Error: unauthorized", null, null);

        UserData userData = new UserData("username", "password", "email");
        userService.register(gameDAO, userDAO, authDAO, userData);
        LoginRequest loginRequest = new LoginRequest("username", "passwordafsfasd");
        LoginResult actual = userService.login(gameDAO, userDAO, authDAO, loginRequest);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void posLogout() throws DataAccessException {
        ClearService clearService = new ClearService();
        GameService gameService = new GameService();
        UserService userService = new UserService();
        MemoryAuthDAO authDAO = new MemoryAuthDAO();
        MemoryUserDAO userDAO = new MemoryUserDAO();
        MemoryGameDAO gameDAO = new MemoryGameDAO();

        LogoutResult expected = new LogoutResult(null);

        UserData userData = new UserData("username", "password", "email");
        userService.register(gameDAO, userDAO, authDAO, userData);
        LoginRequest loginRequest = new LoginRequest("username", "password");
        LoginResult loginResult = userService.login(gameDAO, userDAO, authDAO, loginRequest);
        LogoutRequest logoutRequest = new LogoutRequest(loginResult.authToken());
        LogoutResult actual = userService.logout(gameDAO, userDAO, authDAO, logoutRequest.authToken());

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void negLogout() throws DataAccessException {
        ClearService clearService = new ClearService();
        GameService gameService = new GameService();
        UserService userService = new UserService();
        MemoryAuthDAO authDAO = new MemoryAuthDAO();
        MemoryUserDAO userDAO = new MemoryUserDAO();
        MemoryGameDAO gameDAO = new MemoryGameDAO();

        LogoutResult expected = new LogoutResult("Error: unauthorized");

        UserData userData = new UserData("username", "password", "email");
        userService.register(gameDAO, userDAO, authDAO, userData);
        UserData userData1 = new UserData("username1", "password", "email");
        LoginResult loginResult1 = userService.register(gameDAO, userDAO, authDAO, userData);
        LoginRequest loginRequest = new LoginRequest("username", "password");
        LoginResult loginResult = userService.login(gameDAO, userDAO, authDAO, loginRequest);
        LogoutRequest logoutRequest = new LogoutRequest(loginResult1.authToken());
        LogoutResult actual = userService.logout(gameDAO, userDAO, authDAO, logoutRequest.authToken());

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void posListGames() throws DataAccessException {
        ClearService clearService = new ClearService();
        GameService gameService = new GameService();
        UserService userService = new UserService();
        MemoryAuthDAO authDAO = new MemoryAuthDAO();
        MemoryUserDAO userDAO = new MemoryUserDAO();
        MemoryGameDAO gameDAO = new MemoryGameDAO();

        ArrayList<GameData> gamesToList = new ArrayList<>();

        ListGamesResult expected = new ListGamesResult(null, gamesToList);

        UserData userData = new UserData("username", "password", "email");
        userService.register(gameDAO, userDAO, authDAO, userData);
        LoginRequest loginRequest = new LoginRequest("username", "password");
        LoginResult loginResult = userService.login(gameDAO, userDAO, authDAO, loginRequest);

        ListGamesResult actual = gameService.listGames(gameDAO, userDAO, authDAO, loginResult.authToken());

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void negListGames() throws DataAccessException {
        ClearService clearService = new ClearService();
        GameService gameService = new GameService();
        UserService userService = new UserService();
        MemoryAuthDAO authDAO = new MemoryAuthDAO();
        MemoryUserDAO userDAO = new MemoryUserDAO();
        MemoryGameDAO gameDAO = new MemoryGameDAO();

        ArrayList<GameData> gamesToList = new ArrayList<>();

        ListGamesResult expected = new ListGamesResult("Error: unauthorized", null);

        UserData userData = new UserData("username", "password", "email");
        userService.register(gameDAO, userDAO, authDAO, userData);
        LoginRequest loginRequest = new LoginRequest("username", "password");
        LoginResult loginResult = userService.login(gameDAO, userDAO, authDAO, loginRequest);
        UserData userData1 = new UserData("username1", "password", "email");
        LoginResult loginResult1 = userService.register(gameDAO, userDAO, authDAO, userData);

        CreateGameRequest createGameRequest = new CreateGameRequest("gameName");
        gameService.createGame(gameDAO, userDAO, authDAO, loginResult.authToken(), createGameRequest);
        ListGamesResult actual = gameService.listGames(gameDAO, userDAO, authDAO, loginResult1.authToken());

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void posCreateGame() throws DataAccessException {
        ClearService clearService = new ClearService();
        GameService gameService = new GameService();
        UserService userService = new UserService();
        MemoryAuthDAO authDAO = new MemoryAuthDAO();
        MemoryUserDAO userDAO = new MemoryUserDAO();
        MemoryGameDAO gameDAO = new MemoryGameDAO();

        ArrayList<GameData> gamesToList = new ArrayList<>();

        CreateGameResult expected = new CreateGameResult(null, 1);

        UserData userData = new UserData("username", "password", "email");
        userService.register(gameDAO, userDAO, authDAO, userData);
        LoginRequest loginRequest = new LoginRequest("username", "password");
        LoginResult loginResult = userService.login(gameDAO, userDAO, authDAO, loginRequest);

        CreateGameRequest createGameRequest = new CreateGameRequest("gameName");
        CreateGameResult actual = gameService.createGame(gameDAO, userDAO, authDAO, loginResult.authToken(), createGameRequest);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void negCreateGame() throws DataAccessException {
        ClearService clearService = new ClearService();
        GameService gameService = new GameService();
        UserService userService = new UserService();
        MemoryAuthDAO authDAO = new MemoryAuthDAO();
        MemoryUserDAO userDAO = new MemoryUserDAO();
        MemoryGameDAO gameDAO = new MemoryGameDAO();

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
        MemoryAuthDAO authDAO = new MemoryAuthDAO();
        MemoryUserDAO userDAO = new MemoryUserDAO();
        MemoryGameDAO gameDAO = new MemoryGameDAO();

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
        MemoryAuthDAO authDAO = new MemoryAuthDAO();
        MemoryUserDAO userDAO = new MemoryUserDAO();
        MemoryGameDAO gameDAO = new MemoryGameDAO();

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

}
