package com.makarenko.sqlcmd.commands;

import com.makarenko.sqlcmd.model.DatabaseManager;
import com.makarenko.sqlcmd.view.Message;
import com.makarenko.sqlcmd.view.MessageColor;

import java.util.LinkedHashMap;
import java.util.Map;

public class Update implements Command {
    private Message message;
    private DatabaseManager databaseManager;
    private MessageColor messageColor = new MessageColor();

    public Update(Message message, DatabaseManager databaseManager) {
        this.message = message;
        this.databaseManager = databaseManager;
    }

    @Override
    public boolean beginCommand(String command) {
        return command.startsWith("update|");
    }

    @Override
    public void executionCommand(String command) {
        String[] data = command.split("\\|");
        if (data.length < 6 || data.length % 2 == 1) {
            throw new IllegalArgumentException(messageColor.getErrorMessage(command) +
                    "update|tableName|primaryColumnName|primaryColumnValue|getColumnName1|" +
                    "SetColumnNewValue1|...|getColumnNameN|SetColumnNewValueN");
        }

        String tableName = data[1];
        String keyName = data[2];
        String keyValue = data[3];
        Map<String, Object> columnData = new LinkedHashMap<>();
        for (int index = 4; index < data.length; index += 2) {
            columnData.put(data[index], data[index + 1]);
        }
        databaseManager.update(tableName, keyName, keyValue, columnData);
        message.write("All records successfully updated");
    }

    @Override
    public String formatCommand() {
        return "update|tableName|primaryColumnName|primaryColumnValue|getColumnName1|" +
                "SetColumnNewValue1|...|getColumnNameN|SetColumnNewValueN";
    }

    @Override
    public String depictionCommand() {
        return "Record update";
    }
}
