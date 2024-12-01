package upm.app.data.repositorios.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class RepositoryH2 {
    private static final String URL = "jdbc:h2:mem:test";

    public Connection createConnection() {
        try {
            return DriverManager.getConnection(RepositoryH2.URL);
        } catch (SQLException e) {
            throw new UnsupportedOperationException("Connection error with '" + RepositoryH2.URL + "'. " + e.getMessage());
        }
    }


}
