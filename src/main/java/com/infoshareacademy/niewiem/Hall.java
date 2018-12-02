package com.infoshareacademy.niewiem;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import com.infoshareacademy.niewiem.DataProvider;

import static com.infoshareacademy.niewiem.TableType.POOL;
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

    public void loadExistingHall(){
        this.tableList = DataProvider.returnTablesListFromFile(this);
        this.reservations = DataProvider.returnReservationsFromFile(tableList);
    }

    public Integer getHallId() {
        return hallId;
    }

    public List<Reservation> getReservations() {
        return reservations;
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
        String tableID = "P" + String.format("%02d", tableNumber);
        TableType tableType = POOL;
        int tableOnList = tableList.indexOf(new Table(tableID, tableType));
        return tableList.get(tableOnList);
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

    public boolean addTable(TableType type) {
        int nextAvailableID = tableList.size() + 1;
        String newTableID = "P" + String.format("%02d", nextAvailableID);
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
        return Collections.unmodifiableList(tableList);
    }

    public String getName() {
        return name;
    }
}