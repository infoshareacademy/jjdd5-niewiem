package com.infoshareacademy.niewiem.shared.filters;


import com.infoshareacademy.niewiem.halls.services.HallQueryService;
import com.infoshareacademy.niewiem.pojo.Hall;

import javax.inject.Inject;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(
        filterName = "ActiveHallFilter",
        urlPatterns = {"/*"},
        initParams = {
                @WebInitParam(name = "minSalary", value = "100.0")
        })
public class ActiveHallFilter {

    @Inject
    private HallQueryService hallQueryService;

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
        Object hallObj = session.getAttribute("activeHall");
        if (hallObj == null) {
            resp.sendRedirect("/choose-hall");
        }

        Hall hall = (Hall) hallObj;
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

    public void setNull(HttpSession session){
        session.setAttribute("activeHall", null);
    }
}

}
