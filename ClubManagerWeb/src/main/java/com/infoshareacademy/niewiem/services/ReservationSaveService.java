package com.infoshareacademy.niewiem.services;

import com.infoshareacademy.niewiem.dao.ReservationDao;
import com.infoshareacademy.niewiem.pojo.Reservation;
import com.infoshareacademy.niewiem.services.validators.ReservationValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class ReservationSaveService {
    private static final Logger LOG = LoggerFactory.getLogger(ReservationSaveService.class);

    @Inject
    private ReservationDao reservationDao;

    @Inject
    private ReservationValidator reservationValidator;

    public Long save(Reservation reservation) {

        if(reservationValidator.isResIdNotNull(reservation)){
            LOG.warn("Reservation didn't save because id is not null");
            return -1l;
        }

        if(reservationValidator.isStartTimeNotNull(reservation)){
            LOG.warn("Reservation didn't save because start time is not null");
            return -1l;
        }

        if(reservationValidator.isEndTimeNotNull(reservation)){
            LOG.warn("Reservation didn't save because end time is not null");
            return -1l;
        }

        // todo: validate me like you validate your French girls!
        // apart from usual
        // check if reservation has no conflict
        // check if end is after start
        // time between start and end should not exceed... 24h? Some validation is needed.
        return reservationDao.save(reservation);
    }
}
