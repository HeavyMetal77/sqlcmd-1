package com.makarenko.sqlcmd.commands;

import com.makarenko.sqlcmd.model.DatabaseManager;
import com.makarenko.sqlcmd.view.MessageColor;

public class Find implements Command {
    private DatabaseManager databaseManager;
    private MessageColor messageColor = new MessageColor();

    public Find(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    @Override
    public boolean beginCommand(String command) {
        return command.startsWith("find|");
    }

    @Override
    public void executionCommand(String command) {
        String[] data = command.split("\\|");
        if (data.length != 2) {
            throw new IllegalArgumentException(messageColor.getErrorMessage(command) + "find|tableName");
        }

        String tableName = data[1];
        databaseManager.findTable(tableName);
    }

    @Override
    public String formatCommand() {
        return "find|tableName";
    }

    @Override
    public String depictionCommand() {
        return "Data table output";
    }
}
