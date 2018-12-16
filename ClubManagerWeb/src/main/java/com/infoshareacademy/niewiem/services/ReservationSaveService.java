package com.infoshareacademy.niewiem.services;

import com.infoshareacademy.niewiem.dao.ReservationDao;
import com.infoshareacademy.niewiem.pojo.Reservation;
import com.infoshareacademy.niewiem.pojo.Table;
import com.infoshareacademy.niewiem.services.validators.InputValidator;
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
    private InputValidator inputValidator;

    @Inject
    private TableQueryService tableQueryService;

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
        String tidString = req.getParameter("tid");
        Integer tid = inputValidator.reqIntegerValidator(tidString);

        String startDateString = req.getParameter("startDate");
        String startTimeString = req.getParameter("startTime");
        LocalDateTime start = inputValidator.reqDateTime(startDateString, startTimeString);

        String timeSpanString = req.getParameter("timeSpan");
        Integer timeSpan = inputValidator.reqIntegerValidator(timeSpanString);

        String customer = req.getParameter("customer");

        addReservation(tid, start, timeSpan, customer);
    }

    public void addReservation(Integer tableId, LocalDateTime start, Integer timeSpanMinutes, String customer) {
        LocalDateTime end = start.plusMinutes(timeSpanMinutes);
        addReservation(tableId, start, end, customer);
    }

    public void addReservation(Integer tableId, LocalDateTime start, LocalDateTime end, String customer) {
        Table table = tableQueryService.findById(tableId);
        addReservation(table, start, end, customer);
    }

    public void addReservation(Table table, LocalDateTime start, LocalDateTime end, String customer) {
        Reservation reservation = new Reservation();

        reservation.setTable(table);
        reservation.setStartTime(start);
        reservation.setEndTime(end);

        if (customer == null) {
            reservation.setCustomer("");
        } else {
            reservation.setCustomer(customer);
        }

        save(reservation);
    }

}
