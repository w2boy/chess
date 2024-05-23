package dataaccess;

import model.AuthData;

public interface AuthDAO {

    void deleteAllAuthData() throws DataAccessException;
    AuthData createAuth() throws DataAccessException;

}
