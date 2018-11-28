package com.infoshareacademy.niewiem;

public class Table implements Comparable<Table> {
    private final String tableId;
    private final TableType type;

    public Table(String tableId, TableType type) {
        this.tableId = tableId;
        this.type = type;
    }

    public String getTableId() {
        return tableId;
    }

    public TableType getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Table table = (Table) o;
        return tableId.equals(table.tableId) && type == table.type;
    }

    @Override
    public int compareTo(Table t) {
        return this.tableId.compareTo(t.getTableId());
    }
}