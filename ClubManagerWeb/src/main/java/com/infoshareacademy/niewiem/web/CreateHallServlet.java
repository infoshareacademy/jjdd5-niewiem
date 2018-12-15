package com.infoshareacademy.niewiem.web;

import com.infoshareacademy.niewiem.services.HallSaveService;
import com.infoshareacademy.niewiem.services.ServletService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@MultipartConfig
@WebServlet("/create-hall")
public class CreateHallServlet extends HttpServlet {
    private static final String VIEW_NAME = "/create-hall";
    private static final Logger LOG = LoggerFactory.getLogger(CreateHallServlet.class);

    @Inject
    private ServletService servletService;

    @Inject
    private HallSaveService hallSaveService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.addHeader("Content-Type", "text/html; charset=utf-8");
        ServletContext context = getServletContext();
        Map<String, Object> model = new HashMap<>();

        model.put("bodyTemplate", VIEW_NAME + ".ftlh");

        servletService.sendModelToTemplate(resp, context, model);
        }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        hallSaveService.addNewHall(req);

        resp.sendRedirect("/choose-hall");
    }
}
