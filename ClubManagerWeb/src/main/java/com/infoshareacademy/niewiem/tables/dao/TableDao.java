package com.infoshareacademy.niewiem.tables.dao;

import com.infoshareacademy.niewiem.domain.Hall;
import com.infoshareacademy.niewiem.domain.Table;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Stateless
public class TableDao {

    @PersistenceContext
    private EntityManager entityManager;

    // SAVE, UPDATE, DELETE --------------------------------------------------------------------------------------------

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

    // QUERIES RETURNING BOOLEAN ---------------------------------------------------------------------------------------

    public boolean doesExist(Integer tid) {
        final Query query = entityManager.createQuery("SELECT t FROM Table t WHERE t.id = :tid");
        query.setParameter("tid", tid);

        return query.getResultList().size() > 0;
    }

    // QUERIES RETURNING SINGLE RESULT ---------------------------------------------------------------------------------

    public Table findById(Integer id) {
        return entityManager.find(Table.class, id);
    }

    // QUERIES RETURNING LISTS -----------------------------------------------------------------------------------------

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
}