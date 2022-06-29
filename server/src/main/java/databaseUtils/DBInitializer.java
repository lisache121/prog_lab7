package databaseUtils;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DBInitializer {
    private final Connection connection;
    private final Statement sqlStatement;

    public DBInitializer(Connection connection) throws SQLException {
        this.connection = connection;
        this.sqlStatement = connection.createStatement();
    }

    public void initialize() throws SQLException {
        //sqlStatement.execute(Statements.deleteAll.getStatement());
        //sqlStatement.execute(Statements.deleteUsersTable.getStatement());
        //sqlStatement.execute(Statements.deleteHeadTable.getStatement());
        //sqlStatement.execute(Statements.deleteCoordTable.getStatement());
        sqlStatement.execute(Statements.createUsersTable.getStatement());
        sqlStatement.execute(Statements.createDragonTable.getStatement());
        sqlStatement.execute(Statements.createCoordinateTable.getStatement());
        sqlStatement.execute(Statements.createHeadTable.getStatement());


    }

}
