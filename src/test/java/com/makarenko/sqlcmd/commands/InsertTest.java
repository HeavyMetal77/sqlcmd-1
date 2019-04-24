package com.makarenko.sqlcmd.commands;

import com.makarenko.sqlcmd.model.DatabaseManager;
import com.makarenko.sqlcmd.view.Message;
import com.makarenko.sqlcmd.view.MessageColor;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedHashMap;
import java.util.Map;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class InsertTest {
    private Message message;
    private DatabaseManager databaseManager;
    private Command command;
    private MessageColor messageColor;

    @Before
    public void setUp() {
        message = mock(Message.class);
        databaseManager = mock(DatabaseManager.class);
        command = new Insert(message, databaseManager);
        messageColor = new MessageColor();
    }

    @Test
    public void testBeginCommand() {
        boolean beginCommand = command.beginCommand("insert|");
        assertTrue(beginCommand);
    }

    @Test
    public void testBeginCommandError() {
        boolean beginCommandError = command.beginCommand("insssert|");
        assertFalse(beginCommandError);
    }

    @Test
    public void testWithoutParameterCommand() {
        boolean beginCommandError = command.beginCommand("insert");
        assertFalse(beginCommandError);
    }

    @Test
    public void testExecutionCommandSuccessful() {
        String tableName = "car";
        Map<String, Object> row = new LinkedHashMap<>();
        row.put("id", "3");
        row.put("name", "lada");

        command.executionCommand("insert|car|id|3|name|lada");
        verify(databaseManager).insert(tableName, row);
        verify(message).writeln(String.format("Records successfully added to the 'car'", tableName));
    }

    @Test
    public void testExecutionCommandErrorParameters() {
        try {
            command.executionCommand("insert|car|");
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals(messageColor.getErrorMessage("insert|car|") +
                    "insert|tableName|columnName1|columnValue1|...|columnNameN|columnValueN", e.getMessage());
        }
    }

    @Test
    public void testFormatCommand() {
        String formatCommand = command.formatCommand();
        assertEquals("insert|tableName|columnName1|columnValue1|...|columnNameN|columnValueN", formatCommand);
    }

    @Test
    public void testDepictionCommand() {
        String depictionCommand = command.depictionCommand();
        assertEquals("Insert records in the table", depictionCommand);
    }
}