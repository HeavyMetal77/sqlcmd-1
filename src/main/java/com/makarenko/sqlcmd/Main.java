package com.makarenko.sqlcmd;

import com.makarenko.sqlcmd.controller.Controller;
import com.makarenko.sqlcmd.model.DatabaseManager;
import com.makarenko.sqlcmd.model.JDBCDatabaseManager;
import com.makarenko.sqlcmd.view.Console;
import com.makarenko.sqlcmd.view.Message;

public class Main {
    public static void main(String[] args) {
        Message message = new Console();
        DatabaseManager databaseManager = new JDBCDatabaseManager();

        Controller controller = new Controller(message, databaseManager);
        controller.run();
    }
}
