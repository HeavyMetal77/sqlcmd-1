package com.makarenko.sqlcmd.commands;

import com.makarenko.sqlcmd.model.DatabaseManager;
import com.makarenko.sqlcmd.view.Message;

import java.util.Set;

public class Tables implements Command {
    private Message message;
    private DatabaseManager databaseManager;

    public Tables(Message message, DatabaseManager databaseManager) {
        this.message = message;
        this.databaseManager = databaseManager;
    }

    @Override
    public boolean beginCommand(String command) {
        return command.equals("tables");
    }

    @Override
    public void executionCommand(String command) {
        Set<String> tables = databaseManager.listTables();
        if (tables.size() == 0) {
            message.writeln("This database has no tables");
        }
        message.writeln(tables.toString().
                replace("[", "| ").
                replace("]", " |"));
    }

    @Override
    public String formatCommand() {
        return "tables";
    }

    @Override
    public String depictionCommand() {
        return "List of tables in the database";
    }
}
