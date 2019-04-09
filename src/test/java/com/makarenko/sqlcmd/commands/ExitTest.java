package com.makarenko.sqlcmd.commands;

import com.makarenko.sqlcmd.view.Message;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.verify;

public class ExitTest {
    private Message message;
    private Command command;

    @Before
    public void setUo() {
        message = Mockito.mock(Message.class);
        command = new Exit(message);
    }

    @Test
    public void testExitTrue() {
        boolean exitTrue = command.beginCommand("exit");
        assertTrue(exitTrue);
    }

    @Test
    public void testExitFalse() {
        boolean exitFalse = command.beginCommand("qwe");
        assertFalse(exitFalse);
    }

    @Test
    public void testExitException() {
        try {
            command.executionCommand("exit");
            fail();
        } catch (ExitException e) {

        }
        verify(message).write("Вы вышли из программы, спасибо что воспользовались нашим продуктом");
    }

    @Test
    public void testFormatCommand() {
        String formatCommand = command.formatCommand();
        assertEquals(formatCommand, "exit");
    }

    @Test
    public void testDepictionCommand() {
        String formatCommand = command.depictionCommand();
        assertEquals(formatCommand, "Выход из программы");
    }
}