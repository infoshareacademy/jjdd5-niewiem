package com.infoshareacademy.niewiem.halls.filters;

import com.infoshareacademy.niewiem.halls.dto.HallDTO;
import com.infoshareacademy.niewiem.halls.validators.HallValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(
        filterName = "ActiveHallFilter",
        urlPatterns = {"/*"})
public class ActiveHallFilter implements Filter {
    private static final Logger LOG = LoggerFactory.getLogger(ActiveHallFilter.class);

    public static final String WELCOME_PAGE_TO_REDIRECT = "/choose-hall";
    public static final String ACTIVE_HALL_PARAM = "activeHall";

    private static final String EXCLUDED_ROOT_PATH = "/";
    private static final String[] EXCLUDED_PATH_BEGINNINGS = new String[]{
            WELCOME_PAGE_TO_REDIRECT,
            "/id-token",
            "/logout",
            "/login",
            "/dev-panel",
            "/images",
            "/create-hall",
            "/error-handler"
    };
    private static final String[] EXCLUDED_PATH_ENDINGS = new String[]{
            ".css",
            ".js",
            ".ico",
            ".jpeg",
            ".jpg",
            ".png"
    };

    @Inject
    private HallValidator hallValidator;

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

        if (isURIExcludedFromFilter(reqUri)) {
            LOG.debug("Requested URI is excluded from active hall filter. ({})", reqUri);
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        Object hallObj = req.getSession().getAttribute(ACTIVE_HALL_PARAM);
        if (hallObj == null) {
            LOG.warn("There was no active hall in session. Redirected to hall chooser.");
            resp.sendRedirect(WELCOME_PAGE_TO_REDIRECT);
            return;
        }

        HallDTO hallDTO = (HallDTO) hallObj;

        if (hallValidator.validateHallIdDoesNotExists(hallDTO.getId())) {
            LOG.info("Active hall does not exist in database. Redirected to hall chooser.");
            resp.sendRedirect(WELCOME_PAGE_TO_REDIRECT);
            return;
        }

        LOG.debug("Active hall is in session. Proceeding to chosen URI. ({})", reqUri);
        filterChain.doFilter(servletRequest, servletResponse);
    }

    private boolean isURIExcludedFromFilter(String reqUri) {
        LOG.debug("Requested URI: {}", reqUri);

        if (reqUri.equals(EXCLUDED_ROOT_PATH)) {
            return true;
        }

        for (String s : EXCLUDED_PATH_BEGINNINGS) {
            if (reqUri.startsWith(s)) {
                return true;
            }
        }

        for (String s : EXCLUDED_PATH_ENDINGS) {
            if (reqUri.endsWith(s)) {
                return true;
            }
        }

        return false;
    }
}