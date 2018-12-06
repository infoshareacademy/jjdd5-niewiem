package com.infoshareacademy.niewiem;

import java.util.ArrayList;
import java.util.List;

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

    public String toCSVString(){
        StringBuffer sb = new StringBuffer();
        sb.append(hallId).append(";").append(name);
        return sb.toString();
    }
}