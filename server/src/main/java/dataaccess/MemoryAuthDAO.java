package dataaccess;

import model.AuthData;

import java.util.ArrayList;
import java.util.UUID;

public class MemoryAuthDAO {

    private ArrayList<AuthData> listOfAuthData = new ArrayList<>();

    public void deleteAllAuthData() throws DataAccessException{
        listOfAuthData.clear();
    }

    public AuthData createAuth(String username){
        String authToken = UUID.randomUUID().toString();
        AuthData authData = new AuthData(authToken, username);
        listOfAuthData.add(authData);
        return authData;
    }
}
