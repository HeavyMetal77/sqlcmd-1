package com.makarenko.sqlcmd.commands;

import com.makarenko.sqlcmd.model.DatabaseManager;
import com.makarenko.sqlcmd.view.Message;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

public class Update implements Command {
    private Message message;
    private DatabaseManager databaseManager;

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
        if (!isCorrectCommand(command, data)) {
            return;
        }

        String tableName = data[1];
        String keyName = data[2];
        String keyValue = data[3];
        Map<String, Object> columnData = new LinkedHashMap<>();
        for (int index = 4; index < data.length; index += 2) {
            columnData.put(data[index], data[index + 1]);
        }
        try {
            databaseManager.update(tableName, keyName, keyValue, columnData);
            message.write("Все записи успешно обновлены.");
        } catch (SQLException e) {
            message.write(String.format("Не удалось обновить по причине %s", e.getMessage()));
        }
    }

    @Override
    public String formatCommand() {
        return "update|tableName|primaryColumnName|primaryColumnValue|getColumnName1|" +
                "SetColumnNewValue1|...|getColumnNameN|SetColumnNewValueN";
    }

    @Override
    public String depictionCommand() {
        return "Обновление записи";
    }

    private boolean isCorrectCommand(String command, String data[]) {
        if (data.length < 6 || data.length % 2 == 1) {
            message.write(String.format("Вы неверно ввели команду '%s', а должно быть " +
                    "update|tableName|primaryColumnName|primaryColumnValue|getColumnName1|" +
                    "SetColumnNewValue1|...|getColumnNameN|SetColumnNewValueN", command));
            return false;
        }
        return true;
    }
}
