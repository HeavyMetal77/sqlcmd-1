package com.makarenko.sqlcmd.commands;

import com.makarenko.sqlcmd.model.DatabaseManager;
import com.makarenko.sqlcmd.view.Message;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Help extends NullFormat implements Command {
    private Message message;
    private List<Command> commands;

    public Help(Message message) {
        DatabaseManager databaseManager = null;
        this.message = message;
        this.commands = new ArrayList<>(Arrays.asList(
                new Connect(databaseManager, message),
                new Tables(message, databaseManager),
                new Create(message, databaseManager),
                new Find(databaseManager),
                new Insert(message, databaseManager),
                new Update(message, databaseManager),
                new Delete(message, databaseManager),
                new Drop(message, databaseManager),
                new Clear(message, databaseManager),
                new Exit(message)
        ));
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
