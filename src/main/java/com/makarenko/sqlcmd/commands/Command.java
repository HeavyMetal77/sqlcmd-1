package com.makarenko.sqlcmd.commands;

public interface Command {

    boolean beginCommand(String command);

    void executionCommand(String command);

    String formatCommand();

    String depictionCommand();
}
