package server;
import com.google.gson.Gson;
import dataaccess.*;
import service.*;
import model.AuthData;
import model.UserData;
import model.GameData;

import spark.*;

public class Server {

    private ClearService clearService;
    private GameService gameService;
    private UserService userService;

    private MemoryAuthDAO authDAO = new MemoryAuthDAO();
    private MemoryUserDAO userDAO = new MemoryUserDAO();
    private MemoryGameDAO gameDAO = new MemoryGameDAO();

    private SQLAuthDAO authSQLDAO = new SQLAuthDAO();
    private SQLUserDAO userSQLDAO = new SQLUserDAO();
    private SQLGameDAO gameSQLDAO = new SQLGameDAO();

    public Server(){
        this.clearService = new ClearService();
        this.gameService = new GameService();
        this.userService = new UserService();
    }

    public void createDatabase() throws DataAccessException {
        DatabaseManager.createDatabase();
    }

    public int run(int desiredPort) {

        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        Spark.init();

        // Register your endpoints and handle exceptions here.
        Spark.delete("/db", this::clearAllData);
        Spark.post("/user", this::register);
        Spark.post("/session", this::login);
        Spark.delete("/session", this::logout);
        Spark.get("/game", this::listGames);
        Spark.post("/game", this::createGame);
        Spark.put("/game", this::joinGame);
        //        Spark.exception(ResponseException.class, this::exceptionHandler);

        Spark.awaitInitialization();
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }

    private Object clearAllData(Request req, Response res) throws DataAccessException {
        ClearResult clearResult = clearService.clearAllData(gameSQLDAO, userSQLDAO, authSQLDAO);
        res.status(200);
        return new Gson().toJson(clearResult);
    }

    private Object register(Request req, Response res) throws DataAccessException {
        UserData userData = new Gson().fromJson(req.body(), UserData.class);
        LoginResult loginResult = userService.register(gameSQLDAO, userSQLDAO, authSQLDAO, userData);
        if (loginResult.message() != null){
            if (loginResult.message().equals("Error: bad request")){
                res.status(400);
            }
            else if (loginResult.message().equals("Error: already taken")){
                res.status(403);
            }
        } else {
            res.status(200);
        }
        return new Gson().toJson(loginResult);
    }

    private Object login(Request req, Response res) throws DataAccessException {
        LoginRequest loginRequest = new Gson().fromJson(req.body(), LoginRequest.class);
        LoginResult loginResult  = userService.login(gameSQLDAO, userSQLDAO, authSQLDAO, loginRequest);
        if (loginResult.message() != null){
            res.status(401);
        } else {
            res.status(200);
        }
        return new Gson().toJson(loginResult);
    }

    private Object logout(Request req, Response res) throws DataAccessException {
        String authToken = req.headers("authorization");
        LogoutResult logoutResult = userService.logout(gameSQLDAO, userSQLDAO, authSQLDAO, authToken);
        if (logoutResult.message() != null){
            if (logoutResult.message().equals("Error: unauthorized")){
                res.status(401);
            }
        } else {
            res.status(200);
        }
        return new Gson().toJson(logoutResult);
    }

    private Object listGames(Request req, Response res) throws DataAccessException {
        String authToken = req.headers("authorization");
        ListGamesResult listGamesResult = gameService.listGames(gameSQLDAO, userSQLDAO, authSQLDAO, authToken);
        if (listGamesResult.message() != null){
            if (listGamesResult.message().equals("Error: unauthorized")){
                res.status(401);
            }
        } else {
            res.status(200);
        }
        return new Gson().toJson(listGamesResult);
    }

    private Object createGame(Request req, Response res) throws DataAccessException {
        String authToken = req.headers("authorization");
        CreateGameRequest createGameRequest = new Gson().fromJson(req.body(), CreateGameRequest.class);
        CreateGameResult createGameResult = gameService.createGame(gameSQLDAO, userSQLDAO, authSQLDAO, authToken, createGameRequest);
        if (createGameResult.message() != null){
            if (createGameResult.message().equals("Error: unauthorized")){
                res.status(401);
            }
        } else {
            res.status(200);
        }
        return new Gson().toJson(createGameResult);
    }

    private Object joinGame(Request req, Response res) throws DataAccessException {
        String authToken = req.headers("authorization");
        JoinGameRequest joinGameRequest = new Gson().fromJson(req.body(), JoinGameRequest.class);
        JoinGameResult joinGameResult = gameService.joinGame(gameSQLDAO, userSQLDAO, authSQLDAO, authToken, joinGameRequest);
        if (joinGameResult.message() != null){
            if (joinGameResult.message().equals("Error: unauthorized")){
                res.status(401);
            }
            else if (joinGameResult.message().equals("Error: bad request")){
                res.status(400);
            }
            else if (joinGameResult.message().equals("Error: already taken")){
                res.status(403);
            }
        } else {
            res.status(200);
        }
        return new Gson().toJson(joinGameResult);
    }
}
