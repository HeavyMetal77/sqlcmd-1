package com.makarenko.sqlcmd.commands;

import com.makarenko.sqlcmd.view.Message;
import com.makarenko.sqlcmd.view.MessageColor;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class HelpTest {
    private Message message;
    private Command command;
    private MessageColor messageColor;

    @Before
    public void setUp() {
        message = mock(Message.class);
        command = new Help(message);
        messageColor = new MessageColor();
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
            assertEquals(messageColor.getErrorNotExist("helpp"), e.getMessage());
        }
    }

    @Test
    public void testExecutionCommand() {
        command.executionCommand("help");
    }
}