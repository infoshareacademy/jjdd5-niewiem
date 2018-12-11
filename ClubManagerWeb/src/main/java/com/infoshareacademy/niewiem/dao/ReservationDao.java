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
        // todo: check if reservation has no conflict
        entityManager.persist(reservation);
        return reservation.getId();
    }

    public Reservation update (Reservation reservation){
        // todo: check if reservation has no conflict
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
        final Query query = entityManager.createQuery("SELECT r FROM Reservation r");
        return query.getResultList();
    }

    //todo: find all active reservations for given table (needs left join)
//    SELECT *
//    FROM club.reservations as r
//    LEFT JOIN club.tables as t ON r.table_id = t.id
//    WHERE t.id = 1;

    //todo: find all active reservations in given hall (needs left join)
//    SELECT *
//    FROM club.reservations
//    LEFT JOIN club.tables ON table_id = club.tables.id
//    LEFT JOIN club.halls ON club.tables.hall_id = club.halls.id
//    WHERE halls.id = 3;

}