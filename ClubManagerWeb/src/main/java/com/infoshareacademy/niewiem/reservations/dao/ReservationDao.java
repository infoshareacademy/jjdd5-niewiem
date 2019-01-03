package com.infoshareacademy.niewiem.reservations.dao;

import com.infoshareacademy.niewiem.domain.Reservation;
import com.infoshareacademy.niewiem.enums.TableType;
import com.infoshareacademy.niewiem.reservations.enums.Exclusivity;
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

    // SAVE, UPDATE, DELETE --------------------------------------------------------------------------------------------

    public Long save(Reservation reservation) {
        LOG.info("Saving reservation: {}", reservation);
        entityManager.persist(reservation);
        return reservation.getId();
    }

    public Reservation update(Reservation reservation) {
        LOG.info("Updating reservation: {}", reservation);
        return entityManager.merge(reservation);
    }

    public void delete(Long id) {
        final Reservation reservation = entityManager.find(Reservation.class, id);
        if (reservation != null) {
            LOG.info("Deleting reservation: {}", reservation);
            entityManager.remove(reservation);
        } else {
            LOG.warn("Could not find reservation with ID: {} to delete.", id);
        }
    }

    // QUERIES RETURNING BOOLEAN ---------------------------------------------------------------------------------------

    public boolean isInConflict(Reservation reservation) {
        final Query query = conflictReservations(reservation);

        int conflictsFound = query.getResultList().size();
        LOG.info("Found {} conflicts with provided reservation.", conflictsFound);
        return conflictsFound > 0;
    }

    // QUERIES RETURNING SINGLE RESULT ---------------------------------------------------------------------------------

    public Reservation findById(Long id) {
        return entityManager.find(Reservation.class, id);
    }

    public Reservation getConflictingReservation(Reservation reservation) {
        final Query query = conflictReservations(reservation);
        Reservation conflictingReservation = (Reservation) query.getSingleResult();

        LOG.info("Reservation in conflict: {}", conflictingReservation);

        return conflictingReservation;
    }

    public Reservation findActiveForTable(Integer tid) {
        final Query query = entityManager
                .createQuery("SELECT r FROM Reservation r WHERE (r.table.id = :tid AND r.startTime < :now AND r.endTime > :now)");
        query.setParameter("tid", tid);
        query.setParameter("now", LocalDateTime.now());

        List resultList = query.getResultList();
        if (resultList == null || resultList.isEmpty()) {
            LOG.info("Found no active reservation for table with id: {}", tid);
            return null;
        }
        Reservation reservation = (Reservation) resultList.get(0);
        LOG.info("Found an active reservation: {} for table with id: {}", reservation, tid);
        return reservation;
    }

    // QUERIES RETURNING LISTS -----------------------------------------------------------------------------------------

    public List<Reservation> findAll() {
        final Query query = entityManager.createQuery("SELECT r FROM Reservation r");

        List resultList = query.getResultList();
        LOG.info("Found {} total reservations.", resultList.size());
        return resultList;
    }

    public List<Reservation> findByHallId(Integer hid) {
        final Query query = entityManager
                .createQuery("SELECT r FROM Reservation r WHERE r.table.hall.id = :hid");
        query.setParameter("hid", hid);

        List resultList = query.getResultList();
        LOG.info("Found {} reservations in hall with id: {}.", resultList.size(), hid);
        return resultList;
    }

    public List<Reservation> findActiveByHall(Integer hid) {
        final Query query = entityManager
                .createQuery("SELECT r FROM Reservation r WHERE (r.table.hall.id = :hid AND r.startTime < :now AND r.endTime > :now)");
        query.setParameter("hid", hid);
        query.setParameter("now", LocalDateTime.now());

        List resultList = query.getResultList();
        LOG.info("Found {} active reservations in hall with id: {}.", resultList.size(), hid);
        return resultList;
    }

    public List<Reservation> findByHallAndType(Integer hid, TableType type) {
        final Query query = entityManager
                .createQuery("SELECT r FROM Reservation r WHERE (r.table.hall.id = :hid AND r.table.type = :type)");
        query.setParameter("hid", hid);
        query.setParameter("type", type);

        List resultList = query.getResultList();
        LOG.info("Found {} reservations in hall with id: {} for table type {}.", resultList.size(), hid, type);
        return resultList;
    }

    public List<Reservation> findByTableId(Integer tid) {
        final Query query = entityManager.createQuery("SELECT r FROM Reservation r WHERE r.table.id = :tid");
        query.setParameter("tid", tid);

        List resultList = query.getResultList();
        LOG.info("Found {} reservations for table with ID: {}", resultList.size(), tid);
        return resultList;
    }

    public List<Reservation> findByHallIdAndTimeSpanExclusivityOption(Integer hid, LocalDateTime start, LocalDateTime end, Exclusivity exclusivity) {
        final Query query = entityManager
                .createQuery("SELECT r FROM Reservation r WHERE (" +
                        "(r.table.hall.id = :hid) AND " +
                        exclusivity.getQuery() + ")");
        query.setParameter("hid", hid);
        query.setParameter("start", start);
        query.setParameter("end", end);

        List resultList = query.getResultList();
        LOG.info("Found {} reservations in hall with id: {} in time span: {} -> {} ({})", resultList.size(), hid, start, end, exclusivity.getMessage());
        return resultList;
    }

    public List<Reservation> findByTableIdAndTimeSpanExclusivityOption(Integer tid, LocalDateTime start, LocalDateTime end, Exclusivity exclusivity) {
        final Query query = entityManager
                .createQuery("SELECT r FROM Reservation r WHERE (" +
                        "(r.table.id = :tid) AND " +
                        exclusivity.getQuery() + ")");
        query.setParameter("tid", tid);
        query.setParameter("start", start);
        query.setParameter("end", end);

        List resultList = query.getResultList();
        LOG.info("Found {} reservations for table with id: {} in time span: {} -> {} ({})", resultList.size(), tid, start, end, exclusivity.getMessage());
        return resultList;
    }

    public List<Reservation> findByHallIdAndTypeAndTimeSpanExclusivityOption(Integer hid, TableType type, LocalDateTime start, LocalDateTime end, Exclusivity exclusivity) {
        final Query query = entityManager
                .createQuery("SELECT r FROM Reservation r WHERE (" +
                        "(r.table.hall.id = :hid) AND " +
                        "(r.table.type = :type) AND " +
                        exclusivity.getQuery() + ")");
        query.setParameter("hid", hid);
        query.setParameter("type", type);
        query.setParameter("start", start);
        query.setParameter("end", end);

        List resultList = query.getResultList();
        LOG.info("Found {} reservations in hall with id: {} for table type: {} in time span: {} -> {} ({})", resultList.size(), hid, type, start, end, exclusivity.getMessage());
        return resultList;
    }

    // QUERIES RETURNING QUERY -----------------------------------------------------------------------------------------

    private Query conflictReservations(Reservation reservation) {
        final Query query = entityManager
                .createQuery("SELECT r FROM Reservation r WHERE ((:table = table) AND NOT ((:startDT > r.endTime) OR (:endDT < r.startTime)))");
        query.setParameter("table", reservation.getTable());
        query.setParameter("startDT", reservation.getStartTime());
        query.setParameter("endDT", reservation.getEndTime());

        return query;
    }
}