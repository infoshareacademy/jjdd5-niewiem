package com.infoshareacademy.niewiem.dao;

import com.infoshareacademy.niewiem.dto.TableTimeLeft;
import com.infoshareacademy.niewiem.pojo.Hall;
import com.infoshareacademy.niewiem.pojo.Reservation;
import com.infoshareacademy.niewiem.pojo.Table;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.time.Duration;
import java.time.LocalDateTime;
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

    public List<TableTimeLeft> findAllTablesInHallWithTimeLeftOrZero(Hall hall) {
        List<Table> tables = findAllInHall(hall);
        List<Reservation> reservations = reservationDao.findAllActiveByHall(hall);

        return tables.stream()
                .map(t -> {
                    TableTimeLeft ttl = new TableTimeLeft();
                    ttl.setTable(t);
                    Duration timeLeft = reservations.stream()
                            .filter(r -> r.getTable() == t)
                            .map(r -> Duration.between(r.getEndTime(), LocalDateTime.now()))
                            .findFirst()
                            .orElse(Duration.ZERO);
                    ttl.setTimeLeft(timeLeft);
                    return ttl;
                })
                .collect(Collectors.toList());
    }
}