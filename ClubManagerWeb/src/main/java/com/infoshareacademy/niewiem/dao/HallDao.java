package com.infoshareacademy.niewiem.dao;

import com.infoshareacademy.niewiem.pojo.Hall;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class HallDao {

    @PersistenceContext
    private EntityManager entityManager;

    public Integer save(Hall h){
        entityManager.persist(h);
        return h.getId();
    }
}
