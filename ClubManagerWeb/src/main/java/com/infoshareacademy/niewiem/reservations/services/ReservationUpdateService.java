package com.infoshareacademy.niewiem.reservations.services;

import com.infoshareacademy.niewiem.domain.Reservation;
import com.infoshareacademy.niewiem.halls.dto.HallDTO;
import com.infoshareacademy.niewiem.reservations.dao.ReservationDao;
import com.infoshareacademy.niewiem.reservations.mappers.ReservationRequestMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Stateless
public class ReservationUpdateService {
    private static final Logger LOG = LoggerFactory.getLogger(ReservationUpdateService.class);

    @Inject
    private ReservationDao reservationDao;

    @Inject
    private ReservationRequestMapper reservationRequestMapper;

    public Reservation update(Reservation reservation, List<String> errors) {
        if (reservationDao.isInConflict(reservation)) {
            Reservation conflict = reservationDao.getConflictingReservation(reservation);

            LOG.info("Reservation to update: " + reservation.getId());
            LOG.info("Conflicting reservation to update: " + conflict.getId());

            boolean isNotTheSameReservation = !conflict.getId().equals(reservation.getId());

            if(isNotTheSameReservation) {
                LOG.info("Reservation was in conflict with one already in database. Did not save or delete.");
                errors.add("Reservation was in conflict with one already in database. Did not save or delete.");
                return reservation;
            }
            LOG.info("Reservation was in conflict with itself. Silly reservation.");

        }
        return reservationDao.update(reservation);
    }

    public void updateReservation(HttpServletRequest req, List<String> errors, HallDTO hallDTO) {
        Reservation reservation = reservationRequestMapper.getReservationWithId(req, errors, hallDTO);
        if(reservation == null){
            return;
        }
        update(reservation, errors);
    }
}
