package com.infoshareacademy.niewiem.factories;

import com.infoshareacademy.niewiem.Hall;
import com.infoshareacademy.niewiem.Table;
import com.infoshareacademy.niewiem.TableType;

import java.util.OptionalInt;

public class Tables {
    public static void create(Hall hall, String name, TableType type) {
        Integer tableID = getNextAvailableId(hall);
        load(hall, type, tableID, name);
        // todo: save table to tables.csv, DataProvider.saveTableInCsv()
    }

    public static Integer getNextAvailableId(Hall hall){
        OptionalInt nextAvailableId = hall.getTableList().stream()
                .mapToInt(Table::getTableId)
                .max();
        if(nextAvailableId.isPresent()){
            return nextAvailableId.getAsInt() + 1;
        }
        return 1;
    }

    public static void load(Hall hall, TableType type, Integer tableID, String name) {
        Table table = new Table(hall, type, tableID, name);
        hall.getTableList().add(table);
    }
}