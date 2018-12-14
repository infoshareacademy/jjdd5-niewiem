package com.infoshareacademy.niewiem.web;

import com.infoshareacademy.niewiem.dto.TableEndTimeInMillis;
import com.infoshareacademy.niewiem.pojo.Hall;
import com.infoshareacademy.niewiem.services.HallQueryService;
import com.infoshareacademy.niewiem.services.ServletService;
import com.infoshareacademy.niewiem.services.TableQueryService;
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

@WebServlet("tables-view")
public class TablesViewServlet extends HttpServlet {
    private static final String VIEW_NAME = "/tables-view";
    private static final Logger LOG = LoggerFactory.getLogger(TablesViewServlet.class);

    @Inject
    private TableQueryService tableQueryService;

    @Inject
    private HallQueryService hallQueryService;

    @Inject
    private ServletService servletService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.addHeader("Content-Type", "text/html; charset=utf-8");
        ServletContext context = getServletContext();
        Map<String, Object> model = new HashMap<>();

        model.put("bodyTemplate", VIEW_NAME + ".ftlh");

        // todo: this logic should be in service, maybe tables?
        Integer hallId = Integer.valueOf(req.getParameter("hallId"));
        Hall hall = hallQueryService.findById(hallId);
        model.put("hall", hall);
        List<TableEndTimeInMillis> tetim = tableQueryService.findAllTablesInHallWithEndTimeInMillis(hall);
        model.put("tablesEndTimeIneMillis", tetim);

        servletService.sendModelToTemplate(resp, context, model);
    }
}
