package com.infoshareacademy.niewiem.dto;

import com.infoshareacademy.niewiem.pojo.Table;

import java.time.Duration;

public class TableTimeLeft {
    private Table table;
    private Duration timeLeft;

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public Duration getTimeLeft() {
        return timeLeft;
    }

    public void setTimeLeft(Duration timeLeft) {
        this.timeLeft = timeLeft;
    }
}
