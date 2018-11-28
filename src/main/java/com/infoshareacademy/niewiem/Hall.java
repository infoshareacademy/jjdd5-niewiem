package com.infoshareacademy.niewiem;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Hall {
    private String name;
    private List<Table> tableList;
    private Map<Table, Boolean> activeTables; // shows list of tables,
    private Map<Table, Reservation> reservations;

    public boolean addReservation(){
        // todo: check if reservation possible
        // todo: save in file
        return false;
    }

    public boolean startGame(Table table, Reservation reservation){
        reservations.put(table, reservation);
        return false;
    }

    public Hall(String name, List<Table> tableList) {
        this.name = name;
        this.tableList = tableList;
    }

    public Hall(String name) {
        this.name = name;
        this.tableList = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public boolean addTable(TableType type) {
        // this addTable is used when adding new table from console, so id is decided by app tableList.size()++
        Table newTable = new Table(tableList.size(), type);
        tableList.add(newTable);

        // add table to file

        // adds table to a list
        // saves it to a file in a currently open hall
        // check if table doesn't currently exist
        return false;
    }

    private boolean removeTable(Table table) {
        // rm table
        if (existsInTableList(table)) {
            tableList.remove(table);
            return true;
        }
        // check if is on the list
        // check if is active, if active => ??
        //                          - rm with no other action
        //                          - don't remove, exception
        //                          - rm and cut timeSpan in log? (timeSpan = currentTime - startingTime)
        return false;
    }

    private boolean existsInTableList(Table table) {
        return tableList.contains(table);
    }

    public List<Table> getTableList() {
        return tableList;
    }

    // some getter that gives printer data on what to print
    // can't be getTable

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Hall hall = (Hall) o;
        return Objects.equals(name, hall.name) &&
                Objects.equals(tableList, hall.tableList) &&
                Objects.equals(activeTables, hall.activeTables);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, tableList, activeTables);
    }
}
