package com.infoshareacademy.niewiem.web;

import com.infoshareacademy.niewiem.dao.HallDao;
import com.infoshareacademy.niewiem.freemarker.TemplateProvider;
import com.infoshareacademy.niewiem.pojo.Hall;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



@WebServlet("choose-hall")
public class ChooseHallServlet extends HttpServlet {
    private static final String TEMPLATE_NAME = "choose-hall";
    private static final String ACTION_SAVE_HALL = "save-hall";
    private static final Logger LOG = LoggerFactory.getLogger(ChooseHallServlet.class);

    @Inject
    private TemplateProvider templateProvider;

    @Inject
    private HallDao hallDao;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);


    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, Object> model = new HashMap<>();

        resp.addHeader("Content-Type", "text/html; charset=utf-8");
        addListOfHallsToModel(resp, model);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, String[]> params = req.getParameterMap();
        String action = params.get("action")[0];

        resp.addHeader("Content-Type", "text/html; charset=utf-8");
        Map<String, Object> model = new HashMap<>();

        if(action.equals(ACTION_SAVE_HALL)){
            String name = params.get("name")[0];
            hallDao.save(new Hall(name));
            model.put("savedSuccess", true);
            model.put("savedHall", name);
        }

        addListOfHallsToModel(resp, model);
    }

    private void addListOfHallsToModel(HttpServletResponse resp, Map<String, Object> model) throws IOException {
        List<Hall> halls = hallDao.findAll();
        LOG.info("Found {} halls in halls table", halls.size());

        model.put("halls", halls);

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
