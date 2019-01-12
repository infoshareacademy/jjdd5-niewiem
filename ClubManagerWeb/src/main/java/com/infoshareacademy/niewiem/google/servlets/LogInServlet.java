package com.infoshareacademy.niewiem.google.servlets;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.infoshareacademy.niewiem.google.IdTokenVerifierAndParser;
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
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@WebServlet("/login")
public class LogInServlet extends HttpServlet {

    private static final Logger LOG = LoggerFactory.getLogger(LogInServlet.class);
    private static final String VIEW_NAME = "/login-page";
    private static final String SESSION_NAME = "userName";
    private static final String SESSION_EMAIL = "userEmail";
    private static final String SESSION_IMAGE_URL = "imageUrl";
    private static final Integer ADMIN = 1;
    private static final Integer USER = 2;

    @Inject
    private ServletService servletService;

    @Inject
    private UserDao userDao;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, Object> model = new HashMap<>();

        servletService.sendModelToTemplate(req, resp, getServletContext(), model, VIEW_NAME);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("text/html");
        HttpSession session = req.getSession(true);
        User user = new User();

        try {
            String idToken = req.getParameter("id_token");
            GoogleIdToken.Payload payLoad = IdTokenVerifierAndParser.getPayload(idToken);
            String googleName = (String) payLoad.get("name");
            String googleEmail = (String) payLoad.get("email");
            String imageUrl = (String) payLoad.get("picture");

            LOG.info("User logged in");

            session.setAttribute(SESSION_NAME, googleName);
            session.setAttribute(SESSION_EMAIL, googleEmail);
            session.setAttribute(SESSION_IMAGE_URL, imageUrl);

            String email = (String) session.getAttribute(SESSION_EMAIL);

            List<User> listUsers = userDao.findAll();

            List<User> emailList = listUsers.stream()
                    .filter(e -> e.getUserEmail().equals(email))
                    .collect(Collectors.toList());

            if (emailList.isEmpty()) {
                user.setUserName(googleName);
                user.setUserEmail(email);

                if (isAdmin(email)) {
                    user.setRole(ADMIN);
                }
                user.setRole(USER);
                userDao.save(user);
            }

            if (user != null && user.getRole() == ADMIN) {
                req.getServletContext().getRequestDispatcher("/admin-panel").forward(req, resp);
            } else {
                req.getServletContext().getRequestDispatcher("/choose-hall").forward(req, resp);
            }

        } catch (Exception e) {
            LOG.error("User can not be logged in");
            throw new RuntimeException(e);
        }
    }

    private boolean isAdmin(String email) {
        return email.equals("clubmanager.niewiem@gmail.com");
    }
}

