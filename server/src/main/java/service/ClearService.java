package service;

import dataaccess.DataAccessException;
import dataaccess.MemoryAuthDAO;
import dataaccess.MemoryGameDAO;
import dataaccess.MemoryUserDAO;
import model.AuthData;
import model.GameData;
import model.UserData;

import java.util.ArrayList;

public class ClearService {

    public ClearResult clearAllData(MemoryGameDAO gameDAO, MemoryUserDAO userDAO, MemoryAuthDAO authDAO) throws DataAccessException {
        authDAO.deleteAllAuthData();
        userDAO.deleteAllUsers();
        gameDAO.deleteAllGames();
        return new ClearResult(null);
    }
}