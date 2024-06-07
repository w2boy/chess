package ui;

import model.UserData;
import service.LoginRequest;
import service.LoginResult;

import java.util.Arrays;

public class ChessClient {
    private String visitorName = null;
    private final ServerFacade server;
    private final String serverUrl;
    private final Repl repl;
    State state = State.LOGGED_OUT;
    String authToken = null;

    public ChessClient(String serverUrl, Repl repl) {
        server = new ServerFacade(serverUrl);
        this.serverUrl = serverUrl;
        this.repl = repl;
    }

    public String eval(String input) {
        var tokens = input.toLowerCase().split(" ");
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
            var tokens = input.toLowerCase().split(" ");
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
            var tokens = input.toLowerCase().split(" ");
            var cmd = (tokens.length > 0) ? tokens[0] : "help";
            var params = Arrays.copyOfRange(tokens, 1, tokens.length);
            return switch (cmd) {
                case "create" -> createGame(params);
//                case "list" -> listGames();
//                case "join" -> joinGame(params);
//                case "observe" -> observeGame(params);
//                case "logout" -> logOut();
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
//        assertSignedIn();
//        if (params.length >= 2) {
//            var name = params[0];
//            var type = PetType.valueOf(params[1].toUpperCase());
//            var pet = new Pet(0, name, type);
//            pet = server.addPet(pet);
//            return String.format("You rescued %s. Assigned ID: %d", pet.name(), pet.id());
//        }
        return "createGame";
        //throw new ResponseException("Expected: <name> <CAT|DOG|FROG>");
    }
//
//    public String listGames() throws ResponseException {
//        assertSignedIn();
//        var pets = server.listPets();
//        var result = new StringBuilder();
//        var gson = new Gson();
//        for (var pet : pets) {
//            result.append(gson.toJson(pet)).append('\n');
//        }
//        return result.toString();
//    }
//
//    public String joinGame(String... params) throws ResponseException {
//        assertSignedIn();
//        if (params.length == 1) {
//            try {
//                var id = Integer.parseInt(params[0]);
//                var pet = getPet(id);
//                if (pet != null) {
//                    server.deletePet(id);
//                    return String.format("%s says %s", pet.name(), pet.sound());
//                }
//            } catch (NumberFormatException ignored) {
//            }
//        }
//        throw new ResponseException(400, "Expected: <pet id>");
//    }
//
//    public String observeGame(String... params) throws ResponseException {
//        assertSignedIn();
//        if (params.length == 1) {
//            try {
//                var id = Integer.parseInt(params[0]);
//                var pet = getPet(id);
//                if (pet != null) {
//                    server.deletePet(id);
//                    return String.format("%s says %s", pet.name(), pet.sound());
//                }
//            } catch (NumberFormatException ignored) {
//            }
//        }
//        throw new ResponseException(400, "Expected: <pet id>");
//    }
//
//    public String logOut() throws ResponseException {
//        assertSignedIn();
//        state = State.LOGGED_OUT;
//        return String.format("%s left the shop", visitorName);
//    }
//
//    private Pet getPet(int id) throws ResponseException {
//        for (var pet : server.listPets()) {
//            if (pet.id() == id) {
//                return pet;
//            }
//        }
//        return null;
//    }

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
