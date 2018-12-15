package com.infoshareacademy.niewiem.web;

import com.infoshareacademy.niewiem.pojo.Hall;
import com.infoshareacademy.niewiem.pojo.Table;
import com.infoshareacademy.niewiem.services.ActiveHallService;
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

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Hall hall = activeHallService.getActiveHallOrRedirect(req.getSession(), resp);

        resp.addHeader("Content-Type", "text/html; charset=utf-8");
        ServletContext context = getServletContext();
        Map<String, Object> model = new HashMap<>();

        model.put("bodyTemplate", VIEW_NAME + ".ftlh");

        List<Table> tables = tableQueryService.findAllInHall(hall);
        model.put("tables", tables);

        servletService.sendModelToTemplate(resp, context, model);
    }


}
