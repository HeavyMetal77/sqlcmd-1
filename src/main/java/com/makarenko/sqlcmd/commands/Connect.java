package com.makarenko.sqlcmd.commands;

import com.makarenko.sqlcmd.model.DatabaseManager;
import com.makarenko.sqlcmd.view.Message;

public class Connect implements Command {
    private Message message;
    private DatabaseManager databaseManager;

    public Connect(DatabaseManager databaseManager, Message message) {
        this.message = message;
        this.databaseManager = databaseManager;
    }

    @Override
    public boolean beginCommand(String command) {
        return command.startsWith("connect|");
    }

    @Override
    public void executionCommand(String command) {
        String[] data = command.split("\\|");
        if (data.length != 4) {
            throw new IllegalArgumentException(String.format(
                    "вы неверно ввели команду '%s', а должно быть connect|database|user|password", command));
        }

        String database = data[1];
        String userName = data[2];
        String password = data[3];

        databaseManager.connect(database, userName, password);
        message.write(String.format("Подключение к базе даных '%s' произошло успешно", database));
    }

    @Override
    public String formatCommand() {
        return "connect|database|user|password";
    }

    @Override
    public String depictionCommand() {
        return "Подключение к базе данных";
    }
}
