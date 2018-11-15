import java.util.Objects;

public class Table implements Comparable<Table> {
    // tableID - String, int?
    private int tableID;
    private TableType type;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Table table = (Table) o;
        return tableID == table.tableID && type == table.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(tableID, type);
    }

    @Override
    public int compareTo(Table o) {
        // sort table by number
        return 0;
    }
}
