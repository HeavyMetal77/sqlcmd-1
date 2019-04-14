package com.makarenko.sqlcmd.commands;

import com.makarenko.sqlcmd.model.DatabaseManager;
import com.makarenko.sqlcmd.view.Message;

public class Clear implements Command {
    private Message message;
    private DatabaseManager databaseManager;

    public Clear(Message message, DatabaseManager databaseManager) {
        this.message = message;
        this.databaseManager = databaseManager;
    }

    @Override
    public boolean beginCommand(String command) {
        return command.startsWith("clear|");
    }

    @Override
    public void executionCommand(String command) {
        String data[] = command.split("\\|");
        if (data.length != 2) {
            throw new IllegalArgumentException(String.format(message.getColorRed() +
                    "This command '%s' wrong, should be: clear|tableName" + message.getColorReset(), command));
        }

        String tableName = data[1];
        if (!confirmed(tableName)) {
            return;
        }

        databaseManager.clearTable(tableName);
        message.write(String.format("Table '%s' successfully cleared", tableName));
    }

    @Override
    public String formatCommand() {
        return "clear|tableName";
    }

    @Override
    public String depictionCommand() {
        return "Table cleaning";
    }

    private boolean confirmed(String tableName) {
        message.write(String.format("You want to clear the table '%s'? " +
                "Enter the name of the table to confirm", tableName));
        String verification = message.read();
        if (verification.equals(tableName)) {
            return true;
        }
        message.write("cleaning canceled");
        return false;
    }
}
