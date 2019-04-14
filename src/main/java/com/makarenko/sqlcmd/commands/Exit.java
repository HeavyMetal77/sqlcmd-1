package com.makarenko.sqlcmd.commands;

import com.makarenko.sqlcmd.view.Message;

public class Exit implements Command {
    private Message message;

    public Exit(Message message) {
        this.message = message;
    }

    @Override
    public boolean beginCommand(String command) {
        return command.startsWith("exit");
    }

    @Override
    public void executionCommand(String command) {
        message.write("Goodbye, thank you for using our product");
        throw new ExitException();
    }

    @Override
    public String formatCommand() {
        return "exit";
    }

    @Override
    public String depictionCommand() {
        return "Exit from the program";
    }
}
