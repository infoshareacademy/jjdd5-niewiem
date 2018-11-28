package com.infoshareacademy.niewiem;

public class Table {
    private String tableId;
    private TableType type;

    public Table(String tableId, TableType type) {
        this.tableId = tableId;
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Table table = (Table) o;
        return tableId.equals(table.tableId) && type == table.type;
    }
}
