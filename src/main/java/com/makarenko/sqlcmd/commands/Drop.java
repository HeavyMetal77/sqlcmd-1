package com.makarenko.sqlcmd.commands;

import com.makarenko.sqlcmd.model.DatabaseManager;
import com.makarenko.sqlcmd.view.Message;

public class Drop implements Command {
    private Message message;
    private DatabaseManager databaseManager;

    public Drop(Message message, DatabaseManager databaseManager) {
        this.message = message;
        this.databaseManager = databaseManager;
    }

    @Override
    public boolean beginCommand(String command) {
        return command.startsWith("drop|");
    }

    @Override
    public void executionCommand(String command) {
        String[] data = command.split("\\|");
        if (data.length != 2) {
            throw new IllegalArgumentException(String.format("Вы неверно ввели команду '%s', " +
                    "а должно быть drop|tableName", command));
        }

        String tableName = data[1];
        if (!confirmed(tableName)) {
            return;
        }

        databaseManager.dropTable(tableName);
        message.write(String.format("Таблица '%s' успешно удалена", tableName));
    }

    @Override
    public String formatCommand() {
        return "drop|tableName";
    }

    @Override
    public String depictionCommand() {
        return "Удаление таблицы";
    }

    private boolean confirmed(String tableName) {
        message.write(String.format("Вы собираетесь удалить таблицу '%s'. " +
                "Введите название таблицы для подтверждения", tableName));
        String verification = message.read();
        if (verification.equals(tableName)) {
            return true;
        }
        message.write("Удаление отменено");
        return false;
    }
}
