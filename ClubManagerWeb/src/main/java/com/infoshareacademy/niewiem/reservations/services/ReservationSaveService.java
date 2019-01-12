package com.infoshareacademy.niewiem.reservations.services;

import com.infoshareacademy.niewiem.reservations.dao.ReservationDao;
import com.infoshareacademy.niewiem.domain.Reservation;
import com.infoshareacademy.niewiem.services.RequestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Stateless
public class ReservationSaveService {
    private static final Logger LOG = LoggerFactory.getLogger(ReservationSaveService.class);

    @Inject
    private ReservationDao reservationDao;

    @Inject
    private RequestService requestService;

    public Long save(Reservation reservation) {




        // todo: validate me like you validate your French girls!
        //  apart from usual
        //  check if reservation has no conflict
        //  check if end is after start
        //  check if table exist
        //  check if it exist in given hall
        //  time between start and end should not exceed... 24h? Some validation is needed
        if (reservationDao.isInConflict(reservation)) {
            LOG.info("Reservation was in conflict with one already in database. Did not save.");
            return -1L;
        }

        return reservationDao.save(reservation);
    }

    public void addReservationFromServlet(HttpServletRequest req) {
        save(requestService.getReservation(req));
    }

    public void addReservation(Integer tableId, LocalDateTime start, Integer timeSpanMinutes, String customer) {
        Reservation reservation = requestService.getReservation(tableId, start, timeSpanMinutes, customer);
        save(reservation);
    }
}
