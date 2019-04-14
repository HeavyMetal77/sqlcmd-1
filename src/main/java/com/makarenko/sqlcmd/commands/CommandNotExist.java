package com.makarenko.sqlcmd.commands;

import com.makarenko.sqlcmd.view.Message;

public class CommandNotExist extends NullFormat implements Command {
    private Message message;

    public CommandNotExist(Message message) {
        this.message = message;
    }

    @Override
    public boolean beginCommand(String command) {
        return true;
    }

    @Override
    public void executionCommand(String command) {
        message.write(String.format(message.getColorRed() +
                "Nonexistent command: '%s'" + message.getColorReset(), command));
    }
}
