package com.infoshareacademy.niewiem;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

public class Reservation {
    private Table table;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Optional<String> customer;


    public Reservation(Table table, LocalDateTime startTime, LocalDateTime endTime) {
        this.table = table;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Reservation(Table table, LocalDateTime startTime, LocalDateTime endTime, String customer) {
        this.table = table;
        this.startTime = startTime;
        this.endTime = endTime;
        this.customer = Optional.of(customer);
    }

    public Table getTable() {
        return table;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public Optional<String> getCustomer() {
        return customer;
    }

    public boolean isInProgress(){
        boolean startsBeforeNow = startTime.isBefore(LocalDateTime.now());
        boolean endsAfterNow = endTime.isAfter(LocalDateTime.now());

        return startsBeforeNow && endsAfterNow;
    }

    public Long getTimeRemainingInSeconds(){
        LocalDateTime now = LocalDateTime.now();
        return now.until(endTime, ChronoUnit.SECONDS);
    }
}
