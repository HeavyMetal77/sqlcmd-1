package com.makarenko.sqlcmd.commands;

import com.makarenko.sqlcmd.model.DatabaseManager;
import com.makarenko.sqlcmd.view.Message;

import java.sql.SQLException;

public class DropTable implements Command {
    Message message;
    DatabaseManager databaseManager;

    public DropTable(Message message, DatabaseManager databaseManager) {
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
        if (!isCorrectCommand(command, data)) {
            return;
        }

        String tableName = data[1];
        if (!confirmed(tableName)) {
            return;
        }

        try {
            databaseManager.dropTable(tableName);
            message.write(String.format("Таблица '%s' успешно удалена", tableName));
        } catch (SQLException e) {
            message.write(String.format(
                    "Не удалось удалить таблицу '%s' по причине '%s'", tableName, e.getMessage()));
        }
    }

    @Override
    public String formatCommand() {
        return "drop|tableName";
    }

    @Override
    public String depictionCommand() {
        return "Удаление таблицы";
    }

    private boolean isCorrectCommand(String command, String data[]) {
        if (data.length != 2) {
            message.write(String.format("Вы неверно ввели данные '%s', а должно быть drop|tableName", command));
            return false;
        }
        return true;
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
