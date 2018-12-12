package com.infoshareacademy.niewiem.pojo;

import javax.persistence.*;
import java.util.Set;


@Entity
@javax.persistence.Table(name = "halls")
public class Hall {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "hall", fetch = FetchType.LAZY)
    private Set<Table> tables;

    public Hall() {
    }

    public Hall(String name) {
        this.name = name;
    }

    public Hall(Integer hallId, String name) {
        this.id = hallId;
        this.name = name;
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


    public Set<Table> getTables() {
        return tables;
    }

    public void setTables(Set<Table> tables) {
        this.tables = tables;
    }

    // Business logic for Console UI -----------------------------------------------------------------------------------
    // Needed for console app - DataProvider ---------------------------------------------------------------------------

    public String toCsvString(){
        StringBuffer sb = new StringBuffer();
        sb.append(id).append(";").append(name);
        return sb.toString();
    }
}