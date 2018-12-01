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

    private static final String SEPARATOR = ";";
    private Path path;
    private Path tablesPath;
    private Path hallsPath;
    private Path reservationPath;


    public DataProvider() {
        this.path = Paths.get("test-hall");
        this.tablesPath = Paths.get(path.toString(), "tables.csv");
        this.hallsPath = Paths.get(path.toString(), "halls.csv");
        this.reservationPath = Paths.get(path.toString(), "reservation.csv");
    }

    /*public void saveDataToFile(Set <Table> tables) {

        Set<String> out = new HashSet<>();

        try {
            for (Table table : tables) {
                Set<String> properties = new HashSet<>();
                properties.add(String.valueOf(table.getHall()));
                properties.add(String.valueOf(table.getTableId()));
                properties.add(String.valueOf(table.getType()));
                properties.add(String.valueOf(table.getTableName()));


                String singleEntry = properties.stream().collect(Collectors.joining(SEPARATOR));
                out.add(singleEntry);
            }
            Files.write(tablesPath, out);

        } catch (IOException ex) {
            System.out.println("Can't save this file!");
        }
    }*/


    public List<Hall> readFromHalls() {
        return readFromHalls(getFileAsLineList());
    }

    public List<Table> readFromTables() {
        return readFromTables(getFileAsLineList());
    }

    private List<Table> readFromTables(List<String> fileByLines) {
        List<Table> tables = new ArrayList<>();

        fileByLines.remove(0);

        for (String line : fileByLines) {
            tables.add(createTable(line.split(SEPARATOR)));
        }

        tables.retainAll(readFromHalls());

        return tables;
    }

    private List<Hall> readFromHalls(List<String> fileByLines) {
        List<Hall> halls = new ArrayList<>();

        fileByLines.remove(0);

            for (String line : fileByLines) {
                halls.add(createHall(line.split(SEPARATOR)));
            }

        return halls;
    }

    private List <String> getFileAsLineList() {
        try {
            return new ArrayList<>(Files.readAllLines(hallsPath));
        } catch (IOException ex) {
            System.out.println("Can't find this file!");
            return new ArrayList<>();
        }
    }

    private Hall createHall(String [] splittedLine) {
        return new Hall(Integer.valueOf(splittedLine[0].trim()),String.valueOf((splittedLine[1].trim())));
    }

    private Table createTable(String [] splittedLine) {
        return new Table(this.(splittedLine[0].trim()),TableType.valueOf(splittedLine[1].trim()), Integer.valueOf(splittedLine[2].trim()),String.valueOf(splittedLine[3].trim()));
    }
}




