package dataaccess;

import model.AuthData;
import model.UserData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

public class SQLAuthDAO {

    public void deleteAllAuthData() throws DataAccessException{

        Connection connection = null;

        try (var conn = DatabaseManager.getConnection()) {

            connection = conn;

            String sql = "delete from auth_table";

            try(PreparedStatement stmt = connection.prepareStatement(sql)) {

                int count = stmt.executeUpdate();

                // System.out.printf("Deleted %d authTokens\n", count);
            }

        } catch (SQLException e) {
            throw new DataAccessException("Error while deleting all auth data");
        }
    }

    public AuthData createAuth(String username) throws DataAccessException {
        String authToken = UUID.randomUUID().toString();
        AuthData authData = new AuthData(authToken, username);
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "insert into auth_table (auth_token, username) values (?, ?)";
            try (var ps = conn.prepareStatement(statement)) {
                ps.setString(1, authData.authToken());
                ps.setString(2, authData.username());
                if(ps.executeUpdate() == 1) {
                    // Do Nothing
                } else {
                    System.out.println("Failed to insert authData");
                }
            }
        } catch (Exception e) {
            throw new DataAccessException (String.format("Unable to read data: %s", e.getMessage()));
        }
        return authData;
    }

    public AuthData getAuth(String authToken) throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "SELECT auth_token, username FROM auth_table WHERE auth_token=?";
            try (var ps = conn.prepareStatement(statement)) {
                ps.setString(1, authToken);
                try (var rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return readAuth(rs);
                    }
                }
            }
        } catch (Exception e) {
            throw new DataAccessException (String.format("Unable to read data: %s", e.getMessage()));
        }
        return null;
    }

    private AuthData readAuth(ResultSet rs) throws SQLException {
        return new AuthData(rs.getString("auth_token"), rs.getString("username"));
    }

    public void deleteAuth(String authToken) throws DataAccessException {
        Connection connection = null;

        try (var conn = DatabaseManager.getConnection()) {

            connection = conn;

            String sql = "delete from auth_table WHERE auth_token=?";

            try(PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, authToken);
                int count = stmt.executeUpdate();

                // System.out.printf("Deleted %d authTokens\n", count);
            }

        } catch (SQLException e) {
            throw new DataAccessException("Error while deleting single auth data");
        }
    }


}
