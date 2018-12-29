package com.infoshareacademy.niewiem.reservations.services;

import com.infoshareacademy.niewiem.reservations.dao.ReservationDao;
import com.infoshareacademy.niewiem.domain.Reservation;
import com.infoshareacademy.niewiem.services.RequestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

@Stateless
public class ReservationUpdateService {
    private static final Logger LOG = LoggerFactory.getLogger(ReservationUpdateService.class);

    @Inject
    private ReservationDao reservationDao;

    @Inject
    private RequestService requestService;

    public Reservation update(Reservation reservation) {
        // todo: validate me like you validate your French girls!

        if (reservationDao.isInConflict(reservation)) {
            Reservation conflict = reservationDao.getConflictingReservation(reservation);

            LOG.info("Reservation to update: " + reservation.getId());
            LOG.info("Conflicting reservation to update: " + conflict.getId());

            boolean isNotTheSameReservation = !conflict.getId().equals(reservation.getId());

            if(isNotTheSameReservation) {
                LOG.info("Reservation was in conflict with one already in database. Did not save or delete.");
                return reservation;
            }
            LOG.info("Reservation was in conflict with itself. Silly reservation.");

        }
        return reservationDao.update(reservation);
    }

    public void updateReservation(HttpServletRequest req) {
        update(requestService.getReservationWithId(req));
    }
}
