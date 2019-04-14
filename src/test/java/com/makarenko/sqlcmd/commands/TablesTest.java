package com.makarenko.sqlcmd.commands;

import com.makarenko.sqlcmd.model.DatabaseManager;
import com.makarenko.sqlcmd.view.Message;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class TablesTest {
    private Message message;
    private DatabaseManager databaseManager;
    private Command command;

    @Before
    public void setUp() {
        message = mock(Message.class);
        databaseManager = mock(DatabaseManager.class);
        command = new Tables(message, databaseManager);
    }

    @Test
    public void testBeginCommand() {
        boolean beginCommand = command.beginCommand("tables");
        assertTrue(beginCommand);
    }

    @Test
    public void testBeginCommandError() {
        boolean beginCommandError = command.beginCommand("tabes");
        assertFalse(beginCommandError);
    }

    @Test
    public void executionCommand() {
        command.executionCommand("tables");
        verify(databaseManager).listTables();
    }

    @Test
    public void testFormatCommand() {
        String formatCommand = command.formatCommand();
        assertEquals("tables", formatCommand);
    }

    @Test
    public void testDepictionCommand() {
        String depictionCommand = command.depictionCommand();
        assertEquals("Список существующих таблиц в базе данных.", depictionCommand);
    }
}