package com.infoshareacademy.niewiem;

public class Table implements Comparable<Table> {
    private Hall hall;
    private final TableType type;
    private final Integer tableId;
    private final String tableName;

    public Table(Hall hall, TableType type, Integer tableId, String tableName) {
        this.hall = hall;
        this.type = type;
        this.tableId = tableId;
        this.tableName = tableName;
    }

    public void setHall(Hall hall) {
        this.hall = hall;
    }

    public Hall getHall() {
        return hall;
    }

    public TableType getType() {
        return type;
    }

    public Integer getTableId() {
        return tableId;
    }

    public String getTableName() {
        return tableName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Table table = (Table) o;
        return this.tableId == table.getTableId();
    }

    @Override
    public int compareTo(Table t) {
        return this.tableName.compareTo(t.getTableName());
    }
}