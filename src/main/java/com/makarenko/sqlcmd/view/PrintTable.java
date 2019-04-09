package com.makarenko.sqlcmd.view;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PrintTable {
    private static class Column {

        private String label;
        private int type;
        private int width = 0;
        private List<String> values = new ArrayList<>();
        private String justifyFlag = "";
        private int typeCategory = 0;

        public Column (String label, int type) {
            this.label = label;
            this.type = type;
        }

        public String getLabel() {
            return label;
        }

        public int getType() {
            return type;
        }

        public int getWidth() {
            return width;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        public void addValue(String value) {
            values.add(value);
        }

        public String getValue(int i) {
            return values.get(i);
        }

        public String getJustifyFlag() {
            return justifyFlag;
        }

        public int getTypeCategory() {
            return typeCategory;
        }

        public void setTypeCategory(int typeCategory) {
            this.typeCategory = typeCategory;
        }
    }

    public static void printResultSet(ResultSet resultSet) {
        try {
            if (resultSet == null) {
                System.err.println("TablePrinter Error: Result set is null!");
                return;
            }
            if (resultSet.isClosed()) {
                System.err.println("TablePrinter Error: Result Set is closed!");
                return;
            }

            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            List<Column> columns = new ArrayList<>(columnCount);

            for (int i = 1; i <= columnCount; i++) {
                Column c = new Column(metaData.getColumnLabel(i), metaData.getColumnType(i));
                c.setWidth(c.getLabel().length());
                c.setTypeCategory(c.getType());
                columns.add(c);
            }

            int rowCount = 0;
            while (resultSet.next()) {
                for (int i = 0; i < columnCount; i++) {
                    Column c = columns.get(i);
                    String value;
                    int category = c.getTypeCategory();

                    if (category == 0) {
                        value = "(" + c.typeCategory + ")";
                    } else {
                        value = resultSet.getString(i + 1) == null ? "NULL" : resultSet.getString(i + 1);
                    }

                    c.setWidth(value.length() > c.getWidth() ? value.length() : c.getWidth());
                    c.addValue(value);
                }
                rowCount++;
            }

            printColumnAndRow(columns, rowCount);
        } catch (SQLException e) {
            System.err.println("SQL exception in TablePrinter. Message:");
            System.err.println(e.getMessage());
        }
    }

    private static void printColumnAndRow(List<Column> columns, int rowCount) {
        StringBuilder strToPrint = new StringBuilder();
        StringBuilder rowSeparator = new StringBuilder();

        for (Column c : columns) {
            int width = c.getWidth();

            String toPrint;
            String name = c.getLabel();
            int diff = width - name.length();

            if ((diff%2) == 1) {
                width++;
                diff++;
                c.setWidth(width);
            }

            int paddingSize = diff/2;
            String padding = new String(new char[paddingSize]).replace("\0", " ");
            toPrint = "| " + padding + name + padding + " ";
            strToPrint.append(toPrint);
            rowSeparator.append("+");
            rowSeparator.append(new String(new char[width + 2]).replace("\0", "-"));
        }

        String lineSeparator = System.getProperty("line.separator");
        lineSeparator = lineSeparator == null ? "\n" : lineSeparator;
        rowSeparator.append("+").append(lineSeparator);
        strToPrint.append("|").append(lineSeparator);
        strToPrint.insert(0, rowSeparator);
        strToPrint.append(rowSeparator);

        System.out.print(strToPrint.toString());

        String format;

        for (int i = 0; i < rowCount; i++) {
            for (Column c : columns) {
                format = String.format("| %%%s%ds ", c.getJustifyFlag(), c.getWidth());
                System.out.print(String.format(format, c.getValue(i)));
            }
            System.out.println("|");
            System.out.print(rowSeparator);
        }
        System.out.println();
    }
}
