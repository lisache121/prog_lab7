package databaseUtils;

import log.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector {
    private final String url;
    private final String username;
    private final String password;

    public DBConnector(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public Connection getNewConnection() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            Log.logger.error("DB driver not found!");
            return null;
        }
        return DriverManager.getConnection(url, username, password);
    }


}
