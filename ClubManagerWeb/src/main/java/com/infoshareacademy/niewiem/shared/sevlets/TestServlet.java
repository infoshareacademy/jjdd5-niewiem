package com.infoshareacademy.niewiem.shared.sevlets;

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
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/test")
public class TestServlet extends HttpServlet {
    private static final String VIEW_NAME = "/test";
    private static final Logger LOG = LoggerFactory.getLogger(TestServlet.class);

    @Inject
    private ServletService servletService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, Object> model = new HashMap<>();

        model.put("date", LocalDateTime.now());

        servletService.sendModelToTemplate(req, resp, getServletContext(), model, VIEW_NAME);
    }
}