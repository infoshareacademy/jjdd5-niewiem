package com.infoshareacademy.niewiem.pojo;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;


@Entity
@javax.persistence.Table(name = "reservations")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "start")
    private LocalDateTime startTime;

    @Column(name = "end")
    private LocalDateTime endTime;

    @Column(name = "customer")
    private String customer;

    @ManyToOne
    @JoinColumn(name = "table_id")
    private Table table;

    public Reservation() {
    }

    public Reservation(Table table, LocalDateTime startTime, LocalDateTime endTime, String customer) {
        this.table = table;
        this.startTime = startTime;
        this.endTime = endTime;
        this.customer = customer;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    // Business logic for Console UI -----------------------------------------------------------------------------------

    public boolean isInProgress() {
        boolean startsBeforeNow = startTime.isBefore(LocalDateTime.now());
        boolean endsAfterNow = endTime.isAfter(LocalDateTime.now());

        return startsBeforeNow && endsAfterNow;
    }

    public boolean isOver() {
        return endTime.isBefore(LocalDateTime.now());
    }

    public boolean isUpcoming() {
        return startTime.isAfter(LocalDateTime.now());
    }

    public boolean isUpcomingOrInProgress() {
        return endTime.isAfter(LocalDateTime.now());
    }

    public Long getTimeRemainingInSeconds() {
        LocalDateTime now = LocalDateTime.now();
        return now.until(endTime, ChronoUnit.SECONDS);
    }

    // Needed for console app - DataProvider ---------------------------------------------------------------------------

    public String toCsvString(Table table) {
        StringBuffer sb = new StringBuffer();
        sb.append(table.getId()).append(";").append(startTime).append(";").append(endTime).append(";").append(customer);
        return sb.toString();
    }

}
