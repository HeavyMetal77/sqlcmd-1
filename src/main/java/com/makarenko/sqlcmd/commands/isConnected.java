package com.makarenko.sqlcmd.commands;

import com.makarenko.sqlcmd.model.DatabaseManager;
import com.makarenko.sqlcmd.view.Message;

public class isConnected implements Command {
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
        message.write(String.format("Вы не можете воспользоваться этой командой '%s' " +
                "пока не подключились к базе даных", command));
    }

    @Override
    public String formatCommand() {
        return null;
    }

    @Override
    public String depictionCommand() {
        return null;
    }
}
