package com.makarenko.sqlcmd.commands;

import com.makarenko.sqlcmd.model.DatabaseManager;

public class Find implements Command {
    private DatabaseManager databaseManager;

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
            throw new IllegalArgumentException(
                    String.format("Вы неверно ввели команду '%s', а должно быть find|tableName", command));
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
        return "Вывод таблицы с данными";
    }
}
