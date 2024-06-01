package dataaccess;

import com.google.gson.Gson;
import model.UserData;

import java.sql.*;
import java.util.ArrayList;

public class SQLUserDAO {

    private ArrayList<UserData> users = new ArrayList<>();

    public void deleteAllUsers() throws DataAccessException {
        Connection connection = null;

        try (var conn = DatabaseManager.getConnection()) {

            connection = conn;

            String sql = "delete from user_table";

            try(PreparedStatement stmt = connection.prepareStatement(sql)) {

                int count = stmt.executeUpdate();

                // System.out.printf("Deleted %d authTokens\n", count);
            }

        } catch (SQLException e) {
            throw new DataAccessException("Error while deleting all user data");
        }
    }

    public boolean userExists(String user) throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "SELECT username FROM user_table WHERE username=?";
            try (var ps = conn.prepareStatement(statement)) {
                ps.setString(1, user);
                try (var rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            throw new DataAccessException (String.format("Unable to read data: %s", e.getMessage()));
        }
        return false;
    }

    public void createUser(UserData userData) throws DataAccessException{
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "insert into user_table (username, password, email) values (?, ?, ?)";
            try (var ps = conn.prepareStatement(statement)) {
                ps.setString(1, userData.username());
                ps.setString(2, userData.password());
                ps.setString(3, userData.email());
                if(ps.executeUpdate() == 1) {
                    // Do Nothing
                } else {
                    System.out.println("Failed to insert user");
                }
            }
        } catch (Exception e) {
            throw new DataAccessException (String.format("Unable to read data: %s", e.getMessage()));
        }
    }

    public UserData getUser(String username, String password) throws DataAccessException{
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "SELECT username, password, email FROM user_table WHERE username=? AND password=?";
            try (var ps = conn.prepareStatement(statement)) {
                ps.setString(1, username);
                ps.setString(2, password);
                try (var rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return readUser(rs);
                    }
                }
            }
        } catch (Exception e) {
            throw new DataAccessException (String.format("Unable to read data: %s", e.getMessage()));
        }
        return null;
    }

    private UserData readUser(ResultSet rs) throws SQLException {
        return new UserData(rs.getString("username"), rs.getString("password"), rs.getString("email"));
    }
}
