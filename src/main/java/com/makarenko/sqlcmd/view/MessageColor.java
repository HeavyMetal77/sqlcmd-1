package com.makarenko.sqlcmd.view;

public class MessageColor {
    private Message message = new Console();

    public String getErrorMessage(String command) {
        return String.format(message.getColorRed() +
                "This command '%s' wrong, should be: " +
                message.getColorReset(), command);
    }

    public String getErrorNotExist(String command) {
        return String.format(message.getColorRed() +
                "Nonexistent command: '%s'" + message.getColorReset(), command);
    }

    public String getNotConnect(String command) {
        return String.format(message.getColorRed() +
                "You cannot use commands. Connect to the database" +
                message.getColorReset(), command);
    }
}
