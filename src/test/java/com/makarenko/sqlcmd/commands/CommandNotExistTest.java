package com.makarenko.sqlcmd.commands;

import com.makarenko.sqlcmd.view.Message;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class CommandNotExistTest {
    Message message;
    Command command;

    @Before
    public void setUp() {
        message = mock(Message.class);
        command = new CommandNotExist(message);
    }

    @Test
    public void beginCommandTest() {
        boolean beginCommand = command.beginCommand("");
        assertTrue(beginCommand);
    }

    @Test
    public void executionCommandTest() {
        command.executionCommand("isNotUnsupportedCommand");
        verify(message).write("Такой команды не существует 'isNotUnsupportedCommand'");
    }
}