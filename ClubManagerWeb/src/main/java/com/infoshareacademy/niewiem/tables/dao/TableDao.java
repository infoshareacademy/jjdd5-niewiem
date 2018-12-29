package com.infoshareacademy.niewiem.tables.dao;

import com.infoshareacademy.niewiem.pojo.Hall;
import com.infoshareacademy.niewiem.pojo.Table;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Stateless
public class TableDao {

    @PersistenceContext
    private EntityManager entityManager;

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

    public List<Table> findAllInHall(Hall hall) {
        final Query query = entityManager
                .createQuery("SELECT t FROM Table t WHERE t.hall = :hall");
        query.setParameter("hall", hall);

        return query.getResultList();
    }

    public boolean contains(Table table) {
        final Query query = entityManager.createQuery("SELECT t FROM Table t WHERE t = :table");
        query.setParameter("table", table);

        return query.getResultList().size() > 0;
    }

    public boolean contains(Integer tid) {
        final Query query = entityManager.createQuery("SELECT t FROM Table t WHERE t.id = :tid");
        query.setParameter("tid", tid);

        return query.getResultList().size() > 0;
    }
}