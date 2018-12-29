package com.infoshareacademy.niewiem.tables.dto;

import com.infoshareacademy.niewiem.domain.Table;

public class TableEndTimeInMillis {
    private Table table;
    private Long endTimeInMillis;

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public Long getEndTimeInMillis() {
        return endTimeInMillis;
    }

    public void setEndTimeInMillis(Long endTimeInMillis) {
        this.endTimeInMillis = endTimeInMillis;
    }
}
