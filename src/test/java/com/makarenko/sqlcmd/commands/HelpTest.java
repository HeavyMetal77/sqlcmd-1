package com.makarenko.sqlcmd.commands;

import com.makarenko.sqlcmd.view.Message;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class HelpTest {
    private Message message;
    private Command command;

    @Before
    public void setUp() {
        message = mock(Message.class);
        command = new Help(message);
    }

    @Test
    public void testBeginCommand() {
        boolean beginCommand = command.beginCommand("help");
        assertTrue(beginCommand);
    }

    @Test
    public void testBeginCommandError() {
        boolean beginCommandError = command.beginCommand("helpp");
        assertFalse(beginCommandError);
    }

    @Test
    public void testExecutionCommandError() {
        try {
            command.executionCommand("helpp");
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Такой команды не существует 'helpp'", e.getMessage());
        }
    }
}