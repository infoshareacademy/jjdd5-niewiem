package com.infoshareacademy.niewiem.shared.filters;

import com.infoshareacademy.niewiem.halls.services.HallQueryService;
import com.infoshareacademy.niewiem.domain.Hall;
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

    private static final String ROOT_PATH = "/";
    private static final String[] EXCLUDED_PATHS = new String[]{
            "/choose-hall",
            "/dev-panel",
            "/images",
    };
    private static final String[] EXCLUDED_EXTENSIONS = new String[]{
            ".css",
            ".js",
            ".ico",
            ".jpeg",
            ".jpg",
            ".png"
    };

    @Inject
    private HallQueryService hallQueryService;


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

        if(isURIExcludedFromFilter(reqUri)){
            LOG.info("Requested URI is excluded from active hall filter. ({})", reqUri);
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        Object hallObj = req.getSession().getAttribute("activeHall");

        if(hallObj == null){
            LOG.info("There was no active hall in session. Redirected to hall chooser.");
            resp.sendRedirect("/choose-hall");
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        // todo: probably should not store the whole entity, just the DTO...
        Hall hall = (Hall) hallObj;

        // todo: validate by hallValidator
        if(hallQueryService.doesNotExist(hall)){
            LOG.info("Active hall does not exist in database. Redirected to hall chooser.");
            resp.sendRedirect("/choose-hall");
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        LOG.debug("Active hall is in session. Proceeding to chosen URI. ({})", reqUri);
        filterChain.doFilter(servletRequest, servletResponse);
    }

    private boolean isURIExcludedFromFilter(String reqUri){

        LOG.info("Requested URI: {}", reqUri);

        if(reqUri.equals(ROOT_PATH)){
            return true;
        }

        for(String s : EXCLUDED_PATHS){
            if(reqUri.startsWith(s)){
                return true;
            }
        }

        for(String s : EXCLUDED_EXTENSIONS){
            if(reqUri.endsWith(s)){
                return true;
            }
        }

        return false;
    }
}