package com.infoshareacademy.niewiem.domain;

import com.infoshareacademy.niewiem.enums.TableType;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@javax.persistence.Table(name = "tables")
public class Table implements Comparable<Table> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private TableType type;

    @ManyToOne
    @JoinColumn(name = "hall_id", nullable = false)
    private Hall hall;

    @OneToMany(mappedBy = "table", fetch = FetchType.LAZY)
    private Set<Reservation> reservations;

    public Table() {
    }

    public Table(Hall hall, TableType type, String tableName) {
        this.hall = hall;
        this.type = type;
        this.name = tableName;
    }

    public Table(Hall hall, TableType type, Integer Id, String tableName) {
        this.hall = hall;
        this.type = type;
        this.id = Id;
        this.name = tableName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TableType getType() {
        return type;
    }

    public void setType(TableType type) {
        this.type = type;
    }

    public Hall getHall() {
        return hall;
    }

    public void setHall(Hall hall) {
        this.hall = hall;
    }

    public Set<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(Set<Reservation> reservations) {
        this.reservations = reservations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Table)) return false;
        Table table = (Table) o;
        return Objects.equals(getId(), table.getId()) &&
                Objects.equals(getName(), table.getName()) &&
                getType() == table.getType() &&
                Objects.equals(getHall(), table.getHall()) &&
                Objects.equals(getReservations(), table.getReservations());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getType(), getHall(), getReservations());
    }

    @Override
    public String toString() {
        return "Table{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type=" + type +
                '}';
    }

    // Business logic for Console UI -----------------------------------------------------------------------------------

    @Override
    public int compareTo(Table t) {
        return this.name.compareTo(t.getName());
    }

    // Needed for console app - DataProvider
    public String toCsvString(Hall hall){
        StringBuffer sb = new StringBuffer();
        sb.append(hall.getId()).append(";").append(type).append(";").append(id).append(";").append(name);
        return sb.toString();
    }

}