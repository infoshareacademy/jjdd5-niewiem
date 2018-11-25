package com.infoshareacademy.niewiem;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {
    // read files with time logs for a given hall
    // read files with table data specific to a given hall

    public static void main(String[] args) {

        String fileName = "file.csv";

        List<Integer> tableList = new ArrayList<>();
        tableList.add(1);
        tableList.add(2);
        tableList.add(4);

        saveFile(fileName, tableList);
        List <String> reader = readFile(fileName);
        System.out.println(reader);

    }

    public static void saveFile(String fileName, List<Integer> tableList) {

        Path path = Paths.get("files", fileName);
        List<String> out = new ArrayList<>();

        for (int i = 0; i < tableList.size(); i++) {
            String row = tableList.get(i).toString();

            out.add(row);

            try {
                Files.write(path, out);
            } catch (IOException ex) {
                System.out.println("Can't save this file!");
            }
        }
    }

    public static List<String> readFile(String fileName) {

        Path path = Paths.get("files", fileName);

        List<String> out = new ArrayList<>();
        try {
            out = Files.readAllLines(path);
        } catch (IOException ex) {
            System.out.println("Can't find this file!");
        }
        return out;

    }
}




