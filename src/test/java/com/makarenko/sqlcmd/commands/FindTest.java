package com.makarenko.sqlcmd.commands;

import com.makarenko.sqlcmd.model.DatabaseManager;
import com.makarenko.sqlcmd.view.Message;
import com.makarenko.sqlcmd.view.MessageColor;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.fail;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class FindTest {
    private Message message;
    private DatabaseManager databaseManager;
    private Command command;
    private MessageColor messageColor;

    @Before
    public void setUp() {
        message = mock(Message.class);
        databaseManager = mock(DatabaseManager.class);
        command = new Find(databaseManager);
        messageColor = new MessageColor();
    }

    @Test
    public void testBeginCommand() {
        boolean beginCommand = command.beginCommand("find|");
        assertTrue(beginCommand);
    }

    @Test
    public void testBeginCommandError() {
        boolean beginCommandError = command.beginCommand("fand|");
        assertFalse(beginCommandError);
    }

    @Test
    public void testWithoutParameterCommand() {
        boolean beginCommandWithout = command.beginCommand("find");
        assertFalse(beginCommandWithout);
    }

    @Test
    public void testExecutionCommandSuccessful() {
        command.executionCommand("find|car");
        verify(databaseManager).findTable("car");
    }

    @Test
    public void testWithParameterCommandError() {
        try {
            command.executionCommand("find|");
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals(messageColor.getErrorMessage("find|") + "find|tableName", e.getMessage());
        }
    }

    @Test
    public void testFormatCommand() {
        String format = command.formatCommand();
        assertEquals("find|tableName", format);
    }

    @Test
    public void testDepictionCommand() {
        String depiction = command.depictionCommand();
        assertEquals("Data table output", depiction);
    }
}