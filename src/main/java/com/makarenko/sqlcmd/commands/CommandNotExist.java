package com.makarenko.sqlcmd.commands;

import com.makarenko.sqlcmd.view.Message;

public class CommandNotExist implements Command {
    Message message;

    public CommandNotExist(Message message) {
        this.message = message;
    }

    @Override
    public boolean beginCommand(String command) {
        return true;
    }

    @Override
    public void executionCommand(String command) {
        message.write(String.format("Такой команды не существует '%s'", command));
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
