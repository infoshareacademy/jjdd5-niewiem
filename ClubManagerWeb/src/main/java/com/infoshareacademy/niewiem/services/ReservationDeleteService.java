package com.infoshareacademy.niewiem.services;

import com.infoshareacademy.niewiem.dao.ReservationDao;
import com.infoshareacademy.niewiem.services.validators.ReservationValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class ReservationDeleteService {
    private static final Logger LOG = LoggerFactory.getLogger(ReservationDeleteService.class);

    @Inject
    private ReservationDao reservationDao;

    @Inject
    private ReservationValidator reservationValidator;

    public void delete(Long id) {

        if(reservationValidator.isIdNotNull(id)){
            reservationDao.delete(id);
        }
        LOG.warn("Reservation didn't delete because id is null");
        throw new IllegalArgumentException("Reservation with id '" + id + "' doesn't exist");
    }
}