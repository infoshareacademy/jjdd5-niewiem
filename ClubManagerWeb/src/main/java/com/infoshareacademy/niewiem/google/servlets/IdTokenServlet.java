package com.infoshareacademy.niewiem.google.servlets;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.infoshareacademy.niewiem.google.IdTokenVerifierAndParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/id-token")
public class IdTokenServlet extends HttpServlet {

    private static final Logger LOG = LoggerFactory.getLogger(IdTokenServlet.class);

    @Override
    protected void doPost (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("text/html");

        try {
            String idToken = req.getParameter("id_token");
            GoogleIdToken.Payload payLoad = IdTokenVerifierAndParser.getPayload(idToken);
            String name = (String) payLoad.get("name");
            String email = (String) payLoad.get("email");
            String imageUrl = (String) payLoad.get("picture");


            LOG.info("User logged in");

            HttpSession session = req.getSession(true);
            session.setAttribute("userName", name);
            session.setAttribute("imageUrl", imageUrl);
            req.getServletContext()
                    .getRequestDispatcher("/choose-hall").forward(req, resp);


            if (isAdmin(name)) {
                req.getServletContext()
                        .getRequestDispatcher("/admin-panel").forward(req, resp);
            } else {
                req.getServletContext()
                        .getRequestDispatcher("/choose-hall").forward(req, resp);
            }
        } catch (Exception e) {
            LOG.error("User can not be logged in");
            throw new RuntimeException(e);

        }
    }

    private boolean isAdmin(String email) {
        if (email.equals("clubmanager.niewiem@gmail.com")) {
            return true;
        } else {
            return false;

        }
    }
}
