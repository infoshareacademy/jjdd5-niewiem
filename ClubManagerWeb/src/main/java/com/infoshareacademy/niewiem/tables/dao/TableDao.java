package com.infoshareacademy.niewiem.tables.dao;

import com.infoshareacademy.niewiem.domain.Hall;
import com.infoshareacademy.niewiem.domain.Table;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Stateless
public class TableDao {
    private static final Logger LOG = LoggerFactory.getLogger(TableDao.class);


    @PersistenceContext
    private EntityManager entityManager;

    // SAVE, UPDATE, DELETE --------------------------------------------------------------------------------------------

    public Integer save(Table table) {
        LOG.info("Saving table: {}", table);
        entityManager.persist(table);
        return table.getId();
    }

    public Table update(Table table) {
        LOG.info("Updating table: {}", table);
        return entityManager.merge(table);
    }

    public void delete(Integer id) {
        final Table table = entityManager.find(Table.class, id);
        if (table != null) {
            LOG.info("Deleting table: {}", table);
            entityManager.remove(table);
        }else{
            LOG.warn("Could not find table with id: {} to delete.", id);
        }
    }

    // QUERIES RETURNING BOOLEAN ---------------------------------------------------------------------------------------

    public boolean doesExist(Integer tid) {
        final Query query = entityManager.createQuery("SELECT t FROM Table t WHERE t.id = :tid");
        query.setParameter("tid", tid);

        int foundTables = query.getResultList().size();
        LOG.info("Found {} tables with ID: {} in database.", foundTables, tid);
        return foundTables > 0;
    }

    // QUERIES RETURNING SINGLE RESULT ---------------------------------------------------------------------------------

    public Table findById(Integer id) {
        return entityManager.find(Table.class, id);
    }

    // QUERIES RETURNING LISTS -----------------------------------------------------------------------------------------

    public List<Table> findAll() {
        final Query query = entityManager.createQuery("SELECT table FROM Table table");

        List resultList = query.getResultList();
        LOG.info("Found {} total tables.", resultList.size());
        return resultList;
    }

    public List<Table> findAllInHall(Hall hall) {
        final Query query = entityManager
                .createQuery("SELECT t FROM Table t WHERE t.hall = :hall");
        query.setParameter("hall", hall);

        List resultList = query.getResultList();
        LOG.info("Found {} tables in hall: {}.", resultList.size(), hall);
        return resultList;
    }
}