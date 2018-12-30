package com.infoshareacademy.niewiem.reservations.dto;

import com.infoshareacademy.niewiem.tables.dto.TableDTO;

public class ReservationInMillisDTO {
    private Long id;
    private TableDTO table;
    private Long startMillis;
    private Long endMillis;
    private String customer;

    public ReservationInMillisDTO() {
        // empty constructor
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TableDTO getTable() {
        return table;
    }

    public void setTable(TableDTO table) {
        this.table = table;
    }

    public Long getStartMillis() {
        return startMillis;
    }

    public void setStartMillis(Long startMillis) {
        this.startMillis = startMillis;
    }

    public Long getEndMillis() {
        return endMillis;
    }

    public void setEndMillis(Long endMillis) {
        this.endMillis = endMillis;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }
}
