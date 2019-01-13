package com.infoshareacademy.niewiem.tables.servlets;

import com.infoshareacademy.niewiem.halls.dto.HallDTO;
import com.infoshareacademy.niewiem.halls.services.ActiveHallService;
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

@WebServlet("/tables-view")
public class TablesViewServlet extends HttpServlet {
    private static final String VIEW_NAME = "/tables-view";
    private static final Logger LOG = LoggerFactory.getLogger(TablesViewServlet.class);

    @Inject
    private ActiveHallService activeHallService;

    @Inject
    private ServletService servletService;

    @Inject
    private TablesListPublisher tablesListPublisher;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, Object> model = new HashMap<>();
        HallDTO hallDTO = activeHallService.getActive(req.getSession());

        tablesListPublisher.publishForAllTablesInHallActiveReservationOrTempZeroEndReservation(model, hallDTO);

        servletService.sendModelToTemplate(req, resp, getServletContext(), model, VIEW_NAME);
    }
}