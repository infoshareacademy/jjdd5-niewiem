package com.infoshareacademy.niewiem.tables.servlets;

import com.infoshareacademy.niewiem.halls.dto.HallDTO;
import com.infoshareacademy.niewiem.halls.services.ActiveHallService;
import com.infoshareacademy.niewiem.services.ServletService;
import com.infoshareacademy.niewiem.tables.dto.TableEndTimeInMillis;
import com.infoshareacademy.niewiem.tables.services.TableQueryService;
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

@WebServlet("tables-view")
public class TablesViewServlet extends HttpServlet {
    private static final String VIEW_NAME = "/tables-view";
    private static final Logger LOG = LoggerFactory.getLogger(TablesViewServlet.class);

    @Inject
    private TableQueryService tableQueryService;

    @Inject
    private ActiveHallService activeHallService;

    @Inject
    private ServletService servletService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, Object> model = new HashMap<>();

        // todo: should use reservation view or DTO, not tetim
        HallDTO hallDTO = activeHallService.getActiveHall(req.getSession());
        List<TableEndTimeInMillis> tetim = tableQueryService.findAllTablesInHallWithEndTimeInMillis(hallDTO);
        model.put("tablesEndTimeIneMillis", tetim);

        servletService.sendModelToTemplate(resp, getServletContext(), model, VIEW_NAME);
    }
}
