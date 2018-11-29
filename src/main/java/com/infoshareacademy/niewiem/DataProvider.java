package com.infoshareacademy.niewiem;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class DataProvider {

    // read files with time logs for a given hall
    // read files with table data specific to a given hall

    private static final String SEPARATOR = ",";
    private Path path;
    private Path tablesPath;
    private Path hallsPath;
    private Path reservationPath;


    public DataProvider() {
        this.path = Paths.get("data");
        this.tablesPath = Paths.get(path.toString(), "tables.csv");
        this.hallsPath = Paths.get(path.toString(), "halls.csv");
        this.reservationPath = Paths.get(path.toString(), "reservation.csv");
    }

    public void saveDataToFile(Set <Table> tables) {

        Set<String> out = new HashSet<>();

        try {
            for (Table table : tables) {
                Set<String> properties = new HashSet<>();
                properties.add(String.valueOf(table.getTableId()));
                properties.add(String.valueOf(table.getType()));
                properties.add(String.valueOf(table.getClubName()));


                String singleEntry = properties.stream().collect(Collectors.joining(SEPARATOR));
                out.add(singleEntry);
            }
            Files.write(tablesPath, out);

        } catch (IOException ex) {
            System.out.println("Can't save this file!");
        }
    }

    public Set<Table> readFromTables() {
        return readFromTables(null, getFileAsLineList());
    }

    public Set<Table> readFromTables(String club) {
        return readFromTables(club, getFileAsLineList());
    }

    Set<Table> readFromTables(String club, Set<String> fileByLines) {
        Set<Table> tables = new HashSet<>();

            for (String line : fileByLines) {
                if (club == null) {
                    tables.add(createTable(line.split(SEPARATOR)));
                } else if(line.contains(club)) {
                    tables.add(createTable(line.split(SEPARATOR)));
                }
            }

        return tables;
    }

    private Set<String> getFileAsLineList() {
        try {
            return new HashSet<>(Files.readAllLines(tablesPath));
        } catch (IOException ex) {
            System.out.println("Can't find this file!");
            return new HashSet<>();
        }
    }

    public Table createTable(String [] splittedLine) {
        return new Table(Integer.valueOf(splittedLine[0].trim()), TableType.valueOf(splittedLine[1].trim()), String.valueOf(splittedLine[2].trim()));
    }
}




