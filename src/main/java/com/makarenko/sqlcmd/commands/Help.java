package com.makarenko.sqlcmd.commands;

import com.makarenko.sqlcmd.view.Message;

public class Help extends NullFormat implements Command {
    private Message message;
    private Command[] commands;

    public Help(Message message) {
        this.message = message;
    }

    public void setCommands(Command[] commands) {
        this.commands = commands;
    }

    @Override
    public boolean beginCommand(String command) {
        return command.equals("help");
    }

    @Override
    public void executionCommand(String command) {
        if (!command.equals("help")) {
            throw new IllegalArgumentException(String.format("Такой команды не существует '%s'", command));
        }

        for (Command comm : commands) {
            if(comm.formatCommand() != null) {
                message.write(comm.formatCommand());
                message.write("\t" + comm.depictionCommand() + "\n");
            }
        }
    }
}
