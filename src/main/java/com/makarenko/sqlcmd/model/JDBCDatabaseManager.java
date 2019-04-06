package com.makarenko.sqlcmd.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCDatabaseManager implements DatabaseManager {
    Connection connection;

    public void connect(String database, String userName, String password) throws SQLException, ClassNotFoundException {
            Class.forName("org.postgresql.Driver");
            if (connection != null) connection.close();

            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/" + database, userName, password);
    }

    @Override
    public boolean isConnected() {
        return connection != null;
    }
}
