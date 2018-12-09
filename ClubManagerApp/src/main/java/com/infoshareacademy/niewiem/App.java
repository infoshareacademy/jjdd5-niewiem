package com.infoshareacademy.niewiem;

import com.infoshareacademy.niewiem.consoleui.ConsoleInterface;
import com.infoshareacademy.niewiem.dao.DataProvider;

public class App {

    public static void main(String[] args) {
        DataProvider.checkDataStructure();
        ConsoleInterface consoleUI = new ConsoleInterface();
        consoleUI.bootUp();
    }
}
