package com.makarenko.sqlcmd.commands;

import com.makarenko.sqlcmd.model.DatabaseManager;
import com.makarenko.sqlcmd.view.Message;
import com.makarenko.sqlcmd.view.MessageColor;

public class Delete implements Command {
    private Message message;
    private DatabaseManager databaseManager;
    private MessageColor messageColor = new MessageColor();

    public Delete(Message message, DatabaseManager databaseManager) {
        this.message = message;
        this.databaseManager = databaseManager;
    }

    @Override
    public boolean beginCommand(String command) {
        return command.startsWith("delete|");
    }

    @Override
    public void executionCommand(String command) {
        String[] data = command.split("\\|");
        if (data.length != 4) {
            throw new IllegalArgumentException(messageColor.getErrorMessage(command)
                    + "delete|tableName|columnName|columnValue");
        }

        String tableName = data[1];
        String columnName = data[2];
        String columnValue = data[3];

        databaseManager.delete(tableName, columnName, columnValue);
        message.writeln("Record successfully deleted");
    }

    @Override
    public String formatCommand() {
        return "delete|tableName|columnName|columnValue";
    }

    @Override
    public String depictionCommand() {
        return "Delete record";
    }
}
