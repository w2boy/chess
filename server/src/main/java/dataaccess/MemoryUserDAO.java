package dataaccess;

import model.UserData;

import java.util.ArrayList;

public class MemoryUserDAO {

    private ArrayList<UserData> users = new ArrayList<>();

    public void deleteAllUsers() throws DataAccessException {
        users.clear();
    }
}
