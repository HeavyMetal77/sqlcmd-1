package com.makarenko.sqlcmd.commands;

import com.makarenko.sqlcmd.model.DatabaseManager;
import com.makarenko.sqlcmd.view.Message;
import com.makarenko.sqlcmd.view.MessageColor;

public class Connect implements Command {
    private Message message;
    private DatabaseManager databaseManager;
    private MessageColor messageColor = new MessageColor();

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
            throw new IllegalArgumentException(messageColor.getErrorMessage(command) +
                    "connect|database|user|password");
        }

        String database = data[1];
        String userName = data[2];
        String password = data[3];

        databaseManager.connect(database, userName, password);
        message.write(String.format("Database connection '%s' was successful", database));
    }

    @Override
    public String formatCommand() {
        return "connect|database|user|password";
    }

    @Override
    public String depictionCommand() {
        return "Database connection";
    }
}
