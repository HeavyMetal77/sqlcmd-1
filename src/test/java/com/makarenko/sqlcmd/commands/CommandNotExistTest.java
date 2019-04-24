package com.makarenko.sqlcmd.commands;

import com.makarenko.sqlcmd.view.Message;
import com.makarenko.sqlcmd.view.MessageColor;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class CommandNotExistTest {
    private Message message;
    private Command command;
    private MessageColor messageColor;

    @Before
    public void setUp() {
        message = mock(Message.class);
        command = new CommandNotExist(message);
        messageColor = new MessageColor();
    }

    @Test
    public void testBeginCommand() {
        boolean beginCommand = command.beginCommand("");
        assertTrue(beginCommand);
    }

    @Test
    public void testExecutionCommand() {
        command.executionCommand("is");
        verify(message).writeln(messageColor.getErrorNotExist("is"));
    }
}