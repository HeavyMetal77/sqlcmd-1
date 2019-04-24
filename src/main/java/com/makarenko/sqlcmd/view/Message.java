package com.makarenko.sqlcmd.view;

public interface Message {

    void writeln(String message);

    void write(String message);

    String read();

    String getColorRed();

    String getColorReset();
}
