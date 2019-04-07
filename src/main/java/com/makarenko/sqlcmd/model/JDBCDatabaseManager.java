package com.makarenko.sqlcmd.model;

import java.sql.*;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class JDBCDatabaseManager implements DatabaseManager {
    Connection connection;

    public void connect(String database, String userName, String password) throws SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        if (connection != null) connection.close();

        connection = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/" + database, userName, password);
    }

    @Override
    public Set<String> listTables() throws SQLException {
        Set<String> tables = new LinkedHashSet<>();
        String sql = "SELECT table_name FROM information_schema.tables WHERE table_schema='public'";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                tables.add(resultSet.getString("table_name"));
            }
            return tables;
    }

    @Override
    public void createTable(String tableName, String keyName, Map<String, Object> columns) throws SQLException {
        Statement stmt = connection.createStatement();

        String result = "";
        for (Map.Entry<String, Object> pair : columns.entrySet()) {
            result += ", " + pair.getKey() + " " + pair.getValue();
        }

        stmt.executeUpdate("CREATE TABLE " + tableName +
                " ( " + keyName + " INT PRIMARY KEY NOT NULL " + result + ")");
        stmt.close();
    }

    @Override
    public void dropTable(String tableName) throws SQLException {
        Statement statement = connection.createStatement();
        statement.executeUpdate("DROP TABLE " + tableName);
        statement.close();
    }

    @Override
    public boolean isConnected() {
        return connection != null;
    }
}
