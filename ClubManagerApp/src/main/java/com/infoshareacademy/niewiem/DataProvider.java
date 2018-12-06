package com.infoshareacademy.niewiem;

import com.infoshareacademy.niewiem.factories.Tables;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class DataProvider {

    private static final String SEPARATOR = ";";
    private static final Path path = Paths.get("data");
    private static final Path hallsPath = Paths.get(path.toString(), "halls.csv");
    private static final Path tablesPath = Paths.get(path.toString(), "tables.csv");
    private static final Path reservationPath = Paths.get(path.toString(), "reservations.csv");

    private static final int HALL_ID_IN_HALLS = 0;
    private static final int HALL_NAME_IN_HALLS = 1;
    private static final int TABLE_ID_IN_TABLES = 2;
    private static final int TABLE_TYPE_IN_TABLES = 1;
    private static final int TABLE_NAME_IN_TABLES = 3;
    private static final int TABLE_ID_IN_RESERVATIONS = 0;
    private static final int START_TIME_IN_RESERVATIONS = 1;
    private static final int END_TIME_IN_RESERVATIONS = 2;
    private static final int CUSTOMER = 3;


    /**
     * Halls
     *********************************************************************************************************/

    public static void saveHallInCsv(Hall hall) {

        try {
            String hallToAddAsString = hall.toCSVString();
            Files.write(hallsPath, hallToAddAsString.getBytes(), StandardOpenOption.APPEND);

        } catch (IOException ex) {
            System.out.println("Can't save this file!");
        }
    }

    public static List<Hall> loadHallsAsList() {
        List<String> rawHallsAsStrings = getFileAsRawStringList(hallsPath);
        return getListOfSavedHalls(rawHallsAsStrings);
    }


    private static List<Hall> getListOfSavedHalls(List<String> rawHallsAsStrings) {
        List<Hall> hallsAsMap = new ArrayList<>();

        for (String line : rawHallsAsStrings) {
            String[] hallAsArray = line.split(SEPARATOR);

            Integer hallId = Integer.valueOf(hallAsArray[HALL_ID_IN_HALLS]);
            String hallName = hallAsArray[HALL_NAME_IN_HALLS];

//            hallsAsMap.add(new Hall(hallId, hallName));
        }

        return hallsAsMap;
    }

    public static List<Hall> getMapOfExistingHalls() {
        return getMapOfExistingHalls(getFileAsRawStringList(hallsPath));

        //todo: create list of objects Hall, not map of int / String
    }

    public static Integer getNextAvailableHallID() {
        OptionalInt nextAvailableId = getMapOfExistingHalls().stream()
                .mapToInt(e -> e.getHallId())
                .max();

        if (nextAvailableId.isPresent()) {
            return nextAvailableId.getAsInt() + 1;
        }
        return 1;
    }

    /*** Tables *******************************************************************************************************/

    public static boolean saveTableInCsv(Table table) {
        List<String> out = new ArrayList<>();

        try {
            loadRawFileAsListOfStrings(out, tablesPath);

            List<String> tablesAsStringList = new ArrayList<>();
            tablesAsStringList.add(String.valueOf(table.getHall().getHallId()));
            tablesAsStringList.add(String.valueOf(table.getType()));
            tablesAsStringList.add(String.valueOf(table.getTableId()));
            tablesAsStringList.add(String.valueOf(table.getTableName()));

            String singleEntry = tablesAsStringList.stream().collect(Collectors.joining(SEPARATOR));
            out.add(singleEntry);
            Files.write(tablesPath, out);

            return true;

        } catch (IOException ex) {
            System.out.println("Can't save this file!");
            return false;
        }
    }

    public static List<Table> returnTablesListFromFile(Hall hall) {
        return returnTablesListFromFile(hall, getFileAsRawStringList(tablesPath));
    }

    private static List<Table> returnTablesListFromFile(Hall hall, List<String> fileByLines) {
        List<Table> tables = new ArrayList<>();

        for (String line : fileByLines) {
            Table tableToAdd = loadTable(hall, line.split(SEPARATOR));
            if (tableToAdd != null) {
                tables.add(tableToAdd);
            }
        }
        return tables;
    }

    private static Table loadTable(Hall hall, String[] splittedLine) {

        Integer hallIdFromFile = Integer.valueOf(splittedLine[HALL_ID_IN_HALLS].trim());

        if (hallIdFromFile.equals(hall.getHallId())) {
            TableType tableType = TableType.valueOf(splittedLine[TABLE_TYPE_IN_TABLES].trim());
            Integer tableId = Integer.valueOf(splittedLine[TABLE_ID_IN_TABLES].trim());
            String tableName = splittedLine[TABLE_NAME_IN_TABLES].trim();
            return new Table(hall, tableType, tableId, tableName);
        }

        return null;
    }

    /**
     * Reservations
     **************************************************************************************************/

    public static boolean saveReservationInCsv(Reservation reservation) {
        List<String> out = new ArrayList<>();

        try {
            loadRawFileAsListOfStrings(out, reservationPath);

            List<String> reservationAsStringList = new ArrayList<>();
            reservationAsStringList.add(String.valueOf(reservation.getTable().getTableId()));
            reservationAsStringList.add(String.valueOf(reservation.getStartTime()));
            reservationAsStringList.add(String.valueOf(reservation.getEndTime()));
            reservationAsStringList.add(String.valueOf(reservation.getCustomer()));

            String singleEntry = reservationAsStringList.stream().collect(Collectors.joining(SEPARATOR));
            out.add(singleEntry);
            Files.write(reservationPath, out);

            return true;

        } catch (IOException ex) {
            System.out.println("Can't save this file!");
            return false;
        }
    }

    public static List<Reservation> returnReservationsFromFile(List<Table> tables) {
        return returnReservationsFromFile(tables, getFileAsRawStringList(reservationPath));
    }

    private static List<Reservation> returnReservationsFromFile(List<Table> tables, List<String> fileByLines) {

        List<Reservation> reservations = new ArrayList<>();

        for (String line : fileByLines) {
            Reservation reservationToAdd = loadReservations(tables, line.split(SEPARATOR));
            if (reservationToAdd != null) {
                reservations.add(reservationToAdd);
            }
        }
        return reservations;
    }

    private static Reservation loadReservations(List<Table> tables, String[] splittedLine) {

        Integer tableIdFromFile = Integer.valueOf(splittedLine[TABLE_ID_IN_RESERVATIONS].trim());

        Table tableFromReservation = tables.stream()
                .filter(table -> table.getTableId().equals(tableIdFromFile))
                .findFirst()
                .orElse(null);

        boolean isOnAList = (tableFromReservation != null);

        if (isOnAList) {
            LocalDateTime startTime = LocalDateTime.parse(splittedLine[START_TIME_IN_RESERVATIONS].trim());
            LocalDateTime endTime = LocalDateTime.parse(splittedLine[END_TIME_IN_RESERVATIONS].trim());
            String customer = splittedLine[CUSTOMER].trim();
            return new Reservation(tableFromReservation, startTime, endTime, customer);
        } else
            return null;
    }

    /**
     * Files - shared functionality
     **********************************************************************************/

    private static List<String> getFileAsRawStringList(Path path) {
        try {
            return new ArrayList<>(Files.readAllLines(path));
        } catch (IOException ex) {
            System.out.println("Can't find this file!");
            return new ArrayList<>();
        }
    }

    /***NEW CODE******************************************************************************************************/

    public static void removeTableFromFile(Hall hall, Table table) {

        List<Table> tables = returnTablesListFromFile(hall);

        Table tableToRemove = Tables.getTableByID(hall, table.getTableId());
        tables.remove(tableToRemove);

        for (Table tableFromNewList : tables) {
            saveTableInCsv(tableFromNewList);
        }

    }
}