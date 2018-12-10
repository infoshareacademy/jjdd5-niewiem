package com.infoshareacademy.niewiem.repositories;

import com.infoshareacademy.niewiem.dao.DataProvider;
import com.infoshareacademy.niewiem.enums.TableType;
import com.infoshareacademy.niewiem.pojo.Hall;
import com.infoshareacademy.niewiem.pojo.Table;

import java.util.List;
import java.util.OptionalInt;

public class Tables {
    private static List<Table> tables;

    public static List<Table> getTables() {
        return tables;
    }

    public static void setTables(List<Table> tables) {
        Tables.tables = tables;
    }

    public static void create(Hall hall, String name, TableType type) {
        Integer tableID = getNextAvailableId();
        Table table = load(hall, type, tableID, name);
        DataProvider.saveTableInCsv(table);
    }

    public static Integer getNextAvailableId(){
        OptionalInt nextAvailableId = DataProvider.getSetOfSavedTablesIds().stream()
                .mapToInt(a -> a)
                .max();
        if(nextAvailableId.isPresent()){
            return nextAvailableId.getAsInt() + 1;
        }
        return 1;
    }

    public static Table load(Hall hall, TableType type, Integer tableID, String name) {
        Table table = new Table(hall, type, tableID, name);
        tables.add(table);
        return table;
    }

    public static Table getTableByID(Hall hall, Integer tableId) {
        return tables.stream()
                .filter(table -> table.getId() == tableId)
                .findFirst()
                .orElse(null);
    }

    public static void remove(Hall hall, Table table) {
        Reservations.stop(hall, table);
        Reservations.cancelAllFutureReservationsFromTable(hall, table);
        tables.remove(table);
        // todo: remove table from file as well
    }
}