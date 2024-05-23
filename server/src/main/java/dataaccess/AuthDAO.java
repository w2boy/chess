package dataaccess;

import model.AuthData;

public interface AuthDAO {

    void deleteAllAuthData() throws DataAccessException;
    AuthData createAuth() throws DataAccessException;
    AuthData getAuth() throws DataAccessException;
    AuthData deleteAuth() throws DataAccessException;
}
