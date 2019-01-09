package com.infoshareacademy.niewiem.halls.servlets;

import com.infoshareacademy.niewiem.halls.services.HallQueryService;
import com.infoshareacademy.niewiem.domain.Hall;
import com.infoshareacademy.niewiem.services.ServletService;
import com.infoshareacademy.niewiem.halls.services.ActiveHallService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("choose-hall")
public class ChooseHallServlet extends HttpServlet {
    private static final String VIEW_NAME = "/choose-hall";
    private static final Logger LOG = LoggerFactory.getLogger(ChooseHallServlet.class);

    @Inject
    private ServletService servletService;

    @Inject
    private HallQueryService hallQueryService;

    @Inject
    private ActiveHallService activeHallService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String hid = req.getParameter("hallId");
        if(hid == null || hid.isEmpty()){
            activeHallService.setNull(req.getSession());
            printHallChoice(resp);
            return;
        }

        redirectToTablesInChosenHall(req, resp, hid);
    }

    private void redirectToTablesInChosenHall(HttpServletRequest req, HttpServletResponse resp, String hid) throws IOException {
        boolean hallExists = activeHallService.setActive(req.getSession(), hid);
        if(hallExists) {
            resp.sendRedirect("/tables-view");
            return;
        }
        printHallChoice(resp);
    }

    private void printHallChoice(HttpServletResponse resp) throws IOException {
        Map<String, Object> model = new HashMap<>();
        List<Hall> halls = hallQueryService.findAll();
        LOG.info("Found {} halls in halls table", halls.size());

        model.put("halls", halls);
        servletService.sendModelToTemplate(resp, getServletContext(), model, VIEW_NAME);
    }
}
