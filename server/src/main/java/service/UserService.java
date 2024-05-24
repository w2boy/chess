package service;

import dataaccess.MemoryAuthDAO;
import dataaccess.MemoryGameDAO;
import dataaccess.MemoryUserDAO;
import model.AuthData;
import model.UserData;

public class UserService {
    public LoginResult register(MemoryGameDAO gameDAO, MemoryUserDAO userDAO, MemoryAuthDAO authDAO, UserData userData) {
        if (userData.username() == null || userData.password() == null || userData.email() == null){
            return new LoginResult("Error: bad request", null, null);
        }
        UserData existingUser = userDAO.findUser(userData.username());
        if (existingUser == null){
            userDAO.createUser(userData);
            AuthData authData = authDAO.createAuth(userData.username());
            return new LoginResult(null, authData.username(), authData.authToken());
        }
        return new LoginResult("Error: already taken", null, null);
    }
    public LoginResult login(MemoryGameDAO gameDAO, MemoryUserDAO userDAO, MemoryAuthDAO authDAO, LoginRequest loginRequest) {
        UserData existingUser = userDAO.getUser(loginRequest.username(), loginRequest.password());
        if (existingUser != null){
            AuthData authData = authDAO.createAuth(existingUser.username());
            return new LoginResult(null,authData.username(), authData.authToken());
        }
        return new LoginResult("Error: unauthorized", null, null);
    }
    public LogoutResult logout(MemoryGameDAO gameDAO, MemoryUserDAO userDAO, MemoryAuthDAO authDAO, String authToken) {
        AuthData authData = authDAO.getAuth(authToken);
        if (authData != null){
            authDAO.deleteAuth(authToken);
            return new LogoutResult(null);
        }
        return null;
    }
}
