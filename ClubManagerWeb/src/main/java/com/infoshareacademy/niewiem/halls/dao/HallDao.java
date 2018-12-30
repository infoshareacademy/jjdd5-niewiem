package com.infoshareacademy.niewiem.halls.dao;

import com.infoshareacademy.niewiem.domain.Hall;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Stateless
public class HallDao {
    private static final Logger LOG = LoggerFactory.getLogger(HallDao.class);

    @PersistenceContext
    private EntityManager entityManager;

    // SAVE, UPDATE, DELETE --------------------------------------------------------------------------------------------

    public Integer save(Hall hall){
        LOG.info("Saving hall: {}", hall);
        entityManager.persist(hall);
        return hall.getId();
    }

    public Hall update (Hall hall){
        LOG.info("Updating hall: {}", hall);
        return entityManager.merge(hall);
    }

    public void delete(Integer id){
        final Hall hall = entityManager.find(Hall.class, id);
        if(hall != null){
            LOG.info("Deleting hall: {}", hall);
            entityManager.remove(hall);
        }else{
            LOG.warn("Could not find hall with ID: {} to delete.", id);
        }
    }

    // QUERIES RETURNING BOOLEAN ---------------------------------------------------------------------------------------

    public boolean doesExist(Integer hid){
        final Query query = entityManager.createQuery("SELECT h FROM Hall h WHERE h.id = :hid");
        query.setParameter("hid", hid);

        int foundHalls = query.getResultList().size();
        LOG.info("Found {} halls with ID: {} in database.", foundHalls, hid);
        return foundHalls > 0;
    }

    // QUERIES RETURNING SINGLE RESULT ---------------------------------------------------------------------------------

    public Hall findById(Integer id){
        return entityManager.find(Hall.class, id);
    }

    // QUERIES RETURNING LISTS -----------------------------------------------------------------------------------------

    public List<Hall> findAll(){
        final Query query = entityManager.createQuery("SELECT hall FROM Hall hall");

        List resultList = query.getResultList();
        LOG.info("Found {} total halls.", resultList.size());
        return resultList;
    }
}