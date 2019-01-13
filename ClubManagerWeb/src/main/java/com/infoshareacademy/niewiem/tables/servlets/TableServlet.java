package com.infoshareacademy.niewiem.tables.servlets;

import com.infoshareacademy.niewiem.halls.dto.HallDTO;
import com.infoshareacademy.niewiem.halls.services.ActiveHallService;
import com.infoshareacademy.niewiem.reservations.services.ReservationUpdateService;
import com.infoshareacademy.niewiem.services.ServletService;
import com.infoshareacademy.niewiem.tables.publishers.TablePublisher;
import com.infoshareacademy.niewiem.tables.services.TableDeleteService;
import com.infoshareacademy.niewiem.tables.services.TableSaveService;
import com.infoshareacademy.niewiem.tables.services.TableUpdateService;
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

@WebServlet("/table")
public class TableServlet extends HttpServlet {
    private static final String VIEW_NAME = "/table";
    private static final Logger LOG = LoggerFactory.getLogger(TableServlet.class);

    @Inject
    private ServletService servletService;

    @Inject
    private ActiveHallService activeHallService;

    @Inject
    private TablePublisher tablePublisher;

    @Inject
    private TableSaveService tableSaveService;

    @Inject
    private TableUpdateService tableUpdateService;

    @Inject
    private TableDeleteService tableDeleteService;

    @Inject
    private ReservationUpdateService reservationUpdateService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, Object> model = new HashMap<>();

        List<String> errors = new ArrayList<>();
        model.put("errors", errors);

        HallDTO hallDTO = activeHallService.getActiveHall(req.getSession());

        String tidParam = req.getParameter("tid");
        if (tablePublisher.publishRequestedTable(model, errors, tidParam, hallDTO)) {
            int tid = Integer.parseInt(tidParam);
            tablePublisher.publishIsActive(tid, model);
        } else {
            tablePublisher.publishTableTypes(model);
        }

        LOG.info("Servlet had: {} errors.", errors.size());
        servletService.sendModelToTemplate(req, resp, getServletContext(), model, VIEW_NAME);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        List<String> errors = new ArrayList<>();
        HallDTO activeHall = activeHallService.getActiveHall(req.getSession());
        String tidParam = req.getParameter("tid");

        if ("new".equals(action)) {
            LOG.info("Saving new table.");
            tableSaveService.createNewTable(req, errors, activeHall);
        } else if ("update-name".equals(action)) {
            LOG.info("Updating table name.");
            String name = req.getParameter("name");
            tableUpdateService.updateName(tidParam, name, errors, activeHall);
        } else if ("delete".equals(action)) {
            LOG.info("Deleting table.");
            tableDeleteService.delete(tidParam, errors, activeHall);
        } else if ("startGame".equals(action)) {
            // todo: do me!!
            LOG.info("Requested starting new game on table with id: {}.", tidParam);
//            tableDeleteService.;
        } else if ("stopGame".equals(action)) {
            LOG.info("Requesting stopping of current game on table with id: {}", tidParam);
            reservationUpdateService.stopGame(tidParam, errors, activeHall);
        }
        resp.sendRedirect("/tables-view");
    }
}
