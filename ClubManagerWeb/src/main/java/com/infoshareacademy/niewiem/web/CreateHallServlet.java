package com.infoshareacademy.niewiem.web;

import com.infoshareacademy.niewiem.cdi.FileUploadProcessor;
import com.infoshareacademy.niewiem.dao.HallDao;
import com.infoshareacademy.niewiem.exceptions.HallImageNotFound;
import com.infoshareacademy.niewiem.freemarker.TemplateProvider;
import com.infoshareacademy.niewiem.pojo.Hall;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@MultipartConfig
@WebServlet("/create-hall")
public class CreateHallServlet extends HttpServlet {
    private static final String TEMPLATE_NAME = "create-hall";

    private static final Logger LOG = LoggerFactory.getLogger(CreateHallServlet.class);

    @Inject
    FileUploadProcessor fileUploadProcessor;

    @Inject
    private TemplateProvider templateProvider;

    @Inject
    private HallDao hallDao;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.addHeader("Content-Type", "text/html; charset=utf-8");
        Map<String, Object> model = new HashMap<>();

        sendModelToTemplate(resp, model);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

        Hall hall = new Hall();
        Part part = req.getPart("image");
        hall.setName(req.getParameter("name"));

        try {
            File image = fileUploadProcessor.uploadImageFile(part);
            String imageName = image.getName();
            hall.setImageURL("/images/" + imageName);
        } catch (HallImageNotFound userImageNotFound) {
            userImageNotFound.printStackTrace();
        }

        hallDao.save(hall);

        resp.sendRedirect("/choose-hall");
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
