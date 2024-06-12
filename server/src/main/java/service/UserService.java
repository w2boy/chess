package service;

import dataaccess.*;
import model.AuthData;
import model.UserData;

public class UserService {
    public LoginResult register(SQLGameDAO gameDAO, SQLUserDAO userDAO, SQLAuthDAO authDAO, UserData userData) throws DataAccessException {
        if (userData.username() == null || userData.password() == null || userData.email() == null){
            return new LoginResult("Error: bad request", null, null);
        }
        Boolean doesUserExist = userDAO.userExists(userData.username());
        if (!doesUserExist){
            userDAO.createUser(userData);
            AuthData authData = authDAO.createAuth(userData.username());
            return new LoginResult(null, authData.username(), authData.authToken());
        }
        return new LoginResult("Error: already taken", null, null);
    }
    public LoginResult login(SQLGameDAO gameDAO, SQLUserDAO userDAO, SQLAuthDAO authDAO, LoginRequest loginRequest) throws DataAccessException {
        UserData existingUser = userDAO.getUser(loginRequest.username(), loginRequest.password());
        if (existingUser != null){
            AuthData authData = authDAO.createAuth(existingUser.username());
            return new LoginResult(null,authData.username(), authData.authToken());
        }
        return new LoginResult("Error: unauthorized", null, null);
    }
    public LogoutResult logout(SQLGameDAO gameDAO, SQLUserDAO userDAO, SQLAuthDAO authDAO, String authToken) throws DataAccessException {
        AuthData authData = authDAO.getAuth(authToken);
        if (authData != null){
            authDAO.deleteAuth(authToken);
            return new LogoutResult(null);
        }
        return new LogoutResult("Error: unauthorized");
    }

//    public String getUsername(SQLUserDAO userDAO){
//        userDAO.getUsername()
//    }
}
