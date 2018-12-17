package com.infoshareacademy.niewiem.services;

import com.infoshareacademy.niewiem.dao.ReservationDao;
import com.infoshareacademy.niewiem.pojo.Reservation;
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
            // todo: if reservation is in conflict only with itself it should allow it.
            //  it could be in conflict with more than one entity though.
            LOG.info("Reservation was in conflict with one already in database.");
//            LOG.info("Reservation: " + reservation);
            return reservation;
        }
        return reservationDao.update(reservation);
    }

    public void updateReservation(HttpServletRequest req) {
        update(requestService.getReservationWithId(req));
    }
}
