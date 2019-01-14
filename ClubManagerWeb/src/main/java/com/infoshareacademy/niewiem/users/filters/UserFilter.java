package com.infoshareacademy.niewiem.users.filters;

import com.infoshareacademy.niewiem.users.dao.UserDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(filterName = "UserFilter",
        urlPatterns = {"/reservations",
        "/hall",
        "/table"})

public class UserFilter implements Filter {

    private static final Logger LOG = LoggerFactory.getLogger(UserFilter.class);

    private static final String LOGIN_PAGE = "/login";
    private static final String NO_ACCESS_PAGE = "/no-access";
    private static final Integer ADMIN = 1;
    private static final Integer USER = 2;

    @Inject
    private UserDao userDao;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        String reqUri = req.getRequestURI();

        HttpSession session = req.getSession(true);
        String sessionEmail = (String) session.getAttribute("userEmail");

        if (sessionEmail != null) {
            Integer role = userDao.findByEmail(sessionEmail).getRole();
            if (role == ADMIN || (role == USER && reqUri.contains("/reservations"))) {
                filterChain.doFilter(servletRequest, servletResponse);
                LOG.info("User {} entered {}", sessionEmail, reqUri);
            } else {
                resp.sendRedirect(NO_ACCESS_PAGE);
                LOG.warn("User {} tried to enter {}", sessionEmail, reqUri);
            }
        }
        resp.sendRedirect(LOGIN_PAGE);
        LOG.warn("Unlogged user tried to enter {}", reqUri);
    }
}
