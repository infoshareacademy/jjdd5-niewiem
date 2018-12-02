package com.infoshareacademy.niewiem;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toMap;

public class Hall {
    private Integer hallId;
    private String name;
    private List<Table> tableList;
    private List<Reservation> reservations;

    public Hall(Integer hallId, String name) {
        this.hallId = hallId;
        this.name = name;
        this.tableList = new ArrayList<>();
        this.reservations = new ArrayList<>();
    }

    public Map<Table, Long> getActiveTablesAndRemainingTimes(){
        return reservations.stream()
                .filter(Reservation::isInProgress)
                .collect(toMap(
                        Reservation::getTable,
                        Reservation::getTimeRemainingInSeconds
                ));
    }

    public Map<Table, Long> getAllTablesAndRemainingTimes(){
        Map<Table, Long> activeTables = getActiveTablesAndRemainingTimes();
        return tableList.stream()
                .collect(toMap(
                        t -> t,
                        t -> activeTables.getOrDefault(t, 0L)
                ));
    }

    private Table getTable(int tableId){
        return tableList.stream()
                .filter(table -> table.getTableId() == tableId)
                .findFirst()
                .orElse(null);
    }

    public Integer getHallId() {
        return hallId;
    }

    public String getName() {
        return name;
    }

    public List<Table> getTableList() {
        return tableList;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }
}