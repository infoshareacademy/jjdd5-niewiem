package com.infoshareacademy.niewiem;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class FileHandler {

    // read files with time logs for a given hall
    // read files with table data specific to a given hall
    private  Path path;
    private  Path tablesPath;
    private  Path hallsPath;
    private  Path historyPath;
    private  Path reservationsPath;


    public FileHandler() {
        this.path = Paths.get("data");
        this.tablesPath = Paths.get(path.toString(),"tables.csv");
        this.hallsPath = Paths.get(path.toString(), "halls.csv");
        this.historyPath = Paths.get(path.toString(), "history.csv");
        this.reservationsPath = Paths.get(path.toString(), "reservation.csv");
    }


    public void saveDataToFile(List<Table> tables, Hall hall) {

        List<String> out = new ArrayList<>();

        try {
            for (Table table : tables) {
                List<String> properties = Arrays.asList(
                        String.valueOf(table.getTableID()),
                        String.valueOf(table.getType()),
                        String.valueOf(hall.getName())
                );

                String singleEntry = properties.stream().collect(Collectors.joining(","));
                out.add(singleEntry);
            }
            Files.write(tablesPath, out);

        } catch (IOException ex) {
            System.out.println("Can't save this file!");
        }
    }

    public List<Table> readFromTables() {

        List<Table> tables = new LinkedList<>();

        try {
            for (String line : Files.readAllLines(tablesPath)) {
                tables.add(createTable(line.split(",")));
            }

        } catch (IOException ex) {
            System.out.println("Can't find this file!");
        }

        return tables;
    }

    public List<Table> readFromTables(String club) {

        List<Table> tables = new LinkedList<>();

        try {
            for (String line : Files.readAllLines(tablesPath)) {
                //if
                tables.add(createTable(line.split(",")));
            }

        } catch (IOException ex) {
            System.out.println("Can't find this file!");
        }

        return tables;
    }

    public Table createTable(String [] splittedLine) {
        return new Table(Integer.valueOf(splittedLine[0]), TableType.valueOf(splittedLine[1]));
    }
}




