package com.makarenko.sqlcmd.commands;

import com.makarenko.sqlcmd.model.DatabaseManager;
import com.makarenko.sqlcmd.view.Message;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class isConnectedTest {
    private Message message;
    private DatabaseManager databaseManager;
    private Command command;

    @Before
    public void setUp() {
        message = mock(Message.class);
        databaseManager = mock(DatabaseManager.class);
        command = new isConnected(databaseManager, message);
    }

    @Test
    public void beginCommand() {
        command.beginCommand("connect|sqlcmd|postgres|postgres");
        verify(databaseManager).isConnected();
    }

    @Test
    public void executionCommand() {
        command.executionCommand("connect");
        verify(message).write("You cannot use commands. Connect to the database");
    }
}