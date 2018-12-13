package com.infoshareacademy.niewiem.dao;

import com.infoshareacademy.niewiem.dto.TableEndTimeInMillis;
import com.infoshareacademy.niewiem.pojo.Hall;
import com.infoshareacademy.niewiem.pojo.Reservation;
import com.infoshareacademy.niewiem.pojo.Table;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class TableDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Inject
    private ReservationDao reservationDao;

    public Integer save(Table table) {
        entityManager.persist(table);
        return table.getId();
    }

    public Table update(Table table) {
        return entityManager.merge(table);
    }

    public void delete(Integer id) {
        final Table table = entityManager.find(Table.class, id);
        if (table != null) {
            entityManager.remove(table);
        }
    }

    public Table findById(Integer id) {
        return entityManager.find(Table.class, id);
    }

    public List<Table> findAll() {
        final Query query = entityManager.createQuery("SELECT table FROM Table table");
        return query.getResultList();
    }

    public List findAllInHall(Hall hall) {
        final Query query = entityManager
                .createQuery("SELECT t FROM Table t WHERE t.hall = :hall");
        query.setParameter("hall", hall);

        return query.getResultList();
    }

    public List<TableEndTimeInMillis> findAllTablesInHallWithEndTimeInMillis(Hall hall) {
        List<Table> tables = findAllInHall(hall);
        List<Reservation> reservations = reservationDao.findAllActiveByHall(hall);

        return tables.stream()
                .map(t -> {
                    TableEndTimeInMillis ttl = new TableEndTimeInMillis();
                    ttl.setTable(t);
                    Long timeLeft = reservations.stream()
                            .filter(r -> r.getTable() == t)
                            .map(r -> r.getEndTime().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli())
                            .findFirst()
                            .orElse(Long.valueOf(0));
                    ttl.setEndTimeInMillis(timeLeft);
                    return ttl;
                })
                .collect(Collectors.toList());
    }
}