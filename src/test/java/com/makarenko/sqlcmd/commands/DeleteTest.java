package com.makarenko.sqlcmd.commands;

import com.makarenko.sqlcmd.model.DatabaseManager;
import com.makarenko.sqlcmd.view.Message;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.fail;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class DeleteTest {

    private Message message;
    private DatabaseManager databaseManager;
    private Command command;

    @Before
    public void setUp() {
        message = mock(Message.class);
        databaseManager = mock(DatabaseManager.class);
        command = new Delete(message, databaseManager);
    }

    @Test
    public void testBeginCommand() {
        boolean delete = command.beginCommand("delete|");
        assertTrue(delete);
    }

    @Test
    public void testBeginCommandError() {
        boolean delete = command.beginCommand("delette|");
        assertFalse(delete);
    }

    @Test
    public void testWithoutParameterCommand() {
        boolean delete = command.beginCommand("delete");
        assertFalse(delete);
    }

    @Test
    public void testExecutionCommandSuccessful() {
        String tableName = "car";
        String columnName = "id";
        String columnValue = "1";

        command.executionCommand("delete|car|id|1");
        verify(databaseManager).delete(tableName, columnName, columnValue);
        verify(message).write("Record successfully deleted");
    }

    @Test
    public void testWithParameterCommandError() {
        try {
            command.executionCommand("delete|");
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals(verify(message).getColorRed() + "This command 'delete|' wrong, " +
                    "should be: delete|tableName|columnName|columnValue" + verify(message).getColorReset()
                    , e.getMessage());
        }
    }

    @Test
    public void formatCommand() {
        String format = command.formatCommand();
        assertEquals("delete|tableName|columnName|columnValue", format);
    }

    @Test
    public void depictionCommand() {
        String depiction = command.depictionCommand();
        assertEquals("Delete record", depiction);
    }
}