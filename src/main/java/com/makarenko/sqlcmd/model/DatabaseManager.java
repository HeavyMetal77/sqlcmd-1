package com.makarenko.sqlcmd.model;

import java.sql.SQLException;

public interface DatabaseManager {
    void connect(String database, String userName, String password) throws SQLException, ClassNotFoundException;

    boolean isConnected();
}
