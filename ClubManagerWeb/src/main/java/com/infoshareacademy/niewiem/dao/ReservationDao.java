package com.infoshareacademy.niewiem.dao;

import com.infoshareacademy.niewiem.pojo.Reservation;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Stateless
public class ReservationDao {

    @PersistenceContext
    private EntityManager entityManager;

    public Long save(Reservation reservation){
        entityManager.persist(reservation);
        return reservation.getId();
    }

    public Reservation update (Reservation reservation){
        return entityManager.merge(reservation);
    }

    public void delete(Long id){
        final Reservation reservation = entityManager.find(Reservation.class, id);
        if(reservation != null){
            entityManager.remove(reservation);
        }
    }

    public Reservation findById(Long id){
        return entityManager.find(Reservation.class, id);
    }

    public List<Reservation> findAll(){
        final Query query = entityManager.createQuery("SELECT reservation FROM Reservation reservation");
        return query.getResultList();
    }
}