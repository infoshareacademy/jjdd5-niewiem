package com.infoshareacademy.niewiem.web;

import com.infoshareacademy.niewiem.pojo.Hall;
import com.infoshareacademy.niewiem.pojo.Reservation;
import com.infoshareacademy.niewiem.pojo.Table;
import com.infoshareacademy.niewiem.services.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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
    private TableQueryService tableQueryService;

    @Inject
    private ReservationSaveService reservationSaveService;

    @Inject
    private ReservationUpdateService reservationUpdateService;

    @Inject
    private ReservationQueryService reservationQueryService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Hall hall = activeHallService.getActiveHallOrRedirect(req.getSession(), resp);

        resp.addHeader("Content-Type", "text/html; charset=utf-8");
        ServletContext context = getServletContext();
        Map<String, Object> model = new HashMap<>();

        model.put("bodyTemplate", VIEW_NAME + ".ftlh");

        String idParam = req.getParameter("id");
        if(idParam == null || idParam.isEmpty()){
            LOG.info("Recieved no reservation id. Creating a new one.");
        }else{
            LOG.info("Recieved reservation id: " + idParam);
            Reservation reservation = reservationQueryService.findById(idParam);
            model.put("reservation", reservation);
        }

        List<Table> tables = tableQueryService.findAllInHall(hall);
        model.put("tables", tables);

        servletService.sendModelToTemplate(resp, context, model);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String action = req.getParameter("action");

        if ("new".equals(action)) {
            reservationSaveService.addReservationFromServlet(req);
        } else if ("update".equals(action)) {
            resp.sendRedirect("/reservations");
//            reservationUpdateService.updateReservation(req);
        }
        resp.sendRedirect("/tables-view");
    }
}
