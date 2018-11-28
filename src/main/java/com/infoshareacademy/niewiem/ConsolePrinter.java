package com.infoshareacademy.niewiem;

import java.util.HashMap;
import java.util.Map;

import static com.infoshareacademy.niewiem.TableType.*;

public class ConsolePrinter {
    // todo: put tablesPerRow as Configuration
    // todo: put possibility of changing tablesPerRow in Settings
    private static int tablesPerRow = 20;

    public static void main(String[] args) {
        Map<Table, Long> tables = new HashMap<>();
        tables.put(new Table("P01", POOL), 1200L);
        tables.put(new Table("P02", POOL), 0L);
        tables.put(new Table("P03", POOL), 60L);
        tables.put(new Table("P04", POOL), 800L);
        tables.put(new Table("P05", POOL), 0L);

        printTables(tables);
    }

    public static void printTables(Map<Table, Long> tables) {
        int howManyTables = tables.size();
        int howManyRows = 1 + (howManyTables / tablesPerRow);
        for (int i = 1; i <= howManyRows; i++) {
            // todo: filter the map, so only the tables in the row are fed to printer
            printRowOfTables(tables);
        }
    }

    private static void printRowOfTables(Map<Table, Long> tables) {
        int howManyTables = tables.size();
        printEdge(howManyTables);
        printLineWithTableNumbers(tables);
        printMiddleLine(howManyTables);
        printLineWithRemainingTime(tables);
        printMiddleLine(howManyTables);
        printMiddleLine(howManyTables);
        printEdge(howManyTables);
    }

    private static void printLineWithRemainingTime(Map<Table, Long> tables) {
        tables.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(entry -> printRemainingTime(entry.getValue()));
        System.out.print("\n");
    }

    private static void printRemainingTime(Long remainingSeconds) {
        if (remainingSeconds == 0) {
            System.out.print("|       |");
            return;
        }
        long hours = remainingSeconds / (60L * 60L);
        remainingSeconds -= hours * (60 * 60);
        long minutes = remainingSeconds / 60;
        remainingSeconds -= minutes * 60;
        long seconds = remainingSeconds;

        System.out.printf("|%01d:%02d:%02d|", hours, minutes, seconds);
    }

    private static void printLineWithTableNumbers(Map<Table, Long> tables) {
        tables.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(entry -> System.out.printf("|  " + entry.getKey().getTableId() + "  |"));
        System.out.print("\n");
    }

    private static void printEdge(int howManyTables) {
        for (int i = 1; i <= howManyTables; i++) {
            System.out.printf("+-------+");
        }
        System.out.print("\n");

    }

    private static void printMiddleLine(int howManyTables) {
        for (int i = 1; i <= howManyTables; i++) {
            System.out.printf("|       |");
        }
        System.out.print("\n");
    }

}
