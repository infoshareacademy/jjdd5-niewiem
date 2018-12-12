package com.infoshareacademy.niewiem.dao;

import com.infoshareacademy.niewiem.pojo.Hall;
import com.infoshareacademy.niewiem.pojo.Reservation;
import com.infoshareacademy.niewiem.pojo.Table;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.time.LocalDateTime;
import java.util.List;

@Stateless
public class ReservationDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Inject
    private TableDao tableDao;

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

    public List<Reservation> findAllByTable(Table table){
        final Query query = entityManager
                .createQuery("SELECT r FROM Reservation r WHERE r.table = :table");
        query.setParameter("table", table);
        return query.getResultList();
    }

    public List<Reservation> findAllByHall(Hall hall){
        final Query query = entityManager
                .createQuery("SELECT r FROM Reservation r  WHERE r.table.hall = :hall");
        query.setParameter("hall", hall);
        return query.getResultList();
    }

    public List<Reservation> findAllActiveByHall(Hall hall){
        final Query query = entityManager
                .createQuery("SELECT r FROM Reservation r  WHERE (r.table.hall = :hall AND r.startTime < :now AND r.endTime > :now)");
        query.setParameter("hall", hall);
        query.setParameter("now", LocalDateTime.now());
        return query.getResultList();
    }
}