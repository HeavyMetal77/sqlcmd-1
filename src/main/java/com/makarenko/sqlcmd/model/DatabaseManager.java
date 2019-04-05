package com.makarenko.sqlcmd.model;

public interface DatabaseManager {
    void connect(String database, String userName, String password);

}
