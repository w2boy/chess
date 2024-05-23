package dataaccess;

import model.UserData;

import java.util.ArrayList;

public class MemoryUserDAO {

    private ArrayList<UserData> users = new ArrayList<>();

    public void deleteAllUsers() throws DataAccessException {
        users.clear();
    }

    public UserData findUser(String username){
        for (UserData userData : users){
            if (userData.username().equals(username)){
                return userData;
            }
        }
        return null;
    }

    public void createUser(UserData userData){
        users.add(userData);
    }

    public UserData getUser(String username, String password){
        for (UserData userData : users){
            if (userData.username().equals(username)){
                if (userData.password().equals(password)){
                    return userData;
                }
            }
        }
        return null;
    }
}
