package com.makarenko.sqlcmd.controller;

import com.makarenko.sqlcmd.commands.*;
import com.makarenko.sqlcmd.model.DatabaseManager;
import com.makarenko.sqlcmd.view.Message;

public class Controller {
    private Command[] commands;
    private Message message;
    public Controller(Message message, DatabaseManager databaseManager) {
        Help help;
        this.message = message;
        this.commands = new Command[] {
                help = new Help(message),
                new Exit(message),
                new Connect(databaseManager, message),
                new isConnected(databaseManager, message),
                new List(message, databaseManager),
                new Create(message, databaseManager),
                new Find(message, databaseManager),
                new Insert(message, databaseManager),
                new Update(message, databaseManager),
                new Delete(message, databaseManager),
                new Drop(message, databaseManager),
                new Clear(message, databaseManager),
                new CommandNotExist(message),
        };
        help.setCommands(commands);
    }

    public void run() {
        message.write("Добро пожаловать в программу 'SQLCMD'");

        while (true) {
            String input = message.read();
            for (Command command : commands) {
                try {
                    if (command.beginCommand(input)) {
                        command.executionCommand(input);
                        break;
                    }
                } catch (Exception e) {
                    if (e instanceof ExitException) {
                        throw e;
                    }
                    printError(e);
                    break;
                }
            }
            message.write("Введи команду (или help для помощи):");
        }
    }

    private void printError(Exception e) {
        String error = e.getMessage();
        Throwable cause = e.getCause();
        if (cause != null) {
            error += " " + cause.getMessage();
        }
        message.write("Неудача! по причине: " + error);
    }
}
