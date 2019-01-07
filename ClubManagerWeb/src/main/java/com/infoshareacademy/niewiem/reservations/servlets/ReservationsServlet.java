package com.infoshareacademy.niewiem.reservations.servlets;

import com.infoshareacademy.niewiem.halls.dto.HallDTO;
import com.infoshareacademy.niewiem.halls.services.ActiveHallService;
import com.infoshareacademy.niewiem.reservations.publishers.PeriodListPublisher;
import com.infoshareacademy.niewiem.reservations.publishers.ReservationsListPublisher;
import com.infoshareacademy.niewiem.services.ServletService;
import com.infoshareacademy.niewiem.tables.publishers.TablesListPublisher;
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
    private ServletService servletService;

    @Inject
    private ActiveHallService activeHallService;

    @Inject
    private ReservationsListPublisher reservationsListPublisher;

    @Inject
    private TablesListPublisher tablesListPublisher;

    @Inject
    private PeriodListPublisher periodListPublisher;


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Map<String, Object> model = new HashMap<>();

        List<String> errors = new ArrayList<>();
        model.put("errors", errors);
        List<String> warnings = new ArrayList<>();
        model.put("warnings", warnings);

        HallDTO hallDTO = activeHallService.getActiveHall(req.getSession());

        reservationsListPublisher.publishRequestedReservations(model, errors, warnings, req);

        tablesListPublisher.publishTablesInHall(model, hallDTO);
        tablesListPublisher.publishTableTypes(model);
        periodListPublisher.publishPeriods(model);

        LOG.info("Servlet had: {} errors.", errors.size());
        LOG.info("Servlet had: {} warnings.", warnings.size());
        servletService.sendModelToTemplate(resp, getServletContext(), model, VIEW_NAME);
    }
}