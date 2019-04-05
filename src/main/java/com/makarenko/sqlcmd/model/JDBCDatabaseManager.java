package com.makarenko.sqlcmd.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCDatabaseManager implements DatabaseManager {
    Connection connection;

    public void connect(String database, String userName, String password) {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Пожалуйста добавте файл jdbc jar в библиотеку или зависимость в файл pom.xml.", e);
        }
        try {
            if (connection != null) {
                connection.close();
            }
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/" + database, userName, password);
        } catch (SQLException e) {
            connection = null;
            throw new RuntimeException(String.format("Невозможно подключится к базе данных: " +
                    "%s user: %s password: %s ", database, userName, password), e);
        }
    }


}
