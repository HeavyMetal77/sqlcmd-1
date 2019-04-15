package com.makarenko.sqlcmd.commands;

import com.makarenko.sqlcmd.model.DatabaseManager;
import com.makarenko.sqlcmd.view.Message;
import com.makarenko.sqlcmd.view.MessageColor;

public class isConnected extends NullFormat implements Command {
    private DatabaseManager databaseManager;
    private Message message;
    private MessageColor messageColor = new MessageColor();

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
        message.write(messageColor.getNotConnect(command));
    }
}
