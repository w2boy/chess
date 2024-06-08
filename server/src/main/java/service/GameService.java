package service;

import chess.ChessBoard;
import dataaccess.*;
import model.AuthData;
import model.GameData;

import java.util.List;

public class GameService {
    public ListGamesResult listGames(SQLGameDAO gameDAO, SQLUserDAO userDAO, SQLAuthDAO authDAO, String authToken) throws DataAccessException {
        AuthData authData = authDAO.getAuth(authToken);
        if (authData != null){
            return gameDAO.getListOfGames();
        }
        return new ListGamesResult("Error: unauthorized", null);
    }
    public CreateGameResult createGame(SQLGameDAO gameDAO, SQLUserDAO userDAO, SQLAuthDAO authDAO, String authToken, CreateGameRequest createGameRequest) throws DataAccessException {
        AuthData authData = authDAO.getAuth(authToken);
        if (authData != null){
            return gameDAO.createGame(createGameRequest);
        }
        return new CreateGameResult("Error: unauthorized", null);
    }
    public JoinGameResult joinGame(SQLGameDAO gameDAO, SQLUserDAO userDAO, SQLAuthDAO authDAO, String authToken, JoinGameRequest joinGameRequest) throws DataAccessException {
        AuthData authData = authDAO.getAuth(authToken);
        if (authData != null){
            return gameDAO.joinGame(joinGameRequest, authData.username());
        }
        return new JoinGameResult("Error: unauthorized");
    }

    public ChessBoard getGameBoard(SQLGameDAO gameDAO, GetBoardRequest getBoardRequest) throws DataAccessException {
        return gameDAO.getGameBoard( getBoardRequest);
    }
}
