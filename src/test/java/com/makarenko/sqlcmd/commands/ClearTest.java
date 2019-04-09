package com.makarenko.sqlcmd.commands;

import com.makarenko.sqlcmd.model.DatabaseManager;
import com.makarenko.sqlcmd.view.Message;
import org.junit.Before;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.sql.SQLException;

import static junit.framework.TestCase.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ClearTest {
    private Message message;
    private DatabaseManager databaseManager;
    private Command command;

    @Before
    public void setUp() {
        message = mock(Message.class);
        databaseManager = mock(DatabaseManager.class);
        command = new Clear(message, databaseManager);
    }

    @Test
    public void testClearTableSuccessful() throws SQLException {
        when(message.read()).thenReturn("car");
        command.executionCommand("clear|car");
        verify(message).write("Вы собираетесь очистить таблицу 'car'. Введите название таблицы для подтверждения");
        verify(databaseManager).clearTable("car");
        verify(message).write("Таблица 'car' успешно очищена");
    }


    @Test
    public void testClearTableNotSuccessful() {
        when(message.read()).thenReturn("d");
        command.executionCommand("clear|car");
        verify(message).write("Вы собираетесь очистить таблицу 'car'. Введите название таблицы для подтверждения");
        verify(message).write("Очистка отменена");
    }


    @Test
    public void testBeginCommandClear() {
        boolean beginCommand = command.beginCommand("clear|");
        assertTrue(beginCommand);
    }

    @Test
    public void testBeginCommandErrorClear() {
        boolean beginCommandError = command.beginCommand("clqear|");
        assertFalse(beginCommandError);
    }

    @Test
    public void testWithoutParameterCommandClear() {
        boolean commandWithout = command.beginCommand("clear");
        assertFalse(commandWithout);
    }

    @Test
    public void testWithParameterCommandErrorClear() {
        try {
            command.executionCommand("clear|book|qw");
        } catch (IllegalArgumentException e) {
            assertEquals("Вы неверно ввели команду 'clear|book|qw', а должно быть clear|tableName", e.getMessage());
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
        assertEquals(formatCommand, "Очистка таблицы");
    }

    @Test
    public void testClearException() {
        when(message.read()).thenReturn("home");
        command.executionCommand("clear|home");
        verify(message).write("Вы собираетесь очистить таблицу 'home'. Введите название таблицы для подтверждения");
        try {
            verify(databaseManager).clearTable("home");
        } catch (Exception e) {
            verify(message).write("Неудача! по причине: ERROR: relation \"home\" does not exist\n" +
                    "  Позиция: 13");
        }
    }
}