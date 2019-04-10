package com.makarenko.sqlcmd.model;

import java.sql.SQLException;
import java.util.Map;
import java.util.Set;

public interface DatabaseManager {
    void connect(String database, String userName, String password);

    boolean isConnected();

    Set<String> listTables() throws SQLException;

    void createTable(String tableName, String keyName, Map<String, Object> columns) throws SQLException;

    void dropTable(String tableName) throws SQLException;

    void clearTable(String tableName);

    void findTable(String tableName) throws SQLException;

    void insert(String tableName, Map<String, Object> row) throws SQLException;

    void update(String tableName, String keyName, String keyValue, Map<String,Object> columnData) throws SQLException;

    void delete(String tableName, String columnName, String columnValue) throws SQLException;
}
