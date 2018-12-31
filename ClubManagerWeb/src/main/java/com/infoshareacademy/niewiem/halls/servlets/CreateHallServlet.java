package com.infoshareacademy.niewiem.halls.servlets;

import com.infoshareacademy.niewiem.halls.services.HallSaveService;
import com.infoshareacademy.niewiem.services.ServletService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
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
        Map<String, Object> model = new HashMap<>();

        servletService.sendModelToTemplate(resp, getServletContext(), model, VIEW_NAME);
        }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        hallSaveService.addNewHall(req);

        resp.sendRedirect("/choose-hall");
    }
}