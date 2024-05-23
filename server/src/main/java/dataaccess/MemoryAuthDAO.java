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

    public AuthData getAuth(String authToken){
        for (AuthData authData : listOfAuthData){
            if (authData.authToken().equals(authToken)){
                return authData;
            }
        }
        return null;
    }

    public void deleteAuth(String authToken){
        AuthData authDataToDelete = null;
        for (AuthData authData : listOfAuthData){
            if (authData.authToken().equals(authToken)){
                authDataToDelete = authData;
            }
        }
        if (authDataToDelete != null){
            listOfAuthData.remove(authDataToDelete);
        }
    }
}
