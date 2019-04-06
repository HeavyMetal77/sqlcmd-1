package com.makarenko.sqlcmd.controller;

import com.makarenko.sqlcmd.commands.*;
import com.makarenko.sqlcmd.model.DatabaseManager;
import com.makarenko.sqlcmd.view.Message;

public class Controller {
    private Command[] commands;
    private Message message;

    public Controller(Message message, DatabaseManager databaseManager) {
        this.message = message;
        this.commands = new Command[] {
                new CreateTable(message, databaseManager),
                new Exit(message),
                new Connect(databaseManager, message),
                new isConnected(databaseManager, message)
        };
    }

    public void run() {
        message.write("Добро пожаловать в программу 'SQLCMD'");

        try {
            while (true) {
                message.write("Введите команду или help для помощи");
                String input = message.read();
                if (input == null) {
                    new Exit(message).beginCommand(input);
                }

                for (Command command : commands) {
                    if (command.beginCommand(input)) {
                        command.executionCommand(input);
                        break;
                    }
                }
            }
        } catch (ExitException e) {
            e.getMessage();
        }
    }
}
