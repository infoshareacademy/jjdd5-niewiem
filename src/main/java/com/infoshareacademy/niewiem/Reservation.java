package com.infoshareacademy.niewiem;

import java.time.LocalDateTime;
import java.util.Optional;

public class Reservation {
    Table table;
    LocalDateTime startTime;
    LocalDateTime stopTime;
    Optional<String> customer;


    public Reservation(Table table, LocalDateTime startTime, LocalDateTime stopTime) {
        this.table = table;
        this.startTime = startTime;
        this.stopTime = stopTime;
    }

    public Reservation(Table table, LocalDateTime startTime, LocalDateTime stopTime, String customer) {
        this.table = table;
        this.startTime = startTime;
        this.stopTime = stopTime;
        this.customer = Optional.of(customer);
    }

    public Table getTable() {
        return table;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getStopTime() {
        return stopTime;
    }

    public Optional<String> getCustomer() {
        return customer;
    }
}
