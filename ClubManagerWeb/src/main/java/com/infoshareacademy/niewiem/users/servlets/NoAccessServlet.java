package com.infoshareacademy.niewiem.users.servlets;

import com.infoshareacademy.niewiem.services.ServletService;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/no-access")
public class NoAccessServlet extends HttpServlet {

    private static final String VIEW_NAME = "/no-access";

    @Inject
    private ServletService servletService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, Object> model = new HashMap<>();

        servletService.sendModelToTemplate(req, resp, getServletContext(), model, VIEW_NAME);
    }
}
