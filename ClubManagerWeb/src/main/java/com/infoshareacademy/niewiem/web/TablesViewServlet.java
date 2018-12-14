package com.infoshareacademy.niewiem.web;

import com.infoshareacademy.niewiem.dao.HallDao;
import com.infoshareacademy.niewiem.dao.ReservationDao;
import com.infoshareacademy.niewiem.dao.TableDao;
import com.infoshareacademy.niewiem.dto.TableEndTimeInMillis;
import com.infoshareacademy.niewiem.freemarker.TemplateProvider;
import com.infoshareacademy.niewiem.pojo.Hall;
import freemarker.template.Template;
import freemarker.template.TemplateException;
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

@WebServlet("tables-view")
public class TablesViewServlet extends HttpServlet {
    private static final String TEMPLATE_NAME = "tables-view";
    private static final Logger LOG = LoggerFactory.getLogger(TablesViewServlet.class);

    @Inject
    private TemplateProvider templateProvider;

    @Inject
    private TableDao tableDao;

    @Inject
    private HallDao hallDao;

    @Inject
    private ReservationDao reservationDao;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.addHeader("Content-Type", "text/html; charset=utf-8");
        Map<String, Object> model = new HashMap<>();

        Integer hallId = Integer.valueOf(req.getParameter("hallId"));
        Hall hall = hallDao.findById(hallId);
        model.put("hall", hall);

        List<TableEndTimeInMillis> tetim = tableDao.findAllTablesInHallWithEndTimeInMillis(hall);
        model.put("tablesEndTimeIneMillis", tetim);

        sendModelToTemplate(resp, model);
    }

    private void sendModelToTemplate(HttpServletResponse resp, Map<String, Object> model) throws IOException {
        Template template = templateProvider.getTemplate(getServletContext(), TEMPLATE_NAME);

        try {
            template.process(model, resp.getWriter());
        } catch (TemplateException e) {
            LOG.error("Error while processing template: " + e);
        }
    }

}
