package com.makarenko.sqlcmd.commands;

import com.makarenko.sqlcmd.model.DatabaseManager;
import com.makarenko.sqlcmd.view.Message;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class ClearTest {
    private Message message;
    private DatabaseManager databaseManager;

    @Before
    public void setUp() {
        message = Mockito.mock(Message.class);
        databaseManager = Mockito.mock(DatabaseManager.class);
    }

    @Test
    public void test() {
        Command command = new Clear(message, databaseManager);
    }

}