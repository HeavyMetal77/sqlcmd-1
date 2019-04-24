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

public class UpdateTest {
    private Message message;
    private DatabaseManager databaseManager;
    private Command command;
    private MessageColor messageColor;

    @Before
    public void setUp() {
        message = mock(Message.class);
        databaseManager = mock(DatabaseManager.class);
        command = new Update(message, databaseManager);
        messageColor = new MessageColor();
    }

    @Test
    public void testBeginCommand() {
        boolean beginCommand = command.beginCommand("update|");
        assertTrue(beginCommand);
    }

    @Test
    public void testBeginCommandError() {
        boolean beginCommandError = command.beginCommand("updatte|");
        assertFalse(beginCommandError);
    }

    @Test
    public void testWithoutParameterCommand() {
        boolean withoutParameter = command.beginCommand("update");
        assertFalse(withoutParameter);
    }

    @Test
    public void testExecutionCommandSuccessful() {
        command.executionCommand("update|car|id|1|name|sens");
        String tableName = "car";
        String keyName = "id";
        String keyValue = "1";
        Map<String, Object> columnData = new LinkedHashMap<>();
        columnData.put("name", "sens");

        verify(databaseManager).update(tableName, keyName, keyValue, columnData);
        verify(message).writeln("All records successfully updated");
    }

    @Test
    public void testExecutionCommandErrorParameters() {
        try {
            command.executionCommand("update|car|");
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals(messageColor.getErrorMessage("update|car|") +
                    "update|tableName|primaryColumnName|primaryColumnValue|getColumnName1|" +
                    "SetColumnNewValue1|...|getColumnNameN|SetColumnNewValueN", e.getMessage());
        }
    }

    @Test
    public void testFormatCommand() {
        String formatCommand = command.formatCommand();
        assertEquals("update|tableName|primaryColumnName|primaryColumnValue|getColumnName1|" +
                "SetColumnNewValue1|...|getColumnNameN|SetColumnNewValueN", formatCommand);
    }

    @Test
    public void testDepictionCommand() {
        String depictionCommand = command.depictionCommand();
        assertEquals("Record update", depictionCommand);
    }
}