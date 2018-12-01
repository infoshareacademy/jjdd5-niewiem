package com.infoshareacademy.niewiem;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.*;

public class DataProvider {
    private static final Path path = Paths.get("test-hall");
    private static final Path tablesPath = Paths.get(path.toString(), "halls.csv");
    private static final Path hallsPath = Paths.get(path.toString(), "tables.csv");
    private static final Path reservationPath = Paths.get(path.toString(), "reservations.csv");

    private static final String SEPARATOR = ";";
    public static final int HALL_ID_IN_HALLS = 0;
    public static final int HALL_NAME_IN_HALLS = 1;
    public static final int TABLE_TYPE_IN_TABLES_ = 1;
    public static final int TABLE_ID_IN_TABLES = 2;
    public static final int TABLE_NAME_IN_TABLES = 3;
    public static final int TABLE_ID_IN_RESERVATIONS = 0;

    public static Map<Integer, String> readFromHalls() {
        return readFromHalls(getFileAsLineList(hallsPath));
    }

    public static List<Table> readFromTables(Hall hall) {
        return readFromTables(hall, getFileAsLineList(tablesPath));
    }

    public static Table readFromReservations(List<Table> tables) {
        return readFromReservations(tables, getFileAsLineList(reservationPath));
    }

    private static Map<Integer, String> readFromHalls(List<String> fileByLines) {
        Map<Integer, String> hallsAsMap = new HashMap<>();

        for (String line : fileByLines) {
            String[] hallAsArray = line.split(SEPARATOR);

            Integer hallID = Integer.valueOf(hallAsArray[HALL_ID_IN_HALLS]);
            String hallName = hallAsArray[HALL_NAME_IN_HALLS];


            hallsAsMap.put(hallID, hallName);
        }

        return hallsAsMap;
    }

    private static List<Table> readFromTables(Hall hall, List<String> fileByLines) {
        List<Table> tables = new ArrayList<>();

        for (String line : fileByLines) {
            Table tableToAdd = loadTable(hall, line.split(SEPARATOR));
            if (tableToAdd != null) {
                tables.add(tableToAdd);
            }
        }
        return tables;
    }

    private static List<Reservation> readFromReservations(List<Table> tables, List<String> fileByLines) {
        List<Reservation> reservations = new ArrayList<>();

        for (String line : fileByLines) {
            String[] reservatiosAsArray = line.split(SEPARATOR);
            Integer tableIdFromFile = Integer.valueOf(reservatiosAsArray[0]);
        }
        return null;
    }

    private static List<String> getFileAsLineList(Path path) {
        try {
            return new ArrayList<>(Files.readAllLines(path));
        } catch (IOException ex) {
            System.out.println("Can't find this file!");
            return new ArrayList<>();
        }
    }

    private static Table loadTable(Hall hall, String[] splittedLine) {

        Integer hallIDfromFile = Integer.valueOf(splittedLine[HALL_ID_IN_HALLS].trim());

        if (hallIDfromFile.equals(hall.getHallId())) {
            TableType tableType = TableType.valueOf(splittedLine[TABLE_TYPE_IN_TABLES_].trim());
            Integer tableId = Integer.valueOf(splittedLine[TABLE_ID_IN_TABLES].trim());
            String tableName = splittedLine[TABLE_NAME_IN_TABLES].trim();
            return new Table(hall, tableType, tableId, tableName);
        }

        return null;
    }

    private static Reservation loadReservations(List<Table> tables, String[] splittedLine) {
        Integer tableIdFromFile = Integer.valueOf(splittedLine[TABLE_ID_IN_RESERVATIONS].trim());

        Table tableFromReservation = tables.stream()
                .filter(table -> table.getTableId() == tableIdFromFile)
                .findFirst()
                .orElse(null);

        boolean isOnAList = (tableFromReservation != null);

        if (isOnAList) {
            LocalDateTime startTime = LocalDateTime.parse(splittedLine[1].trim());
            LocalDateTime endTime = LocalDateTime.parse(splittedLine[2].trim());
            String customer = splittedLine[3].trim();

            return new Reservation(tableFromReservation, startTime, endTime, customer);
        }
        return null;
    }
}




