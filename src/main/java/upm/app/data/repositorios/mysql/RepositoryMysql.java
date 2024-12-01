package upm.app.data.repositorios.mysql;

import org.apache.logging.log4j.LogManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class RepositoryMysql {
    private static final String DATABASE = "mysql";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public RepositoryMysql() {
        this.dropDatabase();  //Only develop
        this.createDatabase();
    }

    private void dropDatabase() {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = connection.createStatement()) {
            statement.executeUpdate("USE mysql;");
            statement.executeUpdate("DROP DATABASE IF EXISTS " + DATABASE + ";");
        } catch (SQLException e) {
            LogManager.getLogger(this.getClass()).debug(e);
        }
    }

    private void createDatabase() {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = connection.createStatement()) {
            statement.executeUpdate("USE mysql;");
            statement.executeUpdate("CREATE DATABASE IF NOT EXISTS " + DATABASE + ";");
        } catch (SQLException e) {
            LogManager.getLogger(this.getClass()).debug(e);
        }
    }

    public Connection createConnection() {
        try {
            Class.forName(RepositoryMysql.DRIVER);
            return DriverManager.getConnection(URL + DATABASE, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new UnsupportedOperationException("Driver error: '" + RepositoryMysql.DRIVER + "'. " + e.getMessage());
        } catch (SQLException e) {
            throw new UnsupportedOperationException("Connection error with '" + RepositoryMysql.URL + "'. " + e.getMessage());
        }
    }

}
