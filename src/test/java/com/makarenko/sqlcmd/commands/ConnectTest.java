package com.makarenko.sqlcmd.commands;

import com.makarenko.sqlcmd.model.DatabaseManager;
import com.makarenko.sqlcmd.view.Message;
import com.makarenko.sqlcmd.view.MessageColor;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class ConnectTest {
    private Message message;
    private DatabaseManager databaseManager;
    private Command command;

    @Before
    public void setUp() {
        message = mock(Message.class);
        databaseManager = mock(DatabaseManager.class);
        command = new Connect(databaseManager, message);
    }

    @Test
    public void testBeginCommand() {
        boolean beginCommand = command.beginCommand("connect|");
        assertTrue(beginCommand);
    }

    @Test
    public void testBeginCommandError() {
        boolean beginCommand = command.beginCommand("connec|");
        assertFalse(beginCommand);
    }

    @Test
    public void testBeginCommandWithoutParameter() {
        boolean beginCommand = command.beginCommand("connect");
        assertFalse(beginCommand);
    }

    @Test
    public void testExecutionCommandSuccessful() {
        command.executionCommand("connect|sqlcmd|postgres|postgres");
        verify(message).write("Database connection 'sqlcmd' was successful");
    }

    @Test
    public void testExecutionCommandCountParameter() {
        MessageColor messageColor = new MessageColor();
        try {
            command.executionCommand("connect|sqlcmd|postgres");
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals(messageColor.getErrorMessage("connect|sqlcmd|postgres")
                            + "connect|database|user|password"
                    , e.getMessage());
        }
    }

    @Test
    public void testFormatCommand() {
        String format = command.formatCommand();
        assertEquals(format, "connect|database|user|password");
    }

    @Test
    public void testDepictionCommand() {
        String depiction = command.depictionCommand();
        assertEquals(depiction, "Database connection");
    }
}
