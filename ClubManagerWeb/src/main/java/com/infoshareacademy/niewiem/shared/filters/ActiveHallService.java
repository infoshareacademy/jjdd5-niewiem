package com.infoshareacademy.niewiem.shared.filters;

import com.infoshareacademy.niewiem.halls.services.HallQueryService;
import com.infoshareacademy.niewiem.domain.Hall;
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
        return (Hall) session.getAttribute("activeHall");
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

    public void setNull(HttpSession session){
        session.setAttribute("activeHall", null);
    }
}