package com.infoshareacademy.niewiem.services;

import com.infoshareacademy.niewiem.dao.ReservationDao;
import com.infoshareacademy.niewiem.services.validators.InputValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

@Stateless
public class ReservationDeleteService {
    private static final Logger LOG = LoggerFactory.getLogger(ReservationSaveService.class);

    @Inject
    private ReservationDao reservationDao;

    @Inject
    private InputValidator inputValidator;

    public void delete(Long id) {
        // todo: validate me like you validate your French girls!
        reservationDao.delete(id);
    }

    public void delete(String idString) {
        Long id = inputValidator.reqLongValidator(idString);
        LOG.info("Got reservation id of: " + id + "sending to delete.");
        delete(id);
    }

    public void delete(HttpServletRequest req) {
        String idString = req.getParameter("rid");
        delete(idString);
    }
}