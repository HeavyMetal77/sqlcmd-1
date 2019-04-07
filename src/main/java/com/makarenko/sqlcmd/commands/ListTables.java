package com.makarenko.sqlcmd.commands;

import com.makarenko.sqlcmd.model.DatabaseManager;
import com.makarenko.sqlcmd.view.Message;

import java.sql.SQLException;
import java.util.Set;

public class ListTables implements Command {
    Message message;
    DatabaseManager databaseManager;

    public ListTables(Message message, DatabaseManager databaseManager) {
        this.message = message;
        this.databaseManager = databaseManager;
    }

    @Override
    public boolean beginCommand(String command) {
        return command.startsWith("tables");
    }

    @Override
    public void executionCommand(String command) {
        try {
            Set<String> tables = databaseManager.listTables();
            String list = tables.toString();
            message.write(list);
        } catch (SQLException e) {
            message.write(String.format("Не удалось вывести список всех таблиц по причине '%s'", e.getMessage()));
        }

    }

    @Override
    public String formatCommand() {
        return "tables";
    }

    @Override
    public String depictionCommand() {
        return "Список существующих таблиц в базе данных.";
    }
}
