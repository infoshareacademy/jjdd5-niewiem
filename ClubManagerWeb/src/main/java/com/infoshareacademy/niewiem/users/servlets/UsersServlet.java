package com.infoshareacademy.niewiem.users.servlets;

import com.infoshareacademy.niewiem.services.ServletService;
import com.infoshareacademy.niewiem.users.dao.UserDao;
import com.infoshareacademy.niewiem.users.domain.User;
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

@WebServlet("/users")
public class UsersServlet extends HttpServlet {

    private static final Logger LOG = LoggerFactory.getLogger(UsersServlet.class);

    private static final String VIEW_NAME = "/users";

    @Inject
    private UserDao userDao;

    @Inject
    private ServletService servletService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, Object> model = new HashMap<>();
        List<User> users = userDao.findAll();
        LOG.info("Found {} users", users.size());

        model.put("users", users);

        servletService.sendModelToTemplate(req, resp, getServletContext(), model, VIEW_NAME);
    }
}
