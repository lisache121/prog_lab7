package databaseUtils;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class UserChecker {

    private final Connection dbConnection;


    public UserChecker(Connection dbConnection) {
        this.dbConnection = dbConnection;
    }

    public boolean checkUserInData(Map.Entry<String,String> login) throws SQLException {
        PasswordEncryptor passwordEncryptor = new PasswordEncryptor();
        PreparedStatement statement = dbConnection.prepareStatement(Statements.checkUserInData.getStatement());
        statement.setString(1, login.getKey());
        statement.setString(2, passwordEncryptor.encrypt(login.getValue()));
        ResultSet resultSet = statement.executeQuery();
        return resultSet.next();
    }

    public boolean matchUsername(Map.Entry<String,String> login) throws SQLException {
        PreparedStatement statement = dbConnection.prepareStatement(Statements.checkUsernameInData.getStatement());
        statement.setString(1, login.getKey());
        ResultSet resultSet = statement.executeQuery();
        return resultSet.next();
    }

    public boolean addUser(Map.Entry<String,String> login) throws SQLException {
        PasswordEncryptor passwordEncryptor = new PasswordEncryptor();
        PreparedStatement statement = dbConnection.prepareStatement(Statements.addUserToData.getStatement());
        statement.setString(1, login.getKey());
        statement.setString(2, passwordEncryptor.encrypt(login.getValue()));
        statement.executeUpdate();
        return true;
    }
}
