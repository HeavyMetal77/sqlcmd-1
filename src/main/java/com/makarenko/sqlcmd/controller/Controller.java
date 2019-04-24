package com.makarenko.sqlcmd.controller;

import com.makarenko.sqlcmd.commands.*;
import com.makarenko.sqlcmd.model.DatabaseManager;
import com.makarenko.sqlcmd.view.Message;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Controller {
    private List<Command> commands;
    private Message message;

    public Controller(Message message, DatabaseManager databaseManager) {
        this.message = message;
        this.commands = new ArrayList<>(Arrays.asList(
                new Connect(databaseManager, message),
                new Help(message),
                new Exit(message),
                new isConnected(databaseManager, message),
                new Tables(message, databaseManager),
                new Create(message, databaseManager),
                new Find(databaseManager),
                new Insert(message, databaseManager),
                new Update(message, databaseManager),
                new Delete(message, databaseManager),
                new Drop(message, databaseManager),
                new Clear(message, databaseManager),
                new CommandNotExist(message)
                ));
    }

    public void run() {
        try {
            message.writeln("Welcome to program 'SQLCMD'");
            message.writeln("To view commands enter: help");

            while (true) {
                String input = message.read();
                for (Command command : commands) {
                    try {
                        if (command.beginCommand(input)) {
                            command.executionCommand(input);
                            break;
                        }
                    } catch (RuntimeException e) {
                        if (e instanceof ExitException) throw e;
                        printError(e);
                        break;
                    }
                }
                message.writeln("Enter command (or help):");
            }
        } catch (ExitException e) {}
    }

    private void printError(RuntimeException e) {
        String error = e.getMessage();
        Throwable cause = e.getCause();
        if (cause != null) {
            error += " " + cause.getMessage();
        }
        message.writeln("Fail! because of: " + error);
    }
}
