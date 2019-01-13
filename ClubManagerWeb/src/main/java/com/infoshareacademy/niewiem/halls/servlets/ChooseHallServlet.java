package com.infoshareacademy.niewiem.halls.servlets;

import com.infoshareacademy.niewiem.halls.publishers.HallListPublisher;
import com.infoshareacademy.niewiem.halls.services.ActiveHallService;
import com.infoshareacademy.niewiem.services.ServletService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
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
    private ActiveHallService activeHallService;

    @Inject
    private HallListPublisher hallListPublisher;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        List<String> errors = new ArrayList<>();
        Map<String, Object> model = new HashMap<>();

        String hidParam = req.getParameter("hid");
        if (StringUtils.isNotEmpty(hidParam)) {
            LOG.debug("Requested hall id is: {}", hidParam);
            if(activeHallService.setActive(req.getSession(), hidParam, errors)){
                LOG.info("Requested hall exists, redirecting to tables-view");
                resp.sendRedirect("/tables-view");
                return;
            }
        }
        activeHallService.setNull(req.getSession());
        hallListPublisher.publishHallList(model);

        servletService.sendModelToTemplate(req, resp, getServletContext(), model, VIEW_NAME);
    }
}
