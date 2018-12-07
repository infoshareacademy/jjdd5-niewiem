package com.infoshareacademy.niewiem;

public class App {

    public static void main(String[] args) {
        DataProvider.checkDataStructure();
        ConsoleInterface consoleUI = new ConsoleInterface();
        consoleUI.bootUp();
    }
}
