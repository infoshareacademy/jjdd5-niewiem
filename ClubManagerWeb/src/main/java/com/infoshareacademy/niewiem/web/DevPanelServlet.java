package com.infoshareacademy.niewiem.web;

import com.infoshareacademy.niewiem.dao.HallDao;
import com.infoshareacademy.niewiem.dao.TableDao;
import com.infoshareacademy.niewiem.enums.TableType;
import com.infoshareacademy.niewiem.freemarker.TemplateProvider;
import com.infoshareacademy.niewiem.pojo.Hall;
import com.infoshareacademy.niewiem.pojo.Table;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("dev-panel")
public class DevPanelServlet extends HttpServlet {
    private static final String VIEW_NAME = "/dev-panel";
    private static final String LAYOUT_NAME = "layouts/app";
    private static final Logger LOG = LoggerFactory.getLogger(DevPanelServlet.class);

    @Inject
    private TemplateProvider templateProvider;

    @Inject
    private HallDao hallDao;

    @Inject
    private TableDao tableDao;


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.addHeader("Content-Type", "text/html; charset=utf-8");
        Map<String, Object> model = new HashMap<>();

        addThreeNewClubs();
        addTablesToClubs();
        model.put("bodyTemplate", VIEW_NAME + ".ftlh");
        sendModelToTemplate(resp, model);
    }

    private void addThreeNewClubs() {
        hallDao.save(new Hall("DEMO 1"));
        hallDao.save(new Hall("DEMO 2"));
        hallDao.save(new Hall("DEMO 3"));
    }

    private void addTablesToClubs() {
        addNTablesToHall(1, 4);
        addNTablesToHall(2, 8);
        addNTablesToHall(3, 12);
    }

    private void addNTablesToHall(Integer hallId, Integer numberOfTables) {
        for (int i = 1; i <= numberOfTables; i++) {
            tableDao.save(new Table(hallDao.findById(hallId), TableType.POOL, "P" + String.format("%02d", i)));
        }
    }

    private void sendModelToTemplate(HttpServletResponse resp, Map<String, Object> model) throws IOException {
        Template template = templateProvider.getTemplate(getServletContext(), LAYOUT_NAME);

        try {
            template.process(model, resp.getWriter());
        } catch (TemplateException e) {
            LOG.error("Error while processing template: " + e);
        }
    }


}
