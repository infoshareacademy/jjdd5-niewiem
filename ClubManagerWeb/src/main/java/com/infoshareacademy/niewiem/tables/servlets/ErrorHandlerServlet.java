package com.infoshareacademy.niewiem.tables.servlets;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "errorHandlerServlet",
        urlPatterns = {"/error-handler"},
        loadOnStartup = 1)
public class ErrorHandlerServlet extends HttpServlet {

    private static final Logger LOG = LoggerFactory.getLogger(ErrorHandlerServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        PrintWriter writer = resp.getWriter();

        writer.println(resp.getStatus() + " error has occured.");
        LOG.error("Error: " +  resp.getStatus() + " occured.");

        Throwable exception = (Throwable) req.getAttribute("javax.servlet.error.exception");
        if (exception != null) {
            exception = ExceptionUtils.getRootCause(exception);
            writer.println(exception.getMessage());
            LOG.error("Error caught", exception);
        } else {
            writer.println(" ");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}