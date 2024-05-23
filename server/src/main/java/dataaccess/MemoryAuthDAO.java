package dataaccess;

import model.AuthData;

import java.util.ArrayList;

public class MemoryAuthDAO {

    private ArrayList<AuthData> listOfAuthData = new ArrayList<>();

    public void deleteAllAuthData() throws DataAccessException{
        listOfAuthData.clear();
    }
}
