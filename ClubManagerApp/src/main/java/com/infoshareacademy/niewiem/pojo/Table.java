package com.infoshareacademy.niewiem.pojo;

import com.infoshareacademy.niewiem.enums.TableType;

import javax.persistence.*;
import java.util.Set;


@Entity
@javax.persistence.Table(name = "tables")
public class Table implements Comparable<Table> {

    // Fields ----------------------------------------------------------------------------------------------------------

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private TableType type;

    @ManyToOne
    @JoinColumn(name = "hall_id")
    private Hall hall;

    @OneToMany(mappedBy = "table", fetch = FetchType.EAGER)
    private Set<Reservation> reservations;

    // Constructors ----------------------------------------------------------------------------------------------------

    public Table() {
    }

    public Table(Hall hall, TableType type, Integer Id, String tableName) {
        this.hall = hall;
        this.type = type;
        this.id = Id;
        this.name = tableName;
    }

    // Getters and setters ---------------------------------------------------------------------------------------------

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

    // Business logic for Console UI -----------------------------------------------------------------------------------
    // Needed for console app - DataProvider ---------------------------------------------------------------------------

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Table table = (Table) o;
        return this.id == table.getId();
    }

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