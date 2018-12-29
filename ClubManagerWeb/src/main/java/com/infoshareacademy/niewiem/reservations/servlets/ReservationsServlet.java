package com.infoshareacademy.niewiem.reservations.servlets;

import com.infoshareacademy.niewiem.pojo.Hall;
import com.infoshareacademy.niewiem.pojo.Reservation;
import com.infoshareacademy.niewiem.reservations.services.ReservationQueryService;
import com.infoshareacademy.niewiem.services.ServletService;
import com.infoshareacademy.niewiem.shared.filters.ActiveHallService;
import com.infoshareacademy.niewiem.tables.validators.TableValidator;
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

    @Inject
    private TableValidator tableValidator;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, Object> model = new HashMap<>();
        List<String> errors = new ArrayList<>();
        model.put("errors", errors);

        Hall hall = activeHallService.getActiveHall(req.getSession());
        if(tidParamExists(req, errors, hall)){
            Integer tid = Integer.parseInt(req.getParameter("tid"));
            printResrvationsByTable(model, tid);
        }else{
            printReservationsByHall(model, hall);
        }

        servletService.sendModelToTemplate(resp, getServletContext(), model, VIEW_NAME);
    }

    private boolean tidParamExists(HttpServletRequest req, List<String> errors, Hall hall) {

        String tidParam = req.getParameter("tid");
        if (StringUtils.isEmpty(tidParam)) {
            LOG.info("No table id parameter in request.");
            return false;
        }
        if (tableValidator.validateParamIdIsNotNumeric(tidParam, errors)) {
            return false;
        }

        Integer tid = Integer.parseInt(tidParam);
        if (tableValidator.validateTableIdDoesNotExists(tid, errors)) {
            return false;
        }
        if (tableValidator.validateTableIdDoesNotExistInActiveHallId(tid, hall.getId(), errors)) {
            return false;
        }

        return true;
    }

    private void printReservationsByHall(Map<String, Object> model, Hall hall) {
        // todo: this should not be reservations, but views!
        List<Reservation> reservations = reservationQueryService.findAllByHall(hall);
        model.put("reservations", reservations);
    }

    private void printResrvationsByTable(Map<String, Object> model, Integer tid) {
        // todo: this should not be reservations, but views!
        List<Reservation> reservations = reservationQueryService.findAllByTableId(tid);
        model.put("reservations", reservations);
    }
}