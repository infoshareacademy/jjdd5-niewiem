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

@WebFilter(filterName = "AdminFilter",
        urlPatterns = {"/admin-panel"})

public class AdminFilter implements Filter {

    private static final Logger LOG = LoggerFactory.getLogger(AdminFilter.class);

    private static final String LOGIN_PAGE = "/login";
    private static final String NO_ACCESS_PAGE = "/no-access";
    private static final Integer ADMIN = 1;

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

        HttpSession session = req.getSession(true);
        String sessionEmail = (String) session.getAttribute("userEmail");

        if (sessionEmail != null) {
            if(userDao.findByEmail(sessionEmail).getRole() == ADMIN) {
                filterChain.doFilter(servletRequest, servletResponse);
                LOG.info("User {} got access to admin-panel", sessionEmail);
            }else {
                resp.sendRedirect(NO_ACCESS_PAGE);
                LOG.warn("User {} tried to get access to admin panel", sessionEmail);
            }
        } else {
            resp.sendRedirect(LOGIN_PAGE);
            LOG.warn("Unlogged user tried to get access to admin panel");
        }
    }
}
