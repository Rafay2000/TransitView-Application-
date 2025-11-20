package hiof_project.infrastructure.config;

import hiof_project.domain.exception.SQLDatabaseException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnectionSQL {

    private String url;
    private String username;
    private String password;
    private Connection connection;

    //Konstruktør til å opprette database tilkobling med url, brukernavn og passord
    public DatabaseConnectionSQL(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    //Starter database tilkobling
    public Connection initializeDatabase() throws SQLDatabaseException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, username, password);
            return connection;
        }
        catch (ClassNotFoundException e) {
            throw new SQLDatabaseException("ERROR: Could not find driver class", e);
        }
        catch (SQLException e) {
            throw new SQLDatabaseException("ERROR: Database could not be connected", e);
        }
    }

    //Henter tilkoblingen
    public Connection getConnection() {
        return connection;
    }

    //Avslutter database tilkobling
    public void closeDatabase() throws SQLDatabaseException {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new SQLDatabaseException("ERROR: Could not close database", e);
        }

    }
}
