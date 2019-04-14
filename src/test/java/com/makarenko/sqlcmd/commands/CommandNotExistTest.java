package com.makarenko.sqlcmd.commands;

import com.makarenko.sqlcmd.view.Message;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class CommandNotExistTest {
    private Message message;
    private Command command;

    @Before
    public void setUp() {
        message = mock(Message.class);
        command = new CommandNotExist(message);
    }

    @Test
    public void testBeginCommand() {
        boolean beginCommand = command.beginCommand("");
        assertTrue(beginCommand);
    }

    @Test
    public void testExecutionCommand() {
        command.executionCommand("isNotUnsupportedCommand");
        verify(message).write(message.getColorRed() +
                "Nonexistent command: 'isNotUnsupportedCommand'" + message.getColorReset());
    }
}