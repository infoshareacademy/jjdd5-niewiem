package com.infoshareacademy.niewiem.halls.dao;

import com.infoshareacademy.niewiem.domain.Hall;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Stateless
public class HallDao {

    @PersistenceContext
    private EntityManager entityManager;

    // SAVE, UPDATE, DELETE --------------------------------------------------------------------------------------------

    public Integer save(Hall hall){
        entityManager.persist(hall);
        return hall.getId();
    }

    public Hall update (Hall hall){
        return entityManager.merge(hall);
    }

    public void delete(Integer id){
        final Hall hall = entityManager.find(Hall.class, id);
        if(hall != null){
            entityManager.remove(hall);
        }
    }

    // QUERIES RETURNING BOOLEAN ---------------------------------------------------------------------------------------

    public boolean doesExist(Integer hid){
        final Query query = entityManager.createQuery("SELECT h FROM Hall h WHERE h.id = :hid");
        query.setParameter("hid", hid);

        return query.getResultList().size() > 0;
    }

    // QUERIES RETURNING SINGLE RESULT ---------------------------------------------------------------------------------

    public Hall findById(Integer id){
        return entityManager.find(Hall.class, id);
    }

    // QUERIES RETURNING LISTS -----------------------------------------------------------------------------------------

    public List<Hall> findAll(){
        final Query query = entityManager.createQuery("SELECT hall FROM Hall hall");
        return query.getResultList();
    }
}