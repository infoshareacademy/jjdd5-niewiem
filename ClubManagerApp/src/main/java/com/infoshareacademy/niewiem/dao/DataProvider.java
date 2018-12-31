package com.infoshareacademy.niewiem.dao;

import com.infoshareacademy.niewiem.enums.TableType;
import com.infoshareacademy.niewiem.domain.Hall;
import com.infoshareacademy.niewiem.domain.Reservation;
import com.infoshareacademy.niewiem.domain.Table;
import com.infoshareacademy.niewiem.repositories.Tables;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class DataProvider {

    private static final Logger LOG = LogManager.getLogger(DataProvider.class);


    private static final String SEPARATOR = ";";
    private static final Path path = Paths.get("data");
    private static final Path hallsPath = Paths.get(path.toString(), "halls.csv");
    private static final Path tablesPath = Paths.get(path.toString(), "tables.csv");
    private static final Path reservationPath = Paths.get(path.toString(), "reservations.csv");

    private static final int HALL_ID_IN_HALLS = 0;
    private static final int HALL_NAME_IN_HALLS = 1;
    private static final int HALL_ID_IN_TABLES = 0;
    private static final int TABLE_ID_IN_TABLES = 2;
    private static final int TABLE_TYPE_IN_TABLES = 1;
    private static final int TABLE_NAME_IN_TABLES = 3;
    private static final int TABLE_ID_IN_RESERVATIONS = 1;
    private static final int START_TIME_IN_RESERVATIONS = 2;
    private static final int END_TIME_IN_RESERVATIONS = 3;
    private static final int CUSTOMER_IN_RESERVATIONS = 4;
    public static final int RESERVATION_ID_IN_RESERVATIONS = 0;


    /*** Halls ********************************************************************************************************/

    public static void saveHallInCsv(Hall hall) {
        try {
            String hallToAddAsString = hall.toCsvString();
            Files.write(hallsPath, hallToAddAsString.getBytes(), StandardOpenOption.APPEND);
            Files.write(hallsPath, "\n".getBytes(), StandardOpenOption.APPEND);

        } catch (IOException ex) {
            System.out.println("Can't save this file!");
            LOG.warn("file not saved");
        }
    }

    public static List<Hall> loadHallsAsList() {
        List<String> rawHallsAsStrings = getFileAsRawStringList(hallsPath);
        return getListOfSavedHalls(rawHallsAsStrings);
    }

    private static List<Hall> getListOfSavedHalls(List<String> rawHallsAsStrings) {
        List<Hall> savedHalls = new ArrayList<>();

        for (String line : rawHallsAsStrings) {
            String[] hallAsArray = line.split(SEPARATOR);

            Integer hallId = Integer.valueOf(hallAsArray[HALL_ID_IN_HALLS].trim());
            String hallName = hallAsArray[HALL_NAME_IN_HALLS].trim();

            savedHalls.add(new Hall(hallId, hallName));
        }
        return savedHalls;
    }

    public static void removeHallFromFile(Hall hall) {

        List<Hall> hallsAsList = loadHallsAsList();

        List<Hall> hallsListWithoutRemovedHall = hallsAsList.stream()
                .filter(h -> !(h.getId().equals(hall.getId())))
                .collect(Collectors.toList());

        removePreviousContentFromCsv(hallsPath);

        for (Hall hallFromList : hallsListWithoutRemovedHall) {
            saveHallInCsv(hallFromList);
        }
    }

    /*** Tables *******************************************************************************************************/

    public static void saveTableInCsv(Table table) {
        try {
            String tableToAddAsString = table.toCsvString(table.getHall());
            Files.write(tablesPath, tableToAddAsString.getBytes(), StandardOpenOption.APPEND);
            Files.write(tablesPath, "\n".getBytes(), StandardOpenOption.APPEND);

        } catch (IOException ex) {
            System.out.println("Can't save this file!");
            LOG.warn("file not saved");
        }
    }

    public static Set<Integer> getSetOfSavedTablesIds() {
        List<String> rawTablesAsStrings = getFileAsRawStringList(tablesPath);
        Set<Integer> savedTablesIds = new HashSet<>();

        for (String line : rawTablesAsStrings) {
            String[] tableAsArray = line.split(SEPARATOR);

            Integer tableId = Integer.valueOf(tableAsArray[TABLE_ID_IN_TABLES].trim());
            savedTablesIds.add(tableId);
        }
        return savedTablesIds;
    }

    public static List<Table> loadTablesAsList(Hall hall) {
        List<String> rawTablesAsStrings = getFileAsRawStringList(tablesPath);
        return getListOfSavedTables(hall, rawTablesAsStrings);
    }

    private static List<Table> getListOfSavedTables(Hall hall, List<String> rawTablesAsStrings) {
        List<Table> savedTables = new ArrayList<>();

        for (String line : rawTablesAsStrings) {
            String[] tableAsArray = line.split(SEPARATOR);

            Integer hallIdFromFile = Integer.valueOf(tableAsArray[HALL_ID_IN_TABLES].trim());
            TableType tableType = TableType.valueOf(tableAsArray[TABLE_TYPE_IN_TABLES].trim());
            Integer tableId = Integer.valueOf(tableAsArray[TABLE_ID_IN_TABLES].trim());
            String tableName = tableAsArray[TABLE_NAME_IN_TABLES].trim();

            if (hallIdFromFile.equals(hall.getId())) {
                savedTables.add(new Table(hall, tableType, tableId, tableName));
            }
        }
        return savedTables;
    }

    public static void removeTableFromFile(Hall hall, Table table) {

        List<Table> tablesAsList = loadTablesAsList(hall);

        List<Table> tablesListWithoutRemovedTable = tablesAsList.stream()
                .filter(t -> !(t.getId().equals(table.getId())))
                .collect(Collectors.toList());

        removePreviousContentFromCsv(tablesPath);

        for (Table tableFromList : tablesListWithoutRemovedTable) {
            saveTableInCsv(tableFromList);
        }
    }

    /*** Reservations *************************************************************************************************/

    public static void saveReservationInCsv(Reservation reservation) {
        try {
            String tableToAddAsString = reservation.toCsvString(reservation.getTable());
            Files.write(reservationPath, tableToAddAsString.getBytes(), StandardOpenOption.APPEND);
            Files.write(reservationPath, "\n".getBytes(), StandardOpenOption.APPEND);

        } catch (IOException ex) {
            System.out.println("Can't save this file!");
            LOG.warn("file not saved");
        }
    }

    public static Set<Long> getSetOfSavedResIds() {
        List<String> rawReservationsAsStrings = getFileAsRawStringList(reservationPath);
        Set<Long> savedHallsIds = new HashSet<>();

        for (String line : rawReservationsAsStrings) {
            String[] reservationAsArray = line.split(SEPARATOR);

            Long reservationId = Long.valueOf(reservationAsArray[RESERVATION_ID_IN_RESERVATIONS].trim());
            savedHallsIds.add(reservationId);
        }
        return savedHallsIds;
    }

    public static List<Reservation> loadReservationsAsList(List<Table> tables) {
        List<String> rawReservationsAsStrings = getFileAsRawStringList(reservationPath);
        return getListOfReservations(tables, rawReservationsAsStrings);
    }

    private static List<Reservation> getListOfReservations(List<Table> tables, List<String> rawReservationsAsStrings) {
        List<Reservation> reservations = new ArrayList<>();

        for (String line : rawReservationsAsStrings) {
            String[] reservationAsArray = line.split(SEPARATOR);

            Long reservationsId = Long.valueOf(reservationAsArray[RESERVATION_ID_IN_RESERVATIONS].trim());
            Integer tableIdFromFile = Integer.valueOf(reservationAsArray[TABLE_ID_IN_RESERVATIONS].trim());
            LocalDateTime startTime = LocalDateTime.parse(reservationAsArray[START_TIME_IN_RESERVATIONS].trim());
            LocalDateTime endTime = LocalDateTime.parse(reservationAsArray[END_TIME_IN_RESERVATIONS].trim());
            String customer;
            if (reservationAsArray.length > CUSTOMER_IN_RESERVATIONS) {
                customer = reservationAsArray[CUSTOMER_IN_RESERVATIONS].trim();
            } else {
                customer = "";
            }

            Table tableFromReservation = tables.stream()
                    .filter(table -> table.getId().equals(tableIdFromFile))
                    .findFirst()
                    .orElse(null);

            if (tableFromReservation != null) {
                reservations.add(new Reservation(reservationsId, tableFromReservation, startTime, endTime, customer));
            }
        }
        return reservations;
    }

    public static void removeReservationFromFile(Reservation reservation) {

        List<Reservation> reservationsAsList = loadReservationsAsList(Tables.getTables());

        List<Reservation> resListWithoutRemovedReservation = reservationsAsList.stream()
                .filter(r -> !(r.getId().equals(reservation.getId())))
                .collect(Collectors.toList());

        removePreviousContentFromCsv(reservationPath);

        for (Reservation reservationFromList : resListWithoutRemovedReservation) {
            saveReservationInCsv(reservationFromList);
        }
    }

    public static void editReservationEndTime(Reservation reservation, LocalDateTime newEndTime) {
        reservation.setEndTime(newEndTime);
        removeReservationFromFile(reservation);
        saveReservationInCsv(reservation);
    }

    /*** Files - shared functionality *********************************************************************************/

    private static List<String> getFileAsRawStringList(Path path) {
        try {
            return new ArrayList<>(Files.readAllLines(path));
        } catch (IOException ex) {
            System.out.println("Can't find this file!");
            LOG.warn("file not found");
            return new ArrayList<>();
        }
    }

    public static void checkDataStructure() {
        checkIfExistsAndCreateDataDirectory();
        checkIfExistsAndCreateCsvFile(hallsPath);
        checkIfExistsAndCreateCsvFile(tablesPath);
        checkIfExistsAndCreateCsvFile(reservationPath);
    }

    private static void checkIfExistsAndCreateDataDirectory() {
        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void checkIfExistsAndCreateCsvFile(Path path) {
        if (!Files.exists(path)) {
            try {
                Files.createFile(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void removePreviousContentFromCsv(Path path) {
        List<String> emptyList = new ArrayList<>();
        LOG.info("content removed");
        try {
            Files.write(path, emptyList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}