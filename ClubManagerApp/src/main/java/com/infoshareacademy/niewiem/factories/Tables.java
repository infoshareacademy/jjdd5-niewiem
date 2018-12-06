package com.infoshareacademy.niewiem.factories;

import com.infoshareacademy.niewiem.DataProvider;
import com.infoshareacademy.niewiem.Hall;
import com.infoshareacademy.niewiem.Table;
import com.infoshareacademy.niewiem.TableType;

import java.util.OptionalInt;

public class Tables {
    public static void create(Hall hall, String name, TableType type) {
        Integer tableID = getNextAvailableId(hall);
        Table table = load(hall, type, tableID, name);
        DataProvider.saveTableInCsv(table);
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

    public static Table load(Hall hall, TableType type, Integer tableID, String name) {
        Table table = new Table(hall, type, tableID, name);
        hall.getTableList().add(table);
        return table;
    }

    public static Table getTableByID(Hall hall, Integer tableId) {
        return hall.getTableList().stream()
                .filter(table -> table.getTableId() == tableId)
                .findFirst()
                .orElse(null);
    }

    public static void remove(Hall hall, Table table) {
        Reservations.stop(hall, table);
        Reservations.cancelAllFutureReservationsFromTable(hall, table);
        hall.getTableList().remove(table);
        // todo: remove table from file as well
    }
}