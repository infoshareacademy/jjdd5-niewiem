package com.infoshareacademy.niewiem.halls.servlets;

import com.infoshareacademy.niewiem.halls.publishers.HallPublisher;
import com.infoshareacademy.niewiem.halls.services.HallSaveService;
import com.infoshareacademy.niewiem.halls.services.HallUpdateService;
import com.infoshareacademy.niewiem.services.ServletService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@MultipartConfig
@WebServlet("/hall")
public class HallServlet extends HttpServlet {
    private static final String VIEW_NAME = "/hall";
    private static final Logger LOG = LoggerFactory.getLogger(HallServlet.class);

    @Inject
    private ServletService servletService;

    @Inject
    private HallPublisher hallPublisher;

    @Inject
    private HallSaveService hallSaveService;

    @Inject
    private HallUpdateService hallUpdateService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, Object> model = new HashMap<>();

        List<String> errors = new ArrayList<>();
        model.put("errors", errors);

        String hidParam = req.getParameter("hid");
        hallPublisher.publishRequestedHall(model, errors, hidParam);

        LOG.info("Servlet had: {} errors.", errors.size());
        servletService.sendModelToTemplate(req, resp, getServletContext(), model, VIEW_NAME);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        List<String> errors = new ArrayList<>();

        if ("new".equals(action)) {
            LOG.info("Saving new hall.");
            hallSaveService.createNewHall(req, errors);
        } else if ("update".equals(action)) {
            LOG.info("Saving new hall.");
            hallUpdateService.update(req, errors);
        }
        resp.sendRedirect("/choose-hall");
    }
}
