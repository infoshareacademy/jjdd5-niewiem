package com.infoshareacademy.niewiem.services;

import com.infoshareacademy.niewiem.shared.freemarker.TemplateProvider;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

import static com.infoshareacademy.niewiem.shared.freemarker.TemplateProvider.LAYOUT_NAME;

@Stateless
public class ServletService {
    private static final Logger LOG = LoggerFactory.getLogger(ServletService.class);

    @Inject
    private TemplateProvider templateProvider;

    public void sendModelToTemplate(HttpSession session, HttpServletResponse resp, ServletContext context, Map<String, Object> model, String VIEW_NAME) throws IOException {
        resp.addHeader("Content-Type", "text/html; charset=utf-8");

        model.put("bodyTemplate", VIEW_NAME + ".ftlh");

        if (session.getAttribute("userName") != null) {
            model.put("userAuthenticated", true);
        }
        if(session.getAttribute("activeHall") != null){
            model.put("activeHall", session.getAttribute("activeHall"));
        }
        if(session.getAttribute("imageUrl") != null){
            model.put("imageUrl", session.getAttribute("imageUrl"));
        }
        if(session.getAttribute("userName") != null){
            model.put("name", session.getAttribute("userName"));
        }


        Template template = templateProvider.getTemplate(context, LAYOUT_NAME);

        try {
            template.process(model, resp.getWriter());
        } catch (TemplateException e) {
            LOG.error("Error while processing template: " + e);
        }
    }
}
