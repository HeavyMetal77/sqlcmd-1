package com.makarenko.sqlcmd.model;

import com.makarenko.sqlcmd.view.PrintTable;

import java.sql.*;
import java.util.*;

public class JDBCDatabaseManager implements DatabaseManager {
    Connection connection;

    public void connect(String database, String userName, String password) {
        try {
            Class.forName("org.postgresql.Driver");
            if (connection != null) connection.close();
            connection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/" + database, userName, password);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Setup JDBC driver", e);
        } catch (SQLException e) {
            connection = null;
            throw new RuntimeException(String.format(
                    "Could not connect to database '%s' ", database), e);
        }
    }

    @Override
    public Set<String> listTables() {
        String sql = "SELECT table_name FROM information_schema.tables WHERE table_schema='public'";
        try (Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(sql)) {
            Set<String> tables = new LinkedHashSet<>();
            while (resultSet.next()) {
                tables.add(resultSet.getString("table_name"));
            }
            return tables;
        } catch (SQLException e) {
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }

    @Override
    public void createTable(String tableName, String keyName, Map<String, Object> columns) {
        try (Statement stmt = connection.createStatement()) {
            String result = "";
            for (Map.Entry<String, Object> pair : columns.entrySet()) {
                result += ", " + pair.getKey() + " " + pair.getValue();
            }
            stmt.executeUpdate("CREATE TABLE " + tableName +
                    " ( " + keyName + " INT PRIMARY KEY NOT NULL " + result + ")");
        } catch (SQLException e) {
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }

    @Override
    public void dropTable(String tableName) {
        String sql = "DROP TABLE ";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql + tableName);
        } catch (SQLException e) {
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }

    @Override
    public void clearTable(String tableName) {
        String sql = "DELETE FROM " + tableName;
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }

    @Override
    public void findTable(String tableName) {
        String sql = "SELECT * FROM " + tableName;
        try (Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(sql)) {
            PrintTable.printResultSet(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }

    @Override
    public void insert(String tableName, Map<String, Object> row) {
        try (Statement statement = connection.createStatement()) {
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
        } catch (SQLException e) {
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }

    @Override
    public void update(String tableName, String keyName, String keyValue, Map<String, Object> column) {
        try (Statement statement = connection.createStatement()) {
            for (Map.Entry<String, Object> pair : column.entrySet()) {
                statement.executeUpdate("UPDATE " + tableName +
                        " SET " + pair.getKey() + " = '" + pair.getValue() +
                        "' WHERE " + keyName + " = '" + keyValue + "'");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }

    @Override
    public void delete(String tableName, String columnName, String columnValue) {
        String sql = "DELETE FROM " + tableName + " WHERE " + columnName + " = '" + columnValue + "'";
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }

    @Override
    public boolean isConnected() {
        return connection != null;
    }
}
