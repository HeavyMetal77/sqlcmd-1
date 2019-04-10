package com.makarenko.sqlcmd.commands;

import com.makarenko.sqlcmd.model.DatabaseManager;
import com.makarenko.sqlcmd.view.Message;

public class Delete implements Command {
    private Message message;
    private DatabaseManager databaseManager;

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
            throw new IllegalArgumentException(String.format("Вы неверно ввели команду '%s', " +
                    "а должно быть delete|tableName|columnName|columnValue", command));
        }

        String tableName = data[1];
        String columnName = data[2];
        String columnValue = data[3];

        databaseManager.delete(tableName, columnName, columnValue);
        message.write("Запись успешно удалена.");
    }

    @Override
    public String formatCommand() {
        return "delete|tableName|columnName|columnValue";
    }

    @Override
    public String depictionCommand() {
        return "Удаление записи";
    }
}
