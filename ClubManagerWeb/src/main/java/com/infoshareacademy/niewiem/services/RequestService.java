package com.infoshareacademy.niewiem.services;

import com.infoshareacademy.niewiem.domain.Reservation;
import com.infoshareacademy.niewiem.domain.Table;
import com.infoshareacademy.niewiem.services.validators.InputValidator;
import com.infoshareacademy.niewiem.tables.services.TableQueryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Stateless
public class RequestService {
    private static final Logger LOG = LoggerFactory.getLogger(RequestService.class);

    @Inject
    private InputValidator inputValidator;

    @Inject
    private TableQueryService tableQueryService;



    public Reservation getReservationWithId(HttpServletRequest req) {
        Reservation reservation = getReservation(req);
        reservation.setId(getReservationid(req));
        return reservation;
    }

    public Long getReservationid(HttpServletRequest req){
        String ridString = req.getParameter("rid");
        return inputValidator.reqLongValidator(ridString);
    }

    public Reservation getReservation(HttpServletRequest req) {
        String tidString = req.getParameter("tid");
        Integer tid = inputValidator.reqIntegerValidator(tidString);

        String startDateString = req.getParameter("startDate");
        String startTimeString = req.getParameter("startTime");
        LocalDateTime start = inputValidator.reqDateTime(startDateString, startTimeString);

        String timeSpanString = req.getParameter("timeSpan");
        Integer timeSpan = inputValidator.reqIntegerValidator(timeSpanString);

        String customer = req.getParameter("customer");

        return getReservation(tid, start, timeSpan, customer);
    }

    public Reservation getReservation(Integer tableId, LocalDateTime start, Integer timeSpanMinutes, String customer) {
        LocalDateTime end = start.plusMinutes(timeSpanMinutes);
        return getReservation(tableId, start, end, customer);
    }

    public Reservation getReservation(Integer tableId, LocalDateTime start, LocalDateTime end, String customer) {
        Table table = tableQueryService.findById(tableId);
        return getReservation(table, start, end, customer);
    }

    public Reservation getReservation(Table table, LocalDateTime start, LocalDateTime end, String customer) {
        Reservation reservation = new Reservation();

        reservation.setTable(table);
        reservation.setStartTime(start);
        reservation.setEndTime(end);

        if (customer == null) {
            reservation.setCustomer("");
        } else {
            reservation.setCustomer(customer);
        }
        return reservation;
    }
}