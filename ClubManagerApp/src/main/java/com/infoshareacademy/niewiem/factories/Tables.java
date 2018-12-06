package com.infoshareacademy.niewiem.factories;

import com.infoshareacademy.niewiem.Hall;
import com.infoshareacademy.niewiem.Table;
import com.infoshareacademy.niewiem.TableType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.OptionalInt;

public class Tables {

    private static final Logger LOG = LogManager.getLogger(Tables.class);

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
        LOG.info("new table created: [{}]", table.toString());
        hall.getTableList().add(table);
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