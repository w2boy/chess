package ui;

import com.google.gson.Gson;
import model.UserData;
import service.*;

import java.util.Arrays;

public class ChessClient {
    private ServerFacade server;
    private String serverUrl;
    private Repl repl;
    State state = State.LOGGED_OUT;
    String authToken = null;

    public ChessClient(String serverUrl, Repl repl) {
        server = new ServerFacade(serverUrl);
        this.serverUrl = serverUrl;
        this.repl = repl;
    }

    public String eval(String input) {
        var tokens = input.split(" ");
        var cmd = (tokens.length > 0) ? tokens[0] : "help";
        var params = Arrays.copyOfRange(tokens, 1, tokens.length);
        if (state == State.LOGGED_OUT) {
            return evalLoggedOut(input);
        }
        else {
            return evalLoggedIn(input);
        }
    }

    public String evalLoggedOut(String input) {
        try {
            var tokens = input.split(" ");
            var cmd = (tokens.length > 0) ? tokens[0] : "help";
            var params = Arrays.copyOfRange(tokens, 1, tokens.length);
            return switch (cmd) {
                case "register" -> register(params);
                case "login" -> logIn(params);
                case "quit" -> "quit";
                default -> help();
            };
        } catch (ResponseException ex) {
            return ex.getMessage();
        }
    }

    public String evalLoggedIn(String input) {
        try {
            var tokens = input.split(" ");
            var cmd = (tokens.length > 0) ? tokens[0] : "help";
            var params = Arrays.copyOfRange(tokens, 1, tokens.length);
            return switch (cmd) {
                case "create" -> createGame(params);
                case "list" -> listGames();
                case "join" -> joinGame(params);
                case "observe" -> observeGame(params);
                case "logout" -> logOut();
                case "quit" -> "quit";
                default -> help();
            };
        } catch (ResponseException ex) {
            return ex.getMessage();
        }
    }

    public String register(String... params) throws ResponseException {
        if (params.length >= 3) {
            var username = params[0];
            var password = params[1];
            var email = params[2];
            UserData newUser = new UserData(username, password, email);
            LoginResult loginResult = server.register(newUser);
            authToken = loginResult.authToken();
            state = State.LOGGED_IN;
            return String.format("You reigstered as %s.", username);
        }
        throw new ResponseException("Expected: <USERNAME> <PASSWORD> <EMAIL>");
    }

    public String logIn(String... params) throws ResponseException {
        assertLoggedOut();
        if (params.length >= 2) {
            var username = params[0];
            var password = params[1];
            LoginRequest loginRequest = new LoginRequest(username, password);
            LoginResult loginResult = server.logIn(loginRequest);
            authToken = loginResult.authToken();
            state = State.LOGGED_IN;
            return String.format("You logged in as %s.", username);
        }
        throw new ResponseException("Expected: <USERNAME> <PASSWORD>");
    }

    public String createGame(String... params) throws ResponseException {
        assertLoggedIn();
        if (params.length >= 1) {
            var gameName = params[0];
            CreateGameRequest createGameRequest = new CreateGameRequest(gameName);
            CreateGameResult createGameResult = server.createGame(createGameRequest, authToken);
            return String.format("You game ID is %d.", createGameResult.gameID());
        }
        throw new ResponseException("Expected: <name> <CAT|DOG|FROG>");
    }

    public String listGames() throws ResponseException {
        assertLoggedIn();
        ListGamesResult listGamesResult = server.listGames(authToken);
        var result = new StringBuilder();
        for (var game : listGamesResult.games()) {
            ListGamesOutput listGamesOutput = new ListGamesOutput(game.gameID(), game.whiteUsername(), game.blackUsername(), game.gameName());
            result.append(listGamesOutput).append('\n');
        }
        return result.toString();
    }

    public String joinGame(String... params) throws ResponseException {
        assertLoggedIn();
        if (params.length == 2) {
            int id = Integer.parseInt(params[0]);
            String color = params[1];
            JoinGameRequest joinGameRequest = new JoinGameRequest (color, id);
            JoinGameResult joingameResult = server.joinGame(joinGameRequest, authToken);
            return String.format("Joined Game");
        }
        throw new ResponseException("<ID> [WHITE|BLACK]");
    }

    public String observeGame(String... params) throws ResponseException {
        assertLoggedIn();
        if (params.length == 1) {
            int id = Integer.parseInt(params[0]);
            return String.format("Observing Game %d", id);
        }
        throw new ResponseException("<ID>");
    }

    public String logOut() throws ResponseException {
        assertLoggedIn();
        LogoutResult logoutResult = server.logOut(authToken);
        authToken = null;
        state = State.LOGGED_OUT;
        return String.format("You are logged out");
    }

    public String help(){
        if (state == State.LOGGED_OUT) {
            return """
                    1. register <USERNAME> <PASSWORD> <EMAIL> - to create an account
                    2. login <USERNAME> <PASSWORD> - to play chess
                    3. quit - playing chess
                    4. help - with possible commands
                    """;
        }
        return """
                1. create <NAME> - a game
                2. list - games
                3. join <ID> [WHITE|BLACK] - a game
                4. observe <ID> - a game
                5. logout - when you are done
                6. quit - playing chess
                7. help - with possible commands
                """;
    }

    private void assertLoggedIn() throws ResponseException {
        if (state == State.LOGGED_OUT) {
            throw new ResponseException("You must log in");
        }
    }

    private void assertLoggedOut() throws ResponseException {
        if (state == State.LOGGED_IN) {
            throw new ResponseException("You are already logged in");
        }
    }
}
