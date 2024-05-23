package service;

import dataaccess.MemoryAuthDAO;
import dataaccess.MemoryGameDAO;
import dataaccess.MemoryUserDAO;
import model.AuthData;
import model.UserData;

public class UserService {
    public AuthData register(MemoryGameDAO gameDAO, MemoryUserDAO userDAO, MemoryAuthDAO authDAO, UserData user) {
        return null;
    }
    public AuthData login(MemoryGameDAO gameDAO, MemoryUserDAO userDAO, MemoryAuthDAO authDAO, UserData user) {
        return null;
    }
    public void logout(MemoryGameDAO gameDAO, MemoryUserDAO userDAO, MemoryAuthDAO authDAO, UserData user, AuthData authData) {}
}
