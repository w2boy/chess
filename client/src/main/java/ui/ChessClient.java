package ui;

import chess.*;
import com.google.gson.Gson;
import model.*;
import model.UserData;
import websocket.messages.ErrorMessage;
import websocket.messages.LoadGameMessage;
import websocket.messages.NotificationMessage;
import websocket.messages.ServerMessage;

import java.util.Arrays;

public class ChessClient implements ServerMessageObserver {
    private final DrawBoard drawBoard = new DrawBoard();
    private ServerFacade server;
    private String serverUrl;
    private Repl repl;
    private int currentGameID;
    private String currentTeamColor;

    State state = State.LOGGED_OUT;
    String authToken = null;

    public ChessClient(String serverUrl, Repl repl) {
        server = new ServerFacade(serverUrl, this);
        this.serverUrl = serverUrl;
        this.repl = repl;
    }

    public void receiveNotification(ServerMessage message){
        switch (message.getServerMessageType()) {
            case NOTIFICATION -> displayNotification((NotificationMessage)message);
            case ERROR -> displayError((ErrorMessage)message);
            case LOAD_GAME -> loadGame();
        }
    }

    public String eval(String input) {
        var tokens = input.split(" ");
        var cmd = (tokens.length > 0) ? tokens[0] : "help";
        var params = Arrays.copyOfRange(tokens, 1, tokens.length);
        if (state == State.LOGGED_OUT) {
            return evalLoggedOut(input);
        }
        else if (state == State.LOGGED_IN) {
            return evalLoggedIn(input);
        }
        else if (state == State.PLAYING_GAME) {
            return evalPlayingGame(input);
        }
        else {
            return evalObservingGame(input);
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

    public String evalPlayingGame(String input) {
        try {
            var tokens = input.split(" ");
            var cmd = (tokens.length > 0) ? tokens[0] : "help";
            var params = Arrays.copyOfRange(tokens, 1, tokens.length);
            return switch (cmd) {
                case "redraw" -> loadGame();
                case "leave" -> leaveGame();
                case "makeMove" -> makeMove(params);
                default -> help();
            };
        } catch (Exception ex) {
            return ex.getMessage();
        }
    }

    public String evalObservingGame(String input) {
        try {
            var tokens = input.split(" ");
            var cmd = (tokens.length > 0) ? tokens[0] : "help";
            var params = Arrays.copyOfRange(tokens, 1, tokens.length);
            return switch (cmd) {
                case "redraw" -> loadGame();
                case "leave" -> leaveGame();
                default -> help();
            };
        } catch (Exception ex) {
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
            currentGameID = id;
            currentTeamColor = color;
            server.joinGameWebsocket(authToken, id);
            state = State.PLAYING_GAME;
            return String.format("Joined Game");
        }
        throw new ResponseException("<ID> [WHITE|BLACK]");
    }

    public String makeMove(String... params) throws ResponseException {
        if (params.length >= 1) {
            int id = currentGameID;
            String chessMoveString = params[0];
            String promotionPieceString = null;
            if (params[1] != null){
                promotionPieceString = params[1];
            }
            ChessMove chessMove = createMove(chessMoveString, promotionPieceString);
            server.makeMoveWebsocket(authToken, id, chessMove);
            return "";
        }
        throw new ResponseException("makeMove <CHESS MOVE> <PROMOTION PIECE>");
    }

    public ChessMove createMove(String chessMoveString, String promotionPieceString) throws ResponseException {
        int firstMoveHorizontalView = -1;
        switch(chessMoveString.charAt(0)){
            case 'a':
                firstMoveHorizontalView = 0;
                break;
            case 'b':
                firstMoveHorizontalView = 1;
                break;
            case 'c':
                firstMoveHorizontalView = 2;
                break;
            case 'd':
                firstMoveHorizontalView = 3;
                break;
            case 'e':
                firstMoveHorizontalView = 4;
                break;
            case 'f':
                firstMoveHorizontalView = 5;
                break;
            case 'g':
                firstMoveHorizontalView = 6;
                break;
            case 'h':
                firstMoveHorizontalView = 7;
                break;
            default:
                throw new ResponseException("INVALID CHESS MOVE");
        }
        int firstMoveVerticalView = -1;
        if (Character.isDigit(chessMoveString.charAt(1))){
            firstMoveVerticalView = (int) chessMoveString.charAt(1);
        } else {
            throw new ResponseException("INVALID CHESS MOVE");
        }
        int secondMoveHorizontalView = -1;
        switch(chessMoveString.charAt(2)){
            case 'a':
                secondMoveHorizontalView = 1;
                break;
            case 'c':
                secondMoveHorizontalView = 2;
                break;
            case 'd':
                secondMoveHorizontalView = 3;
                break;
            case 'e':
                secondMoveHorizontalView = 4;
                break;
            case 'f':
                secondMoveHorizontalView = 5;
                break;
            case 'g':
                secondMoveHorizontalView = 6;
                break;
            case 'h':
                secondMoveHorizontalView = 7;
                break;
            default:
                throw new ResponseException("INVALID CHESS MOVE");
        }
        int secondMoveVerticalView = -1;
        if (Character.isDigit(chessMoveString.charAt(3))){
            secondMoveVerticalView = (int) chessMoveString.charAt(3);
        } else {
            throw new ResponseException("INVALID CHESS MOVE");
        }
        ChessPiece.PieceType promotionPieceType = null;
        switch (promotionPieceString){
            case "QUEEN":
                promotionPieceType = ChessPiece.PieceType.QUEEN;
                break;
            case "ROOK":
                promotionPieceType = ChessPiece.PieceType.ROOK;
                break;
            case "BISHOP":
                promotionPieceType = ChessPiece.PieceType.BISHOP;
                break;
            case "KNIGHT":
                promotionPieceType = ChessPiece.PieceType.KNIGHT;
                break;
        }
        ChessPosition startPosition = new ChessPosition(firstMoveVerticalView, firstMoveHorizontalView);
        ChessPosition endPosition = new ChessPosition(secondMoveVerticalView, secondMoveHorizontalView);
        return new ChessMove(startPosition, endPosition, promotionPieceType);
    }

    public String leaveGame() {
        int id = currentGameID;
        server.leaveGameWebsocket(authToken, id);
        state = State.LOGGED_IN;
        return String.format("Left Game");
    }

    public String observeGame(String... params) throws ResponseException {
        assertLoggedIn();
        if (params.length == 1) {
            int id = Integer.parseInt(params[0]);
            server.joinGameWebsocket(authToken, id);
            state = State.OBSERVING_GAME;
            currentGameID = id;
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
        } else if (state == State.LOGGED_IN) {
            return """
                1. create <NAME> - a game
                2. list - games
                3. join <ID> [WHITE|BLACK] - a game
                4. observe <ID> - a game
                5. logout - when you are done
                6. quit - playing chess
                7. help - with possible commands
                """;
        } else if (state == State.PLAYING_GAME) {
            return """
                1. redraw - the  board
                2. leave - the game
                3. makeMove <CHESS MOVE> <PROMOTION PIECE> - move a piece
                4. resign - forfeit the game
                5. Highlight Legal Moves - of piece
                7. help - with possible commands
                """;
        } else {
            return """
                1. redraw - the board
                2. leave - the game
                3. Highlight Legal Moves - of piece
                4. help - with possible commands
                """;
        }

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

    private void displayNotification(NotificationMessage notificationMessage) {
        System.out.println(notificationMessage.getMessage());
    }

    private void displayError(ErrorMessage errorMessage) {
        System.out.println(errorMessage.getErrorMessage());
    }

    private String loadGame() {
        try {
            ChessBoard chessBoard = server.getGameBoard(new GetBoardRequest(currentGameID));
            ChessPiece[][] matrix = chessBoard.squares;

            // If currentTeamColor is null, then the client is observing and should see the board as white.
            if (currentTeamColor == null){
                drawBoard.run(matrix, "WHITE");
            } else {
                drawBoard.run(matrix, currentTeamColor);
            }
        } catch (Exception ex) {
            System.out.println("Error loading game: " + ex.getMessage());
        }
        return "";
    }
}
