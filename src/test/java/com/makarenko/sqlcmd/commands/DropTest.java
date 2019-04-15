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
import static org.mockito.Mockito.when;

public class DropTest {
    private Message message;
    private DatabaseManager databaseManager;
    private Command command;
    private MessageColor messageColor;

    @Before
    public void setUp() {
        message = mock(Message.class);
        databaseManager = mock(DatabaseManager.class);
        command = new Drop(message, databaseManager);
        messageColor = new MessageColor();
    }

    @Test
    public void testBeginCommand() {
        boolean beginCommand = command.beginCommand("drop|");
        assertTrue(beginCommand);
    }

    @Test
    public void testBeginCommandError() {
        boolean beginCommand = command.beginCommand("droop|");
        assertFalse(beginCommand);
    }

    @Test
    public void testWithoutParameterCommand() {
        boolean beginCommand = command.beginCommand("drop");
        assertFalse(beginCommand);
    }

    @Test
    public void testDropTableSuccessful() {
        when(message.read()).thenReturn("car");
        command.executionCommand("drop|car");
        verify(message).write("You want to delete the table 'car'? Enter the name of the table to confirm");
        verify(databaseManager).dropTable("car");
        verify(message).write("Table 'car' successfully deleted");
    }

    @Test
    public void testDropTableNotSuccessful() {
        when(message.read()).thenReturn("d");
        command.executionCommand("drop|car");
        verify(message).write("You want to delete the table 'car'? Enter the name of the table to confirm");
        verify(message).write("deletion canceled");
    }

    @Test
    public void testWithParameterCommandError() {
        try {
            command.executionCommand("drop|");
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals(messageColor.getErrorMessage("drop|")
                    + "drop|tableName", e.getMessage());
        }
    }

    @Test
    public void formatCommand() {
        String format = command.formatCommand();
        assertEquals("drop|tableName", format);
    }

    @Test
    public void depictionCommand() {
        String depiction = command.depictionCommand();
        assertEquals("Deleting a table", depiction);
    }
}