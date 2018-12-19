package com.infoshareacademy.niewiem.reservations.dao;

import com.infoshareacademy.niewiem.pojo.Hall;
import com.infoshareacademy.niewiem.pojo.Reservation;
import com.infoshareacademy.niewiem.pojo.Table;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.time.LocalDateTime;
import java.util.List;

@Stateless
public class ReservationDao {
    private static final Logger LOG = LoggerFactory.getLogger(ReservationDao.class);


    @PersistenceContext
    private EntityManager entityManager;

    public Long save(Reservation reservation){
        entityManager.persist(reservation);
        return reservation.getId();
    }

    public Reservation update (Reservation reservation){
        LOG.info("Updating: ");
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

    public Reservation findActiveForTable(Table table) {
        final Query query = entityManager
                .createQuery("SELECT r FROM Reservation r WHERE (r.table = :table AND r.startTime < :now AND r.endTime > :now)");
        query.setParameter("table", table);
        query.setParameter("now", LocalDateTime.now());
        List resultList = query.getResultList();
        if(resultList == null || resultList.isEmpty()){
            return null;
        }
        return (Reservation) resultList.get(0);
    }

    public Reservation getConflictingReservation(Reservation reservation){
        final Query query = entityManager
                .createQuery("SELECT r FROM Reservation r WHERE ((:table = table) AND NOT ((:startDT > r.endTime) OR (:endDT < r.startTime)))");
        query.setParameter("table", reservation.getTable());
        query.setParameter("startDT", reservation.getStartTime());
        query.setParameter("endDT", reservation.getEndTime());

        return (Reservation) query.getSingleResult();
    }

    public boolean isInConflict(Reservation reservation){
        final Query query = entityManager
                .createQuery("SELECT r FROM Reservation r WHERE ((:table = table) AND NOT ((:startDT > r.endTime) OR (:endDT < r.startTime)))");
        query.setParameter("table", reservation.getTable());
        query.setParameter("startDT", reservation.getStartTime());
        query.setParameter("endDT", reservation.getEndTime());
        return query.getResultList().size() > 0;
    }
}