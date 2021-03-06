package com.makarenko.sqlcmd.model;

import java.util.Map;
import java.util.Set;

public interface DatabaseManager {
    void connect(String database, String userName, String password);

    boolean isConnected();

    Set<String> listTables();

    void createTable(String tableName, String keyName, Map<String, Object> columns);

    void dropTable(String tableName);

    void clearTable(String tableName);

    void findTable(String tableName);

    void insert(String tableName, Map<String, Object> row);

    void update(String tableName, String keyName, String keyValue, Map<String, Object> columnData);

    void delete(String tableName, String columnName, String columnValue);
}
