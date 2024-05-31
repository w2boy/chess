package dataaccess;

import model.AuthData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

public class SQLAuthDAO {

    private ArrayList<AuthData> listOfAuthData = new ArrayList<>();

    public void deleteAllAuthData() throws DataAccessException{

        String connectionURL = "jdbc:mysql://localhost:3306/chess";

        Connection connection = null;

        try (var conn = DatabaseManager.getConnection()) {

            connection = conn;

            String sql = "delete from auth_data_table";

            try(PreparedStatement stmt = connection.prepareStatement(sql)) {

                int count = stmt.executeUpdate();

                // System.out.printf("Deleted %d authTokens\n", count);
            }

        } catch (SQLException e) {
            throw new DataAccessException("Error while deleting all auth data");
        }
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
