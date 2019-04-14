package com.makarenko.sqlcmd.commands;

import com.makarenko.sqlcmd.model.DatabaseManager;
import com.makarenko.sqlcmd.view.Message;
import java.util.LinkedHashMap;
import java.util.Map;

public class Insert implements Command {
    private Message message;
    private DatabaseManager databaseManager;

    public Insert(Message message, DatabaseManager databaseManager) {
        this.message = message;
        this.databaseManager = databaseManager;
    }

    @Override
    public boolean beginCommand(String command) {
        return command.startsWith("insert|");
    }

    @Override
    public void executionCommand(String command) {
        String[] data = command.split("\\|");
        if (data.length < 3 || data.length % 2 == 1) {
            throw new IllegalArgumentException(String.format("Вы неверно ввели команду '%s', " +
                    "а должно быть insert|tableName|columnName1|columnValue1|...|columnNameN|columnValueN", command));
        }

        String tableName = data[1];
        Map<String, Object> row = new LinkedHashMap<>();
        for (int i = 2; i < data.length; i += 2) {
            row.put(data[i], data[i + 1]);
        }

        databaseManager.insert(tableName, row);
        message.write(String.format("В таблицу '%s' успешно добавлены записи", tableName));
    }

    @Override
    public String formatCommand() {
        return "insert|tableName|columnName1|columnValue1|...|columnNameN|columnValueN";
    }

    @Override
    public String depictionCommand() {
        return "Вставка данных в таблицу";
    }
}
