package com.infoshareacademy.niewiem.services;

import com.infoshareacademy.niewiem.pojo.Hall;
import com.infoshareacademy.niewiem.services.validators.InputValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


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

    public Hall getActiveHallOrRedirect(HttpSession session, HttpServletResponse resp) throws IOException {
        Hall hall = (Hall) session.getAttribute("activeHall");

        if (hall == null) {
            resp.sendRedirect("/choose-hall");
        }
        if (hallQueryService.doesNotExist(hall)) {
            resp.sendRedirect("/choose-hall");
        }
        return hall;
    }

    public boolean setActive(HttpSession session, String hid) {
        Integer hallId = inputValidator.reqIntegerValidator(hid);
        Hall hall = hallQueryService.findById(hallId);

        if (hall == null) {
            return false;
        }

        session.setAttribute("activeHall", hall);
        return true;
    }


}


