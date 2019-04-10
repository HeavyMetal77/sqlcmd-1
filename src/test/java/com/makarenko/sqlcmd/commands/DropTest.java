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
import static org.mockito.Mockito.when;

public class DropTest {
    private Message message;
    private DatabaseManager databaseManager;
    private Command command;

    @Before
    public void setUp() {
        message = mock(Message.class);
        databaseManager = mock(DatabaseManager.class);
        command = new Drop(message, databaseManager);
    }

    @Test
    public void testBeginCommand() {
        boolean beginCommand = command.beginCommand("drop|");
        assertTrue(beginCommand);
    }

    @Test
    public void testBeginCommandError() {
        boolean beginCommand = command.beginCommand("dropp|");
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
        verify(message).write("Вы собираетесь удалить таблицу 'car'. Введите название таблицы для подтверждения");
        verify(databaseManager).dropTable("car");
        verify(message).write("Таблица 'car' успешно удалена");
    }

    @Test
    public void testDropTableNotSuccessful() {
        when(message.read()).thenReturn("d");
        command.executionCommand("drop|car");
        verify(message).write("Вы собираетесь удалить таблицу 'car'. Введите название таблицы для подтверждения");
        verify(message).write("Удаление отменено");
    }

    @Test
    public void testWithParameterCommandError() {
        try {
            command.executionCommand("drop|");
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Вы неверно ввели команду 'drop|', а должно быть drop|tableName", e.getMessage());
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
        assertEquals("Удаление таблицы", depiction);
    }
}