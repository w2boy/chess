package dataaccess;

import model.UserData;

import javax.xml.crypto.Data;

public interface UserDAO {
    void deleteAllUsers() throws DataAccessException;
    void userExists() throws DataAccessException;
    void createUser() throws DataAccessException;
    UserData getUser() throws DataAccessException;

}
