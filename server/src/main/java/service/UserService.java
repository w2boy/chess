package service;

import dataaccess.MemoryAuthDAO;
import dataaccess.MemoryGameDAO;
import dataaccess.MemoryUserDAO;
import model.AuthData;
import model.UserData;

public class UserService {
    public AuthData register(MemoryGameDAO gameDAO, MemoryUserDAO userDAO, MemoryAuthDAO authDAO, UserData userData) {
        UserData existingUser = userDAO.findUser(userData.username());
        if (existingUser == null){
            userDAO.createUser(userData);
            AuthData authData = authDAO.createAuth(userData.username());
            return authData;
        }
        return null;
    }
    public AuthData login(MemoryGameDAO gameDAO, MemoryUserDAO userDAO, MemoryAuthDAO authDAO, UserData userData) {
        return null;
    }
    public void logout(MemoryGameDAO gameDAO, MemoryUserDAO userDAO, MemoryAuthDAO authDAO, UserData userData, AuthData authData) {}
}
