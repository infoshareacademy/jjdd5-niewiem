package com.infoshareacademy.niewiem.reservations.servlets;

import com.infoshareacademy.niewiem.pojo.Hall;
import com.infoshareacademy.niewiem.pojo.Reservation;
import com.infoshareacademy.niewiem.reservations.services.ReservationQueryService;
import com.infoshareacademy.niewiem.services.ServletService;
import com.infoshareacademy.niewiem.shared.filters.ActiveHallService;
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
import java.util.List;
import java.util.Map;

@WebServlet("/reservations")
public class ReservationsServlet extends HttpServlet {
    private static final String VIEW_NAME = "/reservations";
    private static final Logger LOG = LoggerFactory.getLogger(ReservationsServlet.class);

    @Inject
    private ActiveHallService activeHallService;

    @Inject
    private ReservationQueryService reservationQueryService;

    @Inject
    private ServletService servletService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, Object> model = new HashMap<>();

        Hall hall = activeHallService.getActiveHall(req.getSession());
        List<Reservation> reservations = reservationQueryService.findAllByHall(hall);
        model.put("reservations", reservations);

        servletService.sendModelToTemplate(resp, getServletContext(), model, VIEW_NAME);
    }

    private void handleTidParam(Map<String, String[]> paramsMap) {
        String tidParam = paramsMap.get("tid")[0];
        // todo: check for:
        // tid is present
        // tid is not present
        // tid is not numbers
        // tid does not exist
        // tid does not exist in active hall



    }
}