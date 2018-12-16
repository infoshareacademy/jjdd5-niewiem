package com.infoshareacademy.niewiem.web;

import com.infoshareacademy.niewiem.enums.TableType;
import com.infoshareacademy.niewiem.services.ServletService;
import com.infoshareacademy.niewiem.services.TableSaveService;
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
import java.util.EnumSet;
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

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.addHeader("Content-Type", "text/html; charset=utf-8");
        ServletContext context = getServletContext();
        Map<String, Object> model = new HashMap<>();

        model.put("bodyTemplate", VIEW_NAME + ".ftlh");

        EnumSet<TableType> types = EnumSet.allOf(TableType.class);
        model.put("types", types);

        servletService.sendModelToTemplate(resp, context, model);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String hid = req.getParameter("hid");
        String name = req.getParameter("name");
        String type = req.getParameter("type");
        tableSaveService.addTableToHall(hid, name, type);

        resp.sendRedirect("/tables-view?hallId="+hid);
    }
}