package com.infoshareacademy.niewiem.shared.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter(
        filterName = "ActiveHallFilter",
        urlPatterns = {"/*"})
public class ActiveHallFilter implements Filter {
    private static final Logger LOG = LoggerFactory.getLogger(ActiveHallFilter.class);


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        checkIfOnWelcomePage(servletRequest);

        filterChain.doFilter(servletRequest, servletResponse);
    }

    private void checkIfOnWelcomePage(ServletRequest servletRequest) {
        HttpServletRequest req = (HttpServletRequest) servletRequest;

        LOG.info("========================");
        String url = req.getRequestURL().toString();
        LOG.info("Requested URL: {}", url);
        LOG.info("-----------------------");
        String sub1 = req.getRequestURL().substring(1);
        LOG.info("Requested sub URL: {}", sub1);
        LOG.info("-----------------------");
        String contextPath = req.getContextPath();
        LOG.info("Requested context path: {}", contextPath);
        LOG.info("-----------------------");
        String pathInfo = req.getPathInfo();
        LOG.info("Requested path info: {}", pathInfo);
        LOG.info("-----------------------");
        String uri = req.getRequestURI();
        LOG.info("Requested URI: {}", uri);
        LOG.info("========================");
    }

    private boolean isURIExcludedFromFilter(){

    }
}