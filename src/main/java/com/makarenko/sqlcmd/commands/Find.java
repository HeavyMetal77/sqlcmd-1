package com.makarenko.sqlcmd.commands;

import com.makarenko.sqlcmd.model.DatabaseManager;
import com.makarenko.sqlcmd.view.Message;
import java.sql.SQLException;

public class Find implements Command {
    private Message message;
    private DatabaseManager databaseManager;

    public Find(Message message, DatabaseManager databaseManager) {
        this.message = message;
        this.databaseManager = databaseManager;
    }

    @Override
    public boolean beginCommand(String command) {
        return command.startsWith("find|");
    }

    @Override
    public void executionCommand(String command) {
        String[] data = command.split("\\|");
        if (!isCorrectCommand(command, data)) {
            return;
        }

        String tableName = data[1];

        try {
            databaseManager.findTable(tableName);
        } catch (SQLException e) {
            message.write(String.format(
                    "Не удалось отобразить таблицу '%s' по причине '%s'", tableName, e.getMessage()));
        }
    }

    @Override
    public String formatCommand() {
        return "find|tableName";
    }

    @Override
    public String depictionCommand() {
        return "Вывод таблицы с данными.";
    }

    private boolean isCorrectCommand(String command, String data[]) {
        if (data.length != 2) {
            message.write(String.format("Вы неверно ввели команду '%s', а должно быть find|tableName", command));
            return false;
        }
        return true;
    }
}
