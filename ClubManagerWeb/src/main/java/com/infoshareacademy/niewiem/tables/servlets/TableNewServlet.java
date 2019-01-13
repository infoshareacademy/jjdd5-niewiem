package com.infoshareacademy.niewiem.tables.servlets;

import com.infoshareacademy.niewiem.halls.dto.HallDTO;
import com.infoshareacademy.niewiem.halls.services.ActiveHallService;
import com.infoshareacademy.niewiem.services.ServletService;
import com.infoshareacademy.niewiem.tables.publishers.TablePublisher;
import com.infoshareacademy.niewiem.tables.services.TableSaveService;
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

@WebServlet("/table-new")
public class TableNewServlet extends HttpServlet {
    private static final String VIEW_NAME = "/table-new";
    private static final Logger LOG = LoggerFactory.getLogger(TableNewServlet.class);

    @Inject
    private ServletService servletService;

    @Inject
    private TableSaveService tableSaveService;

    @Inject
    private TablePublisher tablePublisher;

    @Inject
    private ActiveHallService ahService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Map<String, Object> model = new HashMap<>();

        tablePublisher.publishTableTypes(model);

        servletService.sendModelToTemplate(req, resp, getServletContext(), model, VIEW_NAME);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        HallDTO hallDTO = ahService.getActiveHall(req.getSession());
        String name = req.getParameter("name");
        String type = req.getParameter("type");

        tableSaveService.addTableToHall(hallDTO, name, type);

        resp.sendRedirect("/tables-view");
    }
}