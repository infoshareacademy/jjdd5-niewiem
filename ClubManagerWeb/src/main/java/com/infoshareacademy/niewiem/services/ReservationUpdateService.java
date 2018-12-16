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
    private ReservationValidator reservationValidator;

    public Reservation update(Reservation reservation){

        if(!reservationValidator.isResIdNotNull(reservation)){
            LOG.warn("Reservation didn't update because id is null");
        }

        if(!reservationValidator.isStartTimeNotNull(reservation)){
            LOG.warn("Reservation didn't update because start time is null");
        }

        if(reservationValidator.isEndTimeNotNull(reservation)){
            LOG.warn("Reservation didn't update because end time is null");
        }

        return reservationDao.update(reservation);
    }
    public Reservation update(Reservation reservation){
        // todo: validate me like you validate your French girls!
        return reservationDao.update(reservation);
    }

    public void updateReservation(HttpServletRequest req) {
    }
}
