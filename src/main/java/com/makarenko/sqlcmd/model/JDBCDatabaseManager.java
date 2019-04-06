package com.makarenko.sqlcmd.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

public class JDBCDatabaseManager implements DatabaseManager {
    Connection connection;

    public void connect(String database, String userName, String password) throws SQLException, ClassNotFoundException {
            Class.forName("org.postgresql.Driver");
            if (connection != null) connection.close();

            connection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/" + database, userName, password);
    }

    @Override
    public void createTable(String tableName, String keyName, Map<String, Object> columns) throws SQLException {
        Statement stmt = connection.createStatement();
        stmt.executeUpdate("CREATE TABLE " + tableName + " ( " + keyName + " INT PRIMARY KEY NOT NULL " +
                getParameters(columns) + ")");
        stmt.close();
    }

    private String getParameters(Map<String, Object> columns) {
        String result = "";
        for (Map.Entry<String, Object> pair : columns.entrySet()) {
            result += ", " + pair.getKey() + " " + pair.getValue();
        }
        return result;
    }

    @Override
    public boolean isConnected() {
        return connection != null;
    }
}
