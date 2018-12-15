package com.infoshareacademy.niewiem.web;

import com.infoshareacademy.niewiem.pojo.Hall;
import com.infoshareacademy.niewiem.services.ActiveHallService;
import com.infoshareacademy.niewiem.services.HallQueryService;
import com.infoshareacademy.niewiem.services.ServletService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
            showHallsToChoose(resp);
            return;
        }
        redirectToChosenHall(req, resp, hid);
    }

    private void redirectToChosenHall(HttpServletRequest req, HttpServletResponse resp, String hid) throws IOException {
        HttpSession session = req.getSession();
        boolean hallExists = activeHallService.setActive(session, hid);

        if(hallExists) {
            resp.sendRedirect("/tables-view");
            return;
        }
        showHallsToChoose(resp);
    }

    private void showHallsToChoose(HttpServletResponse resp) throws IOException {
        resp.addHeader("Content-Type", "text/html; charset=utf-8");
        ServletContext context = getServletContext();
        Map<String, Object> model = new HashMap<>();

        model.put("bodyTemplate", VIEW_NAME + ".ftlh");
        addListOfHallsToModel(model);

        servletService.sendModelToTemplate(resp, context, model);
    }

    private void addListOfHallsToModel(Map<String, Object> model) throws IOException {
        List<Hall> halls = hallQueryService.findAll();
        LOG.info("Found {} halls in halls table", halls.size());

        model.put("halls", halls);
    }
}
