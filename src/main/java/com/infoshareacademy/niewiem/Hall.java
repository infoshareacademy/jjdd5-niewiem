package com.infoshareacademy.niewiem;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Hall {
    private String name;
    private List<Table> tableList;
    private List<Reservation> reservations;

    public Hall(String name) {
        this.name = name;
        this.tableList = new ArrayList<>();
        this.reservations = new ArrayList<>();
    }

    public boolean startGame(int tableNumber, int timeSpanInMinutes){

        Table table = getTable(tableNumber);
        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime endTime = startTime.plusMinutes(timeSpanInMinutes);

        Reservation reservation = new Reservation(table, startTime, endTime);

        reservations.add(reservation);
        return true;
    }

    private Table getTable(int tableNumber){
        // Until GUI we only have pool tables, so we are automatically adding "P" prefix
        String tableID = "P" + tableNumber;
        int tableOnList = tableList.indexOf(tableID);
        return tableList.get(tableOnList);
    }

    public boolean addTable(TableType type) {
        String newTableID = "P" + tableList.size();
        Table newTable = new Table(newTableID, type);
        tableList.add(newTable);
        return true;
    }

    private boolean removeTable(Table table) {
        if (existsInTableList(table)) {
            tableList.remove(table);
            return true;
        }
        return false;
    }

    private boolean existsInTableList(Table table) {
        return tableList.contains(table);
    }

    public List<Table> getTableList() {
        return tableList;
    }

    public String getName() {
        return name;
    }
}
