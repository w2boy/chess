package service;

import dataaccess.*;
import model.AuthData;
import model.GameData;
import model.UserData;

import java.util.ArrayList;

public class ClearService {

    public ClearResult clearAllData(SQLGameDAO gameDAO, SQLUserDAO userDAO, SQLAuthDAO authDAO) throws DataAccessException {
        authDAO.deleteAllAuthData();
        userDAO.deleteAllUsers();
        gameDAO.deleteAllGames();
        return new ClearResult(null);
    }
}