package com.infoshareacademy.niewiem.services;

import com.infoshareacademy.niewiem.pojo.Hall;
import com.infoshareacademy.niewiem.services.validators.InputValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;


@Stateless
public class ActiveHallService {
    private static final Logger LOG = LoggerFactory.getLogger(ActiveHallService.class);

    @Inject
    private HallQueryService hallQueryService;

    @Inject
    private InputValidator inputValidator;

    public Hall getActiveHall(HttpSession session) {
        Hall hall = (Hall) session.getAttribute("activeHall");

        if (hall == null) {
            return null;
        }
        if (hallQueryService.doesNotExist(hall)) {
            return null;
        }
        return hall;
    }

    public boolean setActive(HttpSession session, String hid) {
        Integer hallId = inputValidator.reqIntegerValidator(hid);
        Hall hall = hallQueryService.findById(hallId);

        if (hall == null) {
            return false;
        }

        // todo: what happens when attribute already exists?
        session.setAttribute("activeHall", hall);
        return true;
    }


}


