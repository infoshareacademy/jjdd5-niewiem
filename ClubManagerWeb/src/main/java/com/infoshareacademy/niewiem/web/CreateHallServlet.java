package com.infoshareacademy.niewiem.web;

import com.infoshareacademy.niewiem.freemarker.TemplateProvider;
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
import static com.infoshareacademy.niewiem.freemarker.TemplateProvider.LAYOUT_NAME;

@WebServlet("/create-hall")
public class CreateHallServlet extends HttpServlet {
    private static final String VIEW_NAME = "/create-hall";
    private static final Logger LOG = LoggerFactory.getLogger(CreateHallServlet.class);


    @Inject
    private TemplateProvider templateProvider;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.addHeader("Content-Type", "text/html; charset=utf-8");
        Map<String, Object> model = new HashMap<>();
        model.put("bodyTemplate", VIEW_NAME + ".ftlh");
        sendModelToTemplate(resp, model);
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
