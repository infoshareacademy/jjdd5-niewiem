package com.infoshareacademy.niewiem.reservations.servlets;

import com.infoshareacademy.niewiem.halls.dto.HallDTO;
import com.infoshareacademy.niewiem.halls.services.ActiveHallService;
import com.infoshareacademy.niewiem.reservations.publishers.ReservationPublisher;
import com.infoshareacademy.niewiem.reservations.services.ReservationDeleteService;
import com.infoshareacademy.niewiem.reservations.services.ReservationSaveService;
import com.infoshareacademy.niewiem.reservations.services.ReservationUpdateService;
import com.infoshareacademy.niewiem.services.ServletService;
import com.infoshareacademy.niewiem.tables.publishers.TablePublisher;
import com.infoshareacademy.niewiem.tables.publishers.TablesListPublisher;
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

@WebServlet("/reservation")
public class ReservationServlet extends HttpServlet {
    private static final String VIEW_NAME = "/reservation";
    private static final Logger LOG = LoggerFactory.getLogger(ReservationServlet.class);

    @Inject
    private ServletService servletService;

    @Inject
    private ActiveHallService activeHallService;

    @Inject
    private ReservationPublisher reservationPublisher;

    @Inject
    private TablePublisher tablePublisher;

    @Inject
    private TablesListPublisher tablesListPublisher;

    @Inject
    private ReservationSaveService reservationSaveService;

    @Inject
    private ReservationUpdateService reservationUpdateService;

    @Inject
    private ReservationDeleteService reservationDeleteService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, Object> model = new HashMap<>();

        List<String> errors = new ArrayList<>();
        model.put("errors", errors);

        String ridParam = req.getParameter("rid");
        HallDTO activeHall = activeHallService.getActive(req.getSession());

        reservationPublisher.publishReservationById(model, errors, ridParam, activeHall);

        tablesListPublisher.publishTablesInHall(model, activeHall);

        String tidParam = req.getParameter("tid");
        tablePublisher.publishTid(model, errors, tidParam, activeHall);

        servletService.sendModelToTemplate(req, resp, getServletContext(), model, VIEW_NAME);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String action = req.getParameter("action");
        List<String> errors = new ArrayList<>();
        HallDTO activeHall = activeHallService.getActive(req.getSession());

        // todo: decide what to do with errors, where should I go?
        if ("new".equals(action)) {
            LOG.info("Adding new reservation.");
            reservationSaveService.createNewReservation(req, errors, activeHall);
        } else if ("update".equals(action)) {
            LOG.info("Updating reservation.");
            reservationUpdateService.updateReservation(req, errors, activeHall);
        }else if ("delete".equals(action)) {
            LOG.info("Deleting reservation.");
            String ridParam = req.getParameter("rid");
            reservationDeleteService.delete(ridParam, errors, activeHall);
        }
        resp.sendRedirect("/tables-view");
    }
}
