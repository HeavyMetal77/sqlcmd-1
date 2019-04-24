package com.makarenko.sqlcmd.commands;

import com.makarenko.sqlcmd.model.DatabaseManager;
import com.makarenko.sqlcmd.view.Message;
import com.makarenko.sqlcmd.view.MessageColor;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.*;
import static org.mockito.Mockito.*;

public class ClearTest {
    private Message message;
    private DatabaseManager databaseManager;
    private Command command;
    private MessageColor messageColor;

    @Before
    public void setUp() {
        message = mock(Message.class);
        databaseManager = mock(DatabaseManager.class);
        command = new Clear(message, databaseManager);
        messageColor = new MessageColor();
    }

    @Test
    public void testClearTableSuccessful() {
        when(message.read()).thenReturn("car");
        command.executionCommand("clear|car");
        verify(message).writeln("You want to clear the table 'car'? Enter the name of the table to confirm");
        verify(databaseManager).clearTable("car");
        verify(message).writeln("Table 'car' successfully cleared");
    }


    @Test
    public void testClearTableNotSuccessful() {
        when(message.read()).thenReturn("d");
        command.executionCommand("clear|car");
        verify(message).writeln("You want to clear the table 'car'? Enter the name of the table to confirm");
        verify(message).writeln("cleaning canceled");
    }


    @Test
    public void testBeginCommand() {
        boolean beginCommand = command.beginCommand("clear|");
        assertTrue(beginCommand);
    }

    @Test
    public void testBeginCommandError() {
        boolean beginCommandError = command.beginCommand("clqear|");
        assertFalse(beginCommandError);
    }

    @Test
    public void testWithoutParameterCommand() {
        boolean commandWithout = command.beginCommand("clear");
        assertFalse(commandWithout);
    }

    @Test
    public void testWithParameterCommandError() {
        try {
            command.executionCommand("clear|");
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals(messageColor.getErrorMessage("clear|") + "clear|tableName", e.getMessage());
        }
    }

    @Test
    public void testFormatCommand() {
        String formatCommand = command.formatCommand();
        assertEquals(formatCommand, "clear|tableName");
    }

    @Test
    public void testDepictionCommand() {
        String formatCommand = command.depictionCommand();
        assertEquals(formatCommand, "Table cleaning");
    }
}