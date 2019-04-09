package com.makarenko.sqlcmd.commands;

import com.makarenko.sqlcmd.model.DatabaseManager;
import com.makarenko.sqlcmd.view.Message;

public class Clear implements Command {
    private Message message;
    private DatabaseManager databaseManager;

    public Clear(Message message, DatabaseManager databaseManager) {
        this.message = message;
        this.databaseManager = databaseManager;
    }

    @Override
    public boolean beginCommand(String command) {
        return command.startsWith("clear|");
    }

    @Override
    public void executionCommand(String command) {
        String data[] = command.split("\\|");
        if (!isCorrectCommand(command, data)) {
            return;
        }

        String tableName = data[1];
        if (!confirmed(tableName)) {
            return;
        }

        databaseManager.clearTable(tableName);
        message.write(String.format("Таблица '%s' успешно очищена", tableName));
    }

    @Override
    public String formatCommand() {
        return "clear|tableName";
    }

    @Override
    public String depictionCommand() {
        return "Очистка таблицы";
    }

    private boolean isCorrectCommand(String command, String data[]) {
        if (data.length != 2) {
            message.write(String.format("Вы неверно ввели команду '%s', а должно быть clear|tableName", command));
            return false;
        }
        return true;
    }

    private boolean confirmed(String tableName) {
        message.write(String.format("Вы собираетесь очистить таблицу '%s'. " +
                "Введите название таблицы для подтверждения", tableName));
        String verification = message.read();
        if (verification.equals(tableName)) {
            return true;
        }
        message.write("Очистка отменена");
        return false;
    }
}
