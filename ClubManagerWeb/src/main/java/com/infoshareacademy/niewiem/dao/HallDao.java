package com.infoshareacademy.niewiem.dao;

import com.infoshareacademy.niewiem.pojo.Hall;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Stateless
public class HallDao {

    @PersistenceContext
    private EntityManager entityManager;

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

    public Hall findById(Integer id){
        return entityManager.find(Hall.class, id);
    }

    public List<Hall> findAll(){
        final Query query = entityManager.createQuery("SELECT hall FROM Hall hall");
        return query.getResultList();
    }
}