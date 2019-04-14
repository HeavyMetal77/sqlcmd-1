package com.makarenko.sqlcmd.view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Console implements Message {

    @Override
    public void write(String message) {
        System.out.println(message);
    }

    @Override
    public String read() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            return reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    public String getColorRed() {
        return "\u001B[31m";
    }

    @Override
    public String getColorReset() {
        return "\u001B[0m";
    }
}
