package com.makarenko.sqlcmd.commands;

import com.makarenko.sqlcmd.model.DatabaseManager;
import com.makarenko.sqlcmd.view.Message;

import java.sql.SQLException;

public class Delete implements Command {
    Message message;
    DatabaseManager databaseManager;

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
        if (!isCorrectCommand(command, data)) {
            return;
        }

        String tableName = data[1];
        String columnName = data[2];
        String columnValue = data[3];

        try {
            databaseManager.delete(tableName, columnName, columnValue);
            message.write("Запись успешно удалена.");
        } catch (SQLException e) {
            message.write(String.format("Не удалось удалить запись в таблице '%s' по причине '%s'",
                    tableName, e.getMessage()));
        }
    }

    @Override
    public String formatCommand() {
        return "delete|tableName|columnName|columnValue";
    }

    @Override
    public String depictionCommand() {
        return "Удаление записи";
    }

    private boolean isCorrectCommand(String command, String data[]) {
        if (data.length != 4) {
            message.write(String.format("Вы неверно ввели команду '%s', " +
                    "а должно быть delete|tableName|columnName|columnValue", command));
            return false;
        }
        return true;
    }
}
