package dataaccess;

import model.AuthData;

import java.util.ArrayList;
import java.util.UUID;

public class SQLAuthDAO {

    private ArrayList<AuthData> listOfAuthData = new ArrayList<>();

    public void deleteAllAuthData() throws DataAccessException{

    }

    public AuthData createAuth(String username){
        return null;
    }

    public AuthData getAuth(String authToken){

        return null;
    }

    public void deleteAuth(String authToken){

    }
}
