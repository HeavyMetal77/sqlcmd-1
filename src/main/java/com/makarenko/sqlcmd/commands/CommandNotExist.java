package com.makarenko.sqlcmd.commands;

import com.makarenko.sqlcmd.view.Message;
import com.makarenko.sqlcmd.view.MessageColor;

public class CommandNotExist extends NullFormat implements Command {
    private Message message;
    private MessageColor messageColor = new MessageColor();

    public CommandNotExist(Message message) {
        this.message = message;
    }

    @Override
    public boolean beginCommand(String command) {
        return true;
    }

    public void executionCommand(String command) {
        message.write(messageColor.getErrorNotExist(command));
    }
}
