package com.makarenko.sqlcmd.commands;

import com.makarenko.sqlcmd.view.Message;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class ExitTest {
    private Message message;
    private Command command;

    @Before
    public void setUp() {
        message = mock(Message.class);
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
        verify(message).write("Goodbye, thank you for using our product");
    }

    @Test
    public void testFormatCommand() {
        String formatCommand = command.formatCommand();
        assertEquals(formatCommand, "exit");
    }

    @Test
    public void testDepictionCommand() {
        String formatCommand = command.depictionCommand();
        assertEquals(formatCommand, "Exit from the program");
    }
}