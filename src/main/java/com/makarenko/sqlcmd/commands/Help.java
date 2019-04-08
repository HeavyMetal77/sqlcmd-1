package com.makarenko.sqlcmd.commands;

import com.makarenko.sqlcmd.view.Message;

public class Help extends NullFormat implements Command {
    Message message;
    Command[] commands;

    public void setCommands(Command[] commands) {
        this.commands = commands;
    }

    public Help(Message message) {
        this.message = message;
    }

    @Override
    public boolean beginCommand(String command) {
        return command.startsWith("help");
    }

    @Override
    public void executionCommand(String command) {
        for (Command comm : commands) {
            if(comm.formatCommand() != null) {
                message.write(comm.formatCommand());
                message.write("\t" + comm.depictionCommand() + "\n");
            }
        }
    }
}
