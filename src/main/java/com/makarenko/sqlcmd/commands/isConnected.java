package com.makarenko.sqlcmd.commands;

import com.makarenko.sqlcmd.model.DatabaseManager;
import com.makarenko.sqlcmd.view.Message;

public class isConnected extends NullFormat implements Command {
    private DatabaseManager databaseManager;
    private Message message;

    public isConnected(DatabaseManager databaseManager, Message message) {
        this.databaseManager = databaseManager;
        this.message = message;
    }

    @Override
    public boolean beginCommand(String command) {
        return !databaseManager.isConnected();
    }

    @Override
    public void executionCommand(String command) {
        message.write("You cannot use commands. Connect to the database");
    }
}
