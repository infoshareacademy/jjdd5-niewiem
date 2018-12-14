package com.infoshareacademy.niewiem.web;

import com.infoshareacademy.niewiem.dao.TableDao;
import com.infoshareacademy.niewiem.freemarker.TemplateProvider;
import com.infoshareacademy.niewiem.pojo.Table;
import com.infoshareacademy.niewiem.services.TableQueryService;
import com.infoshareacademy.niewiem.services.TableSaveService;
import com.infoshareacademy.niewiem.services.TableUpdateService;
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
import java.util.Map;

@WebServlet("/table-menu")
public class TableMenuServlet extends HttpServlet {
    private static final String TEMPLATE_NAME = "table-menu";

    private static final Logger LOG = LoggerFactory.getLogger(CreateHallServlet.class);

    @Inject
    private TemplateProvider templateProvider;
    @Inject
    private TableSaveService tableSaveService;
    @Inject
    private TableQueryService tableQueryService;
    @Inject
    private TableUpdateService tableUpdateService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.addHeader("Content-Type", "text/html; charset=utf-8");
        String hid = req.getParameter("hid");

        Table tableById = tableQueryService.findById(Integer.parseInt(hid));
        Map<String, Object> model = new HashMap<>();
        model.put("hid", hid);
        sendModelToTemplate(resp, model);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer hid = Integer.parseInt(req.getParameter("hid"));
        String name = req.getParameter("name");

        Table table = tableQueryService.findById(hid);

        if (table != null) {
            table.setName(name);
            tableUpdateService.update(table);
        }
    }

    private void sendModelToTemplate(HttpServletResponse resp, Map<String, Object> model) throws IOException {
        Template template = templateProvider.getTemplate(getServletContext(), TEMPLATE_NAME);

        try {
            template.process(model, resp.getWriter());
        } catch (TemplateException e) {
            LOG.error("Error while processing template: " + e);
        }
    }

    private void addNTablesToHall(Integer hallId, Integer numberOfTables) {
        for (int i = 1; i <= numberOfTables; i++) {
            tableSaveService.addTableToHall(hallId, i);
        }
    }
}