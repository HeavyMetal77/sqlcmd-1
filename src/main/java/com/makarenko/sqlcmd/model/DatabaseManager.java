package com.makarenko.sqlcmd.model;

import java.sql.SQLException;
import java.util.Map;

public interface DatabaseManager {
    void connect(String database, String userName, String password) throws SQLException, ClassNotFoundException;

    boolean isConnected();

    void createTable(String tableName, String keyName, Map<String, Object> columns) throws SQLException;

    void dropTable(String tableName) throws SQLException;
}
