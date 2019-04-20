package com.makarenko.sqlcmd.integration;

import com.makarenko.sqlcmd.Main;
import org.junit.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

import static org.junit.Assert.assertEquals;

public class IntegrationTest {
    private static ConfigInputStream inputStream;
    private static ByteArrayOutputStream outputStream;

    @BeforeClass
    public static void setUp() {
        inputStream = new ConfigInputStream();
        outputStream = new ByteArrayOutputStream();

        System.setIn(inputStream);
        System.setOut(new PrintStream(outputStream));
    }

    @Before
    public void clearIn() {
        inputStream.reset();
    }

    @Test
    public void testExit() {
        inputStream.add("exit");

        Main.main(new String[0]);

        assertEquals("Welcome to program 'SQLCMD'\n" +
                "To view commands enter: help\n" +
                //help
                "Goodbye, thank you for using our product\n", getData());
    }

    @Test
    public void testHelpExit() {
        inputStream.add("help");
        inputStream.add("exit");

        Main.main(new String[0]);

        assertEquals("Welcome to program 'SQLCMD'\n" +
                "To view commands enter: help\n" +
                //help
                "connect|database|user|password\n" +
                "\tDatabase connection\n" +
                "\n" +
                "tables\n" +
                "\tList of tables in the database\n" +
                "\n" +
                "create|tableName|primaryKeyName|columnName1|columnValue1|....|columnNameN|columnValueN\n" +
                "\tCreating a table with data\n" +
                "\n" +
                "find|tableName\n" +
                "\tData table output\n" +
                "\n" +
                "insert|tableName|columnName1|columnValue1|...|columnNameN|columnValueN\n" +
                "\tInsert records in the table\n" +
                "\n" +
                "update|tableName|primaryColumnName|primaryColumnValue|getColumnName1|SetColumnNewValue1|...|getColumnNameN|SetColumnNewValueN\n" +
                "\tRecord update\n" +
                "\n" +
                "delete|tableName|columnName|columnValue\n" +
                "\tDelete record\n" +
                "\n" +
                "drop|tableName\n" +
                "\tDeleting a table\n" +
                "\n" +
                "clear|tableName\n" +
                "\tTable cleaning\n" +
                "\n" +
                "exit\n" +
                "\tExit from the program\n" +
                "\n" +
                "Enter command (or help):\n" +
                //exit
                "Goodbye, thank you for using our product\n", getData());
    }

    @Test
    public void testUnsupportedWithoutConnect() {
        inputStream.add("unsupported");
        inputStream.add("exit");

        Main.main(new String[0]);

        assertEquals("Welcome to program 'SQLCMD'\n" +
                "To view commands enter: help\n" +
                //unsupported
                "\u001B[31mYou cannot use commands. Connect to the database\u001B[0m\n" +
                "Enter command (or help):\n" +
                //exit
                "Goodbye, thank you for using our product\n", getData());
    }

    @Test
    public void testConnect() {
        inputStream.add("connect|sqlcmd|postgres|postgres");
        inputStream.add("exit");

        Main.main(new String[0]);

        assertEquals("Welcome to program 'SQLCMD'\n" +
                "To view commands enter: help\n" +
                //connect|sqlcmd|postgres|postgres
                "Database connection 'sqlcmd' was successful\n" +
                "Enter command (or help):\n" +
                //exit
                "Goodbye, thank you for using our product\n", getData());
    }

    @Test
    public void testTables() {
        inputStream.add("connect|sqlcmd|postgres|postgres");
        inputStream.add("tables");
        inputStream.add("exit");

        Main.main(new String[0]);

        assertEquals("Welcome to program 'SQLCMD'\n" +
                "To view commands enter: help\n" +
                //connect|sqlcmd|postgres|postgres
                "Database connection 'sqlcmd' was successful\n" +
                "Enter command (or help):\n" +
                //tables
                "| book, car |\n" +
                "Enter command (or help):\n" +
                //exit
                "Goodbye, thank you for using our product\n", getData());
    }

    @Test
    public void testUnsupportedWithConnect() {
        inputStream.add("connect|sqlcmd|postgres|postgres");
        inputStream.add("unsupported");
        inputStream.add("exit");

        Main.main(new String[0]);

        assertEquals("Welcome to program 'SQLCMD'\n" +
                "To view commands enter: help\n" +
                //connect|sqlcmd|postgres|postgres
                "Database connection 'sqlcmd' was successful\n" +
                "Enter command (or help):\n" +
                //unsupported
                "\u001B[31mNonexistent command: 'unsupported'\u001B[0m\n" +
                "Enter command (or help):\n" +
                //exit
                "Goodbye, thank you for using our product\n", getData());
    }

    @Test
    public void testCreateDrop() {
        inputStream.add("connect|sqlcmd|postgres|postgres");
        inputStream.add("create|people|id|name|text|password|text");
        inputStream.add("drop|people");
        inputStream.add("people");
        inputStream.add("exit");

        Main.main(new String[0]);

        assertEquals("Welcome to program 'SQLCMD'\n" +
                "To view commands enter: help\n" +
                //connect|sqlcmd|postgres|postgres
                "Database connection 'sqlcmd' was successful\n" +
                "Enter command (or help):\n" +
                //create|people|id|name|text|password|text
                "Table 'people' created successfully\n" +
                "Enter command (or help):\n" +
                //drop|people
                "You want to delete the table 'people'? Enter the name of the table to confirm\n" +
                //people
                "Table 'people' successfully deleted\n" +
                "Enter command (or help):\n" +
                //exit
                "Goodbye, thank you for using our product\n", getData());
    }

    @Test
    public void testCreateInsertDrop() {
        inputStream.add("connect|sqlcmd|postgres|postgres");
        inputStream.add("create|people|id|name|text|password|text");
        inputStream.add("insert|people|id|1|name|Adam|password|****");
        inputStream.add("find|people");
        inputStream.add("drop|people");
        inputStream.add("people");
        inputStream.add("exit");

        Main.main(new String[0]);

        assertEquals("Welcome to program 'SQLCMD'\n" +
                "To view commands enter: help\n" +
                //connect|sqlcmd|postgres|postgres
                "Database connection 'sqlcmd' was successful\n" +
                "Enter command (or help):\n" +
                //create|people|id|name|text|password|text
                "Table 'people' created successfully\n" +
                "Enter command (or help):\n" +
                //insert|people|id|1|name|Adam|password|****
                "Records successfully added to the 'people'\n" +
                "Enter command (or help):\n" +
                //find|people
                "+----+------+----------+\n" +
                "| id | name | password |\n" +
                "+----+------+----------+\n" +
                "|  1 | Adam |     **** |\n" +
                "+----+------+----------+\n" +
                "\n" +
                "Enter command (or help):\n" +
                //drop|people
                "You want to delete the table 'people'? Enter the name of the table to confirm\n" +
                //people
                "Table 'people' successfully deleted\n" +
                "Enter command (or help):\n" +
                //exit
                "Goodbye, thank you for using our product\n", getData());
    }

    @Test
    public void testCreateInsertUpdateDrop() {
        inputStream.add("connect|sqlcmd|postgres|postgres");
        inputStream.add("create|people|id|name|text|password|text");
        inputStream.add("insert|people|id|1|name|Adam|password|****");
        inputStream.add("find|people");
        inputStream.add("update|people|id|1|name|Adam|password|ababab");
        inputStream.add("find|people");
        inputStream.add("drop|people");
        inputStream.add("people");
        inputStream.add("exit");

        Main.main(new String[0]);

        assertEquals("Welcome to program 'SQLCMD'\n" +
                "To view commands enter: help\n" +
                //connect|sqlcmd|postgres|postgres
                "Database connection 'sqlcmd' was successful\n" +
                "Enter command (or help):\n" +
                //create|people|id|name|text|password|text
                "Table 'people' created successfully\n" +
                "Enter command (or help):\n" +
                //insert|people|id|1|name|Adam|password|****
                "Records successfully added to the 'people'\n" +
                "Enter command (or help):\n" +
                //find|people
                "+----+------+----------+\n" +
                "| id | name | password |\n" +
                "+----+------+----------+\n" +
                "|  1 | Adam |     **** |\n" +
                "+----+------+----------+\n" +
                "\n" +
                "Enter command (or help):\n" +
                //"update|people|id|1|name|Adam|password|ababab"
                "All records successfully updated\n" +
                "Enter command (or help):\n" +
                //find|people
                "+----+------+----------+\n" +
                "| id | name | password |\n" +
                "+----+------+----------+\n" +
                "|  1 | Adam |   ababab |\n" +
                "+----+------+----------+\n" +
                "\n" +
                "Enter command (or help):\n" +
                //drop|people
                "You want to delete the table 'people'? Enter the name of the table to confirm\n" +
                //people
                "Table 'people' successfully deleted\n" +
                "Enter command (or help):\n" +
                //exit
                "Goodbye, thank you for using our product\n", getData());
    }

    @Test
    public void testCreateInsertDeleteRowDrop() {
        inputStream.add("connect|sqlcmd|postgres|postgres");
        inputStream.add("create|people|id|name|text|password|text");
        inputStream.add("insert|people|id|1|name|Adam|password|****");
        inputStream.add("insert|people|id|2|name|Eva|password|++++");
        inputStream.add("find|people");
        inputStream.add("delete|people|name|Adam");
        inputStream.add("find|people");
        inputStream.add("drop|people");
        inputStream.add("people");
        inputStream.add("exit");

        Main.main(new String[0]);

        assertEquals("Welcome to program 'SQLCMD'\n" +
                "To view commands enter: help\n" +
                //connect|sqlcmd|postgres|postgres
                "Database connection 'sqlcmd' was successful\n" +
                "Enter command (or help):\n" +
                //create|people|id|name|text|password|text
                "Table 'people' created successfully\n" +
                "Enter command (or help):\n" +
                //insert|people|id|1|name|Adam|password|****
                "Records successfully added to the 'people'\n" +
                "Enter command (or help):\n" +
                //insert|people|id|2|name|Eva|password|++++
                "Records successfully added to the 'people'\n" +
                "Enter command (or help):\n" +
                //find|people
                "+----+------+----------+\n" +
                "| id | name | password |\n" +
                "+----+------+----------+\n" +
                "|  1 | Adam |     **** |\n" +
                "+----+------+----------+\n" +
                "|  2 |  Eva |     ++++ |\n" +
                "+----+------+----------+\n" +
                "\n" +
                "Enter command (or help):\n" +
                //delete|people|name|Adam
                "Record successfully deleted\n" +
                "Enter command (or help):\n" +
                //find|people
                "+----+------+----------+\n" +
                "| id | name | password |\n" +
                "+----+------+----------+\n" +
                "|  2 |  Eva |     ++++ |\n" +
                "+----+------+----------+\n" +
                "\n" +
                "Enter command (or help):\n" +
                //drop|people
                "You want to delete the table 'people'? Enter the name of the table to confirm\n" +
                //people
                "Table 'people' successfully deleted\n" +
                "Enter command (or help):\n" +
                //exit
                "Goodbye, thank you for using our product\n", getData());
    }

    @Test
    public void testCreateInsertClearDrop() {
        inputStream.add("connect|sqlcmd|postgres|postgres");
        inputStream.add("create|people|id|name|text|password|text");
        inputStream.add("insert|people|id|1|name|Adam|password|****");
        inputStream.add("find|people");
        inputStream.add("clear|people");
        inputStream.add("people");
        inputStream.add("find|people");
        inputStream.add("drop|people");
        inputStream.add("people");
        inputStream.add("exit");

        Main.main(new String[0]);

        assertEquals("Welcome to program 'SQLCMD'\n" +
                "To view commands enter: help\n" +
                "Database connection 'sqlcmd' was successful\n" +
                "Enter command (or help):\n" +
                "Table 'people' created successfully\n" +
                "Enter command (or help):\n" +
                "Records successfully added to the 'people'\n" +
                "Enter command (or help):\n" +
                "+----+------+----------+\n" +
                "| id | name | password |\n" +
                "+----+------+----------+\n" +
                "|  1 | Adam |     **** |\n" +
                "+----+------+----------+\n" +
                "\n" +
                "Enter command (or help):\n" +
                "You want to clear the table 'people'? Enter the name of the table to confirm\n" +
                "Table 'people' successfully cleared\n" +
                "Enter command (or help):\n" +
                "+----+------+----------+\n" +
                "| id | name | password |\n" +
                "+----+------+----------+\n" +
                "\n" +
                "Enter command (or help):\n" +
                "You want to delete the table 'people'? Enter the name of the table to confirm\n" +
                "Table 'people' successfully deleted\n" +
                "Enter command (or help):\n" +
                "Goodbye, thank you for using our product\n", getData());
    }


    private String getData() {
        try {
            String result = new String(outputStream.toByteArray(), "UTF-8").replaceAll("\r\n", "\n");
            outputStream.reset();
            return result;
        } catch (UnsupportedEncodingException e) {
            return e.getMessage();
        }
    }
}
