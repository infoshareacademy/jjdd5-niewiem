package com.infoshareacademy.niewiem;

import java.util.Objects;

public class Table implements Comparable<Table> {
    // tableId - String, int?
    private int tableId;
    private TableType type;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Table table = (Table) o;
        return tableId == table.tableId && type == table.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(tableId, type);
    }

    @Override
    public int compareTo(Table o) {
        // sort table by number
        return 0;
    }

    public Table(int tableId, TableType type) {
        this.tableId = tableId;
        this.type = type;
    }

    @Override
    public String toString() {
        return "Table{" +
                "tableId=" + tableId +
                ", type=" + type +
                '}';
    }
}
