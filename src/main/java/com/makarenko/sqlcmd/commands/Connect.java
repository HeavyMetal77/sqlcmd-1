package com.makarenko.sqlcmd.commands;

import com.makarenko.sqlcmd.model.DatabaseManager;
import com.makarenko.sqlcmd.view.Message;
import java.sql.SQLException;

public class Connect implements Command {
    private Message message;
    private DatabaseManager databaseManager;
    private int count = 4;

    public Connect(DatabaseManager databaseManager, Message message) {
        this.message = message;
        this.databaseManager = databaseManager;
    }

    @Override
    public String formatCommand() {
        return "connect|database|user|password";
    }

    @Override
    public String depictionCommand() {
        return "Подключение к базе данных";
    }

    @Override
    public boolean beginCommand(String command) {
        return command.startsWith("connect|");
    }

    @Override
    public void executionCommand(String command) {
        String[] data = command.split("\\|");
        if (data.length != count) {
            isCorrectCommand(command);
            return;
        }

        String database = data[1];
        String userName = data[2];
        String password = data[3];

        try {
            databaseManager.connect(database, userName, password);
            message.write(String.format("Подключение к базе даных '%s' произошло успешно", database));
        } catch (SQLException e) {
            message.write(String.format("Не удалось подключиться к базе данных '%s' по причине '%s'", database, e.getMessage()));
        } catch (ClassNotFoundException e) {
            message.write(String.format("Нет файла jdbc.jar, добавте его в библиотеку"));
        }
    }

    private void isCorrectCommand(String command) {
        message.write(String.format("вы ввели неверное количество параметров " +
                "'%s', а должно быть connect|database|user|password", command));
    }
}
