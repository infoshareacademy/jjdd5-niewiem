package com.infoshareacademy.niewiem.tables.servlets;

import com.infoshareacademy.niewiem.halls.dto.HallDTO;
import com.infoshareacademy.niewiem.halls.services.ActiveHallService;
import com.infoshareacademy.niewiem.services.ServletService;
import com.infoshareacademy.niewiem.tables.publishers.TablePublisher;
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
    private TablePublisher tablePublisher;

    @Inject
    private ServletService servletService;

    @Inject
    private ActiveHallService activeHallService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, Object> model = new HashMap<>();

        List<String> errors = new ArrayList<>();
        model.put("errors", errors);
        List<String> warnings = new ArrayList<>();
        model.put("warnings", warnings);

        HallDTO hallDTO = activeHallService.getActiveHall(req.getSession());

        tablePublisher.publishRequestedTable(model, errors, warnings, req.getParameter("tid"), hallDTO);

        LOG.info("Servlet had: {} errors.", errors.size());
        servletService.sendModelToTemplate(req, resp, getServletContext(), model, VIEW_NAME);
    }
}
