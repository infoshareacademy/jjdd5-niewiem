package com.infoshareacademy.niewiem.reservations.servlets;

import com.infoshareacademy.niewiem.reservations.publishers.ReservationsListPublisher;
import com.infoshareacademy.niewiem.services.ServletService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/reservations")
public class ReservationsServlet extends HttpServlet {
    private static final String VIEW_NAME = "/reservations";
    private static final Logger LOG = LoggerFactory.getLogger(ReservationsServlet.class);

    @Inject
    private ReservationsListPublisher reservationsListPublisher;

    @Inject
    private ServletService servletService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Map<String, Object> model = new HashMap<>();
        List<String> errors = new ArrayList<>();
        model.put("errors", errors);

        reservationsListPublisher.publishRequestedReservations(model, errors, req);

        LOG.info("Servlet had: {} errors.", errors.size());
        servletService.sendModelToTemplate(resp, getServletContext(), model, VIEW_NAME);
    }
}