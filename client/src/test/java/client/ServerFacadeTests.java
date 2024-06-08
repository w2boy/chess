package client;

import model.*;
import ui.*;
import org.junit.jupiter.api.*;
import server.Server;

import static org.junit.jupiter.api.Assertions.assertTrue;


public class ServerFacadeTests {

    private static Server server;
    static ui.ServerFacade facade;

    @BeforeAll
    public static void init()  {
        server = new Server();
        int port = server.run(0);
        System.out.println("Started test HTTP server on " + port);
        String serverUrl = "http://localhost:" + Integer.toString(port);
        facade = new ServerFacade(serverUrl);
    }

    @BeforeEach
    void clearAllData() throws ResponseException {
        facade.clearAllData();
    }

    @AfterAll
    static void stopServer() {
        server.stop();
    }


    @Test
    void posRegister() throws Exception {
        var authData = facade.register(new UserData("player1", "password", "p1@email.com"));
        assertTrue(authData.authToken().length() > 10);
    }

    @Test
    void negRegister() throws Exception {
        try{
            facade.register(new UserData("player1", "password", "p1@email.com"));
            var authData = facade.register(new UserData("player1", "password", "p1@email.com"));
        } catch(Exception e){
            assertTrue(e.getMessage() != null);
        }
    }

    @Test
    void posLogin() throws Exception {
        facade.register(new UserData("player1", "password", "p1@email.com"));
        LoginResult loginResult = facade.logIn(new LoginRequest("player1", "password"));
        assertTrue(loginResult.authToken().length() > 10);
    }

    @Test
    void negLogin() throws Exception {
        try{
            facade.register(new UserData("player1", "password", "p1@email.com"));
            LoginResult loginResult = facade.logIn(new LoginRequest("player1", "passworddd"));
        } catch(Exception e){
            assertTrue(e.getMessage() != null);
        }
    }

    @Test
    void posCreateGame() throws Exception {
        facade.register(new UserData("player1", "password", "p1@email.com"));
        LoginResult loginResult = facade.logIn(new LoginRequest("player1", "password"));
        CreateGameResult createGameResult = facade.createGame(new CreateGameRequest("gamename"), loginResult.authToken());
        assertTrue(createGameResult.message() == null);
    }

    @Test
    void negCreateGame() throws Exception {
        try{
            facade.register(new UserData("player1", "password", "p1@email.com"));
            LoginResult loginResult = facade.logIn(new LoginRequest("player1", "password"));
            CreateGameResult createGameResult = facade.createGame(new CreateGameRequest("gamename"), "fakeToken");
        } catch(Exception e){
            assertTrue(e.getMessage() != null);
        }
    }

    @Test
    void posLogOut() throws Exception {
        facade.register(new UserData("player1", "password", "p1@email.com"));
        LoginResult loginResult = facade.logIn(new LoginRequest("player1", "password"));
        LogoutResult logoutResult =  facade.logOut(loginResult.authToken());
        assertTrue(logoutResult.message() == null);
    }

    @Test
    void negLogout() throws Exception {
        try{
            facade.register(new UserData("player1", "password", "p1@email.com"));
            LoginResult loginResult = facade.logIn(new LoginRequest("player1", "password"));
            LogoutResult logoutResult =  facade.logOut("fakeToken");
        } catch(Exception e){
            assertTrue(e.getMessage() != null);
        }
    }

    @Test
    void posListGames() throws Exception {
        facade.register(new UserData("player1", "password", "p1@email.com"));
        LoginResult loginResult = facade.logIn(new LoginRequest("player1", "password"));
        CreateGameResult createGameResult = facade.createGame(new CreateGameRequest("gamename"), loginResult.authToken());
        ListGamesResult listGamesResult = facade.listGames(loginResult.authToken());
        assertTrue(listGamesResult.games().size() == 1);
    }

    @Test
    void negListGames() throws Exception {
        try{
            facade.register(new UserData("player1", "password", "p1@email.com"));
            LoginResult loginResult = facade.logIn(new LoginRequest("player1", "password"));
            ListGamesResult listGamesResult = facade.listGames("fakeToken");
        } catch(Exception e){
            assertTrue(e.getMessage() != null);
        }
    }

    @Test
    void posJoinGame() throws Exception {
        facade.register(new UserData("player1", "password", "p1@email.com"));
        LoginResult loginResult = facade.logIn(new LoginRequest("player1", "password"));
        CreateGameResult createGameResult = facade.createGame(new CreateGameRequest("gamename"), loginResult.authToken());
        JoinGameResult joinGameResult = facade.joinGame(new JoinGameRequest("WHITE", 1), loginResult.authToken());
        assertTrue(joinGameResult.message() == null);
    }

    @Test
    void negJoinGame() throws Exception {
        try{
            facade.register(new UserData("player1", "password", "p1@email.com"));
            LoginResult loginResult = facade.logIn(new LoginRequest("player1", "password"));
            CreateGameResult createGameResult = facade.createGame(new CreateGameRequest("gamename"), loginResult.authToken());
            JoinGameResult joinGameResult = facade.joinGame(new JoinGameRequest("WHITE", 3), loginResult.authToken());
        } catch(Exception e){
            assertTrue(e.getMessage() != null);
        }
    }

}
