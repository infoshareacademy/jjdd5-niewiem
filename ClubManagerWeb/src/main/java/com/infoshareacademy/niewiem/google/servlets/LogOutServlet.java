package com.infoshareacademy.niewiem.google.servlets;

import com.infoshareacademy.niewiem.services.ServletService;
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

@WebServlet("/logout")
public class LogOutServlet extends HttpServlet {

    private static final Logger LOG = LoggerFactory.getLogger(LogOutServlet.class);
    private static final String VIEW_NAME = "/logout-page";

    @Inject
    private ServletService servletService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, Object> model = new HashMap<>();

        req.getSession().removeAttribute("userName");
        req.getSession().removeAttribute("imageUrl");
        LOG.info("User logged out");

        servletService.sendModelToTemplate(req.getSession(), resp, getServletContext(), model, VIEW_NAME);
    }
}
