package com.makarenko.sqlcmd.model;

import com.makarenko.sqlcmd.view.PrintTable;
import java.sql.*;
import java.util.Iterator;
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
        statement.close();
        resultSet.close();
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
        String sql = "DROP TABLE ";
        Statement statement = connection.createStatement();
        statement.executeUpdate(sql + tableName);
        statement.close();
    }

    @Override
    public void clearTable(String tableName) throws SQLException {
        String sql = "DELETE FROM " + tableName;
        Statement statement = connection.createStatement();
        statement.executeUpdate(sql);
        statement.close();
    }

    @Override
    public void findTable(String tableName) throws SQLException {
        String sql = "SELECT * FROM " + tableName;
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        PrintTable.printResultSet(resultSet);
        statement.close();
        resultSet.close();
    }

    @Override
    public void insert(String tableName, Map<String, Object> row) throws SQLException {
        Statement statement = connection.createStatement();
        StringBuilder sql = new StringBuilder("INSERT INTO " + tableName + " (");
        Iterator<String> iteratorKey = row.keySet().iterator();
        while (iteratorKey.hasNext()) {
            sql.append(iteratorKey.next());
            if (iteratorKey.hasNext()) {
                sql.append(", ");
            }
        }
        sql.append(") VALUES (");
        Iterator<Object> iteratorValue = row.values().iterator();
        while (iteratorValue.hasNext()) {
            sql.append("'" + iteratorValue.next() + "'");
            if (iteratorValue.hasNext()) {
                sql.append(", ");
            }
        }
        sql.append(")").toString();
        statement.executeUpdate(String.valueOf(sql));
        statement.close();
    }

    @Override
    public void update(String tableName, String keyName, String keyValue, Map<String, Object> column) throws SQLException {
        Statement statement = connection.createStatement();
        for (Map.Entry<String, Object> pair : column.entrySet()) {
            statement.executeUpdate("UPDATE " + tableName +
                    " SET " + pair.getKey() + " = '" + pair.getValue() +
                    "' WHERE " + keyName + " = '" + keyValue + "'");
        }
        statement.close();
    }

    @Override
    public void delete(String tableName, String columnName, String columnValue) throws SQLException {
        String sql = "DELETE FROM " + tableName + " WHERE " + columnName + " = '" + columnValue + "'";
        Statement stmt = connection.createStatement();
        stmt.executeUpdate(sql);
        stmt.close();
    }

    @Override
    public boolean isConnected() {
        return connection != null;
    }
}
