package com.infoshareacademy.niewiem.reservations.servlets;

import com.infoshareacademy.niewiem.domain.Reservation;
import com.infoshareacademy.niewiem.halls.dto.HallDTO;
import com.infoshareacademy.niewiem.halls.services.ActiveHallService;
import com.infoshareacademy.niewiem.reservations.services.ReservationDeleteService;
import com.infoshareacademy.niewiem.reservations.services.ReservationQueryService;
import com.infoshareacademy.niewiem.reservations.services.ReservationSaveService;
import com.infoshareacademy.niewiem.reservations.services.ReservationUpdateService;
import com.infoshareacademy.niewiem.services.ServletService;
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
import java.util.HashMap;
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
    private TablesListPublisher tablesListPublisher;

    @Inject
    private ReservationSaveService reservationSaveService;

    @Inject
    private ReservationUpdateService reservationUpdateService;

    @Inject
    private ReservationDeleteService reservationDeleteService;

    @Inject
    private ReservationQueryService reservationQueryService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, Object> model = new HashMap<>();

        HallDTO hallDTO = activeHallService.getActiveHall(req.getSession());

        // todo: this logic should go to publisher
        String idParam = req.getParameter("id");
        if(idParam == null || idParam.isEmpty()){
            LOG.info("Recieved no reservation id. Creating a new one.");
        }else{
            LOG.info("Recieved reservation id: " + idParam);
            Reservation reservation = reservationQueryService.findById(idParam);
            model.put("reservation", reservation);
        }

        tablesListPublisher.publishTablesInHall(model, hallDTO);

        servletService.sendModelToTemplate(resp, getServletContext(), model, VIEW_NAME);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String action = req.getParameter("action");

        if ("new".equals(action)) {
            LOG.info("Adding new reservation.");
            reservationSaveService.addReservationFromServlet(req);
        } else if ("update".equals(action)) {
            LOG.info("Updating reservation.");
            reservationUpdateService.updateReservation(req);
        }else if ("delete".equals(action)) {
            LOG.info("Deleting reservation.");
            reservationDeleteService.delete(req);
        }
        resp.sendRedirect("/tables-view");
    }
}