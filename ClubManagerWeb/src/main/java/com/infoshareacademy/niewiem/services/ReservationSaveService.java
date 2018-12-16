package com.infoshareacademy.niewiem.services;

import com.infoshareacademy.niewiem.dao.ReservationDao;
import com.infoshareacademy.niewiem.exceptions.SavingFailed;
import com.infoshareacademy.niewiem.pojo.Reservation;
import com.infoshareacademy.niewiem.services.validators.ReservationValidator;
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

    @Inject
    private ReservationValidator reservationValidator;

    public Long save(Reservation reservation) throws SavingFailed {

        if(reservationValidator.isResIdNotNull(reservation)){
            throwException("Reservation didn't save because id is not null");
        }

        if(reservationValidator.isStartTimeNotNull(reservation)){
            throwException("Reservation didn't save because start time is not null");
        }

        if(reservationValidator.isEndTimeNotNull(reservation)){
            throwException("Reservation didn't save because end time is not null");
        }

        if(!reservationValidator.isEndAfterStartTime(reservation)){
            throwException("Reservation didn't save because end time is not after start time");
        }

        // todo: validate me like you validate your French girls!
        // check if reservation has no conflict
        // time between start and end should not exceed... 24h? Some validation is needed.
        return reservationDao.save(reservation);
    }

    public void addNewReservation(HttpServletRequest req) throws SavingFailed {
        String tidString = req.getParameter("tid");
        String startDateString = req.getParameter("startDate");
        String startTimeString = req.getParameter("startTime");
        String timeSpanString = req.getParameter("timeSpan");
        String customer = req.getParameter("customer");

        Integer tid = inputValidator.reqIntegerValidator(tidString);
        // todo: should validate if table exist
        // todo: should validate if table exists in active hall
        Table table = tableQueryService.findById(tid);

        LocalDateTime startDateTime = inputValidator.reqDateTime(startDateString, startTimeString);
        Integer timeSpan = inputValidator.reqIntegerValidator(timeSpanString);
        LocalDateTime endDateTime = startDateTime.plusMinutes(timeSpan);

        Reservation reservation = new Reservation();
        reservation.setStartTime(startDateTime);
        reservation.setEndTime(endDateTime);
        reservation.setTable(table);
        reservation.setCustomer(customer);

        save(reservation);
    }

    private void throwException(String message) throws SavingFailed {
        LOG.warn(message);
        throw new SavingFailed(message);
    }
}
