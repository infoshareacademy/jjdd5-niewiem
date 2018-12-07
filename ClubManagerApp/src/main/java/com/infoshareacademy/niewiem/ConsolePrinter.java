package com.infoshareacademy.niewiem;

import com.infoshareacademy.niewiem.factories.Reservations;

import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ConsolePrinter {
    private static final int HOUR_IN_SECONDS = 60 * 60;
    private static final int MINUTE_IN_SECONDS = 60;
    // todo: put tablesPerRow as Configuration
    // todo: put possibility of changing tablesPerRow in Settings
    private static int tablesPerRow = 100;

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

        String remainingTime = convertSecondsToString(remainingSeconds);

        System.out.printf("|" + remainingTime + "|");
    }

    private String convertSecondsToString(Long remainingSeconds) {
        long hours = remainingSeconds / (HOUR_IN_SECONDS);
        remainingSeconds -= hours * HOUR_IN_SECONDS;
        long minutes = remainingSeconds / MINUTE_IN_SECONDS;
        remainingSeconds -= minutes * MINUTE_IN_SECONDS;
        long seconds = remainingSeconds;

        return "" + String.format("%01d", hours) + ":" + String.format("%02d", minutes) + ":" + String.format("%02d", seconds);
    }


    private static void printLineWithTableNumbers(Map<Table, Long> tables) {
        tables.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(entry -> System.out.print("|  " + entry.getKey().getTableName() + "  |"));
        System.out.print("\n");
    }

    private static void printEdge(int howManyTables) {
        for (int i = 1; i <= howManyTables; i++) {
            System.out.print("o-------o");
        }
        System.out.print("\n");

    }

    private static void printMiddleLine(int howManyTables) {
        for (int i = 1; i <= howManyTables; i++) {
            System.out.print("|       |");
        }
        System.out.print("\n");
    }

    public List<Reservation> printListOfReservationsSortedByTableName(Hall hall) {
        List<Reservation> reservations = Reservations.getUpcomingOrInProgressReservations(hall).stream()
                .sorted(Comparator.comparing(Reservation::getTable))
                .collect(Collectors.toList());
        printListOfReservations(reservations);
        return reservations;
    }


    public List<Reservation> showOnlyUpcomingReservations(Hall hall) {
        List<Reservation> reservations = Reservations.getUpcomingReservations(hall).stream()
                .sorted(Comparator.comparing(Reservation::getTable))
                .collect(Collectors.toList());
        printListOfReservations(reservations);
        return reservations;
    }

    public void printFastestAvailableTables(Hall hall) {
        Reservations.getAllTablesAndRemainingTimes(hall).entrySet().stream()
                .sorted(Comparator.comparing(Map.Entry::getValue))
                .forEach(e -> {
                    String tableName = e.getKey().getTableName();
                    Long remainingSeconds = e.getValue();
                    System.out.println(tableName + " - remaining time: " + convertSecondsToString(remainingSeconds));
                });
    }

    private void printListOfReservations(List<Reservation> reservations) {
        DateTimeFormatter startFormat = DateTimeFormatter.ofPattern("(MM-dd) HH:mm");
        DateTimeFormatter endFormat = DateTimeFormatter.ofPattern("HH:mm");
        for (int i = 0; i < reservations.size(); i++) {
            String index = String.format("%02d", i + 1);
            Reservation r = reservations.get(i);
            String tableName = r.getTable().getTableName();
            String startTime = r.getStartTime().format(startFormat);
            String endTime = r.getEndTime().format(endFormat);
            System.out.println(index + ". " + tableName + ": " + startTime + " -> " + endTime);
        }
    }

    public List<Reservation> printReservationForSpecificTable(Hall hall, Table table) {
        List<Reservation> reservations = Reservations.getUpcomingOrInProgressReservations(hall).stream()
                .filter(r -> r.getTable().equals(table))
                .sorted(Comparator.comparing(Reservation::getStartTime))
                .collect(Collectors.toList());
        printListOfReservations(reservations);
        return reservations;
    }

    public void printPastReservations(Hall hall) {
        List<Reservation> reservations = Reservations.getPastReservations(hall).stream()
                .sorted(Comparator.comparing(Reservation::getTable))
                .collect(Collectors.toList());
        printListOfReservations(reservations);
    }

    public void showPastReservationsForSpecificTable(Hall hall, Table table) {
        List<Reservation> reservations = Reservations.getPastReservations(hall).stream()
                .filter(r -> r.getTable().equals(table))
                .sorted(Comparator.comparing(Reservation::getStartTime))
                .collect(Collectors.toList());
        printListOfReservations(reservations);
    }


    // LAB - NOT YET WORKING FEATURES!!!
    public void printAvailabilityAsGraph(Hall hall){
        System.out.println("" +
                "TIME|18:00||19:00||20:00||21:00|" +
                "P01:[++|++][++|++][++|++][++|++]" +
                "P01:[++|++][--|--][--|--][++|++]" +
                "P01:[++|++][++|++][++|++][++|++]" +
                "P02:[--|--][--|--][--|--][--|--]");
    }
}
