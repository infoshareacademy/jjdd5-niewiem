package com.infoshareacademy.niewiem.services;

import com.infoshareacademy.niewiem.dao.ReservationDao;
import com.infoshareacademy.niewiem.pojo.Reservation;
import com.infoshareacademy.niewiem.services.validators.ReservationValidator;
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

    @Inject
    private ReservationValidator reservationValidator;

    public Reservation update(Reservation reservation) {
        // todo: validate me like you validate your French girls!
        if(!reservationValidator.isResIdNotNull(reservation)){
            throwException("Reservation didn't update because id is null");
        }

        if(!reservationValidator.isStartTimeNotNull(reservation)){
            throwException("Reservation didn't update because start time is null");
        }

        if(reservationValidator.isEndTimeNotNull(reservation)){
            throwException("Reservation didn't update because end time is null");
        }
        if (reservationDao.isInConflict(reservation)) {
            Reservation conflict = reservationDao.getConflictingReservation(reservation);

            LOG.info("Reservation to update: " + reservation.getId());
            LOG.info("Conflicting reservation to update: " + conflict.getId());

            boolean isNotTheSameReservation = !conflict.getId().equals(reservation.getId());

            if (isNotTheSameReservation) {
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

    private void throwException(String message) {
        LOG.warn(message);
        throw new IllegalArgumentException(message);
    }
}
