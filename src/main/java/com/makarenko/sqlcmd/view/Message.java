package com.makarenko.sqlcmd.view;

public interface Message {

    void write(String message);

    String read();

    String getColorRed();

    String getColorReset();
}
