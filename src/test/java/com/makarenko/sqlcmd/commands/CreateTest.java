package com.makarenko.sqlcmd.commands;

import com.makarenko.sqlcmd.model.DatabaseManager;
import com.makarenko.sqlcmd.view.Message;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedHashMap;
import java.util.Map;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.fail;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class CreateTest {
    private Message message;
    private DatabaseManager databaseManager;
    private Command command;

    @Before
    public void setUp() {
        message = mock(Message.class);
        databaseManager = mock(DatabaseManager.class);
        command = new Create(message, databaseManager);
    }

    @Test
    public void testBeginCommand() {
        boolean beginCommand = command.beginCommand("create|");
        assertTrue(beginCommand);
    }

    @Test
    public void testBeginCommandError() {
        boolean beginCommand = command.beginCommand("creat|");
        assertFalse(beginCommand);
    }

    @Test
    public void testWithoutParameterCommand() {
        boolean beginCommand = command.beginCommand("create");
        assertFalse(beginCommand);
    }

    @Test
    public void testExecutionCommandSuccessful() {
        String tableName = "people";
        String keyName = "id";
        Map<String, Object> columns = new LinkedHashMap<>();
        columns.put("name" , "text");

        command.executionCommand("create|people|id|name|text");
        verify(databaseManager).createTable(tableName, keyName, columns);
        verify(message).write("Table 'people' created successfully");
    }

    @Test
    public void testExecutionCommandErrorParameters() {
        try {
            command.executionCommand("create|people");
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals(verify(message).getColorRed() + "This command 'create|people' wrong" +
                    ", should be: " +
                    "create|tableName|primaryKeyName|columnName1|columnValue1|" +
                    "....|columnNameN|columnValueN" + verify(message).getColorReset(), e.getMessage());
        }
    }

    @Test
    public void testFormatCommand() {
        String formatCommand = command.formatCommand();
        assertEquals("create|tableName|primaryKeyName|columnName1|columnValue1|" +
                "....|columnNameN|columnValueN", formatCommand);
    }

    @Test
    public void testDepictionCommand() {
        String depictionCommand = command.depictionCommand();
        assertEquals("Creating a table with data", depictionCommand);
    }
}