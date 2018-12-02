package com.infoshareacademy.niewiem;

import java.util.Map;

public class ConsolePrinter {
    private static final int HOUR_IN_SECONDS = 60 * 60;
    private static final int MINUTE_IN_SECONDS = 60;
    // todo: put tablesPerRow as Configuration
    // todo: put possibility of changing tablesPerRow in Settings
    private static int tablesPerRow = 20;

    public void printTables(Map<Table, Long> tables) {
        int howManyTables = tables.size();
        int howManyRows = 1 + (howManyTables / tablesPerRow);
        for (int i = 1; i <= howManyRows; i++) {
            // todo: filter the map, so only the tables in the row are fed to printer
            printRowOfTables(tables);
        }
    }

    private void printRowOfTables(Map<Table, Long> tables) {
        int howManyTables = tables.size();
        printEdge(howManyTables);
        printLineWithTableNumbers(tables);
        printMiddleLine(howManyTables);
        printLineWithRemainingTime(tables);
        printMiddleLine(howManyTables);
        printMiddleLine(howManyTables);
        printEdge(howManyTables);
    }

    private void printLineWithRemainingTime(Map<Table, Long> tables) {
        tables.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(entry -> printRemainingTime(entry.getValue()));
        System.out.print("\n");
    }

    private void printRemainingTime(Long remainingSeconds) {
        if (remainingSeconds == 0) {
            System.out.print("|       |");
            return;
        }
        long hours = remainingSeconds / (HOUR_IN_SECONDS);
        remainingSeconds -= hours * HOUR_IN_SECONDS;
        long minutes = remainingSeconds / MINUTE_IN_SECONDS;
        remainingSeconds -= minutes * MINUTE_IN_SECONDS;
        long seconds = remainingSeconds;

        System.out.printf("|%01d:%02d:%02d|", hours, minutes, seconds);
    }

    private static void printLineWithTableNumbers(Map<Table, Long> tables) {
        tables.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(entry -> System.out.print("|  " + entry.getKey().getTableName() + "  |"));
        System.out.print("\n");
    }

    private static void printEdge(int howManyTables) {
        for (int i = 1; i <= howManyTables; i++) {
            System.out.print("+-------+");
        }
        System.out.print("\n");

    }

    private static void printMiddleLine(int howManyTables) {
        for (int i = 1; i <= howManyTables; i++) {
            System.out.print("|       |");
        }
        System.out.print("\n");
    }
}
