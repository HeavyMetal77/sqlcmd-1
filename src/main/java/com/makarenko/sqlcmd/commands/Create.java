package com.makarenko.sqlcmd.commands;

import com.makarenko.sqlcmd.model.DatabaseManager;
import com.makarenko.sqlcmd.view.Message;
import com.makarenko.sqlcmd.view.MessageColor;

import java.util.LinkedHashMap;
import java.util.Map;

public class Create implements Command {
    private Message message;
    private DatabaseManager databaseManager;
    private MessageColor messageColor = new MessageColor();

    public Create(Message message, DatabaseManager databaseManager) {
        this.message = message;
        this.databaseManager = databaseManager;
    }

    @Override
    public boolean beginCommand(String command) {
        return command.startsWith("create|");
    }

    @Override
    public void executionCommand(String command) {
        String[] data = command.split("\\|");
        if (data.length < 3 || data.length % 2 != 1) {
            throw new IllegalArgumentException(messageColor.getErrorMessage(command)
                    + "create|tableName|primaryKeyName|columnName1|columnValue1|....|columnNameN|columnValueN");
        }

        String tableName = data[1];
        String keyName = data[2];
        Map<String, Object> columns = new LinkedHashMap<>();
        for (int i = 3; i < data.length; i += 2) {
            columns.put(data[i], data[i + 1]);
        }

        databaseManager.createTable(tableName, keyName, columns);
        message.writeln(String.format("Table '%s' created successfully", tableName));
    }

    @Override
    public String formatCommand() {
        return "create|tableName|primaryKeyName|columnName1|columnValue1|....|columnNameN|columnValueN";
    }

    @Override
    public String depictionCommand() {
        return "Creating a table with data";
    }
}
