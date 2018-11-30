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
        this.hallsPath = Paths.get(path.toString(), "halls.csv");
        this.tablesPath = Paths.get(path.toString(), "tables.csv");
        this.reservationPath = Paths.get(path.toString(), "reservations.csv");
    }

    /*public void saveDataToFile(List <Table> tables) {

        List<String> out = new HashSet<>();

        try {
            for (Table table : tables) {
                List<String> properties = new ArrayList<>();
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

    public Map<Integer,String> readFromHalls() {
        return readFromHalls(getFileAsLineList(hallsPath));
    }

    public List<Table> readFromTables(Hall hall) {
        return readFromTables(hall, getFileAsLineList(tablesPath));
    }

//    public Table readFromReservations(List<Table> tables){
//        return readFromReservations(getFileAsLineList(reservationPath));
//    }


    private Map<Integer,String> readFromHalls(List<String> fileByLines) {
        Map<Integer,String> halls = new HashMap<>();

        for (String line : fileByLines) {
            Hall hall = createHall(line.split(SEPARATOR));
            halls.put(hall.getHallId(), hall.getName());
        }

        return halls;
    }

    private List<Table> readFromTables(Hall hall,List<String> fileByLines) {
        List<Table> tables = new ArrayList<>();

        for (String line : fileByLines) {
            Table tableToAdd = createTable(hall,line.split(SEPARATOR));
            if(tableToAdd != null) {
                tables.add(tableToAdd);
            }
        }
        return tables;
    }

    private Table readFromReservations(List<String> fileByLines){

    }

    private List <String> getFileAsLineList(Path path) {
        try {
            return new ArrayList<>(Files.readAllLines(path));
        } catch (IOException ex) {
            System.out.println("Can't find this file!");
            return new ArrayList<>();
        }
    }

    private Hall createHall(String [] splittedLine) {
        return new Hall(Integer.valueOf(splittedLine[0].trim()),String.valueOf((splittedLine[1].trim())));
    }

    private Table createTable(Hall hall,String [] splittedLine) {

        Integer hallIDfromFile = Integer.valueOf(splittedLine[0].trim());

        if(hallIDfromFile.equals(hall.getHallId())) {
            TableType tableType = TableType.valueOf(splittedLine[1].trim());
            Integer tableId = Integer.valueOf(splittedLine[2].trim());
            String tableName = String.valueOf(splittedLine[3].trim());
            return new Table(hall, tableType, tableId, tableName);
        }

        return null;
    }
}




