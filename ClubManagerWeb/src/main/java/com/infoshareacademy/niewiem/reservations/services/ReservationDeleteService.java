package com.infoshareacademy.niewiem.reservations.services;

import com.infoshareacademy.niewiem.halls.dto.HallDTO;
import com.infoshareacademy.niewiem.reservations.dao.ReservationDao;
import com.infoshareacademy.niewiem.reservations.validators.ReservationValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class ReservationDeleteService {
    private static final Logger LOG = LoggerFactory.getLogger(ReservationSaveService.class);

    @Inject
    private ReservationDao reservationDao;

    @Inject
    private ReservationValidator reservationValidator;

    public void delete(String ridString, List<String> errors, HallDTO hallDTO) {
        // todo: When reservation is being deleted, maybe it should send an email to customer, when there is one?
        if(reservationValidator.validateRidParam(ridString, errors, hallDTO)){
            Long rid = Long.parseLong(ridString);

            LOG.info("Got reservation id of: " + rid + "sending to delete.");

            reservationDao.delete(rid);
        }
    }
}