package com.infoshareacademy.niewiem.web;

import com.infoshareacademy.niewiem.dao.HallDao;
import com.infoshareacademy.niewiem.dao.ReservationDao;
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
    private static final String TEMPLATE_NAME = "dev-panel";
    private static final Logger LOG = LoggerFactory.getLogger(ChooseHallServlet.class);

    @Inject
    private TemplateProvider templateProvider;

    @Inject
    private HallDao hallDao;
    @Inject
    private TableDao tableDao;
    @Inject
    private ReservationDao reservationDao;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Map<String, Object> model = new HashMap<>();
        resp.addHeader("Content-Type", "text/html; charset=utf-8");

        addThreeNewClubs();
        addTablesToClubs();

        sendModelToTemplate(resp, model);
    }

    private void addThreeNewClubs() {
        hallDao.save(new Hall("Green"));
        hallDao.save(new Hall("Ślunźkie Modżajto"));
        hallDao.save(new Hall("Timmy the Tim-Tim"));
    }

    private void addTablesToClubs(){
        tableDao.save(new Table(hallDao.findById(1), TableType.POOL, "P01"));
        tableDao.save(new Table(hallDao.findById(1), TableType.POOL, "P02"));
        tableDao.save(new Table(hallDao.findById(1), TableType.POOL, "P03"));
        tableDao.save(new Table(hallDao.findById(1), TableType.POOL, "P04"));

        tableDao.save(new Table(hallDao.findById(2), TableType.POOL, "P01"));
        tableDao.save(new Table(hallDao.findById(2), TableType.POOL, "P02"));
        tableDao.save(new Table(hallDao.findById(2), TableType.POOL, "P03"));
        tableDao.save(new Table(hallDao.findById(2), TableType.POOL, "P04"));
        tableDao.save(new Table(hallDao.findById(2), TableType.POOL, "P05"));
        tableDao.save(new Table(hallDao.findById(2), TableType.POOL, "P06"));
        tableDao.save(new Table(hallDao.findById(2), TableType.POOL, "P07"));
        tableDao.save(new Table(hallDao.findById(2), TableType.POOL, "P08"));

        tableDao.save(new Table(hallDao.findById(3), TableType.POOL, "P01"));
        tableDao.save(new Table(hallDao.findById(3), TableType.POOL, "P02"));
        tableDao.save(new Table(hallDao.findById(3), TableType.POOL, "P03"));
        tableDao.save(new Table(hallDao.findById(3), TableType.POOL, "P04"));
        tableDao.save(new Table(hallDao.findById(3), TableType.POOL, "P05"));
        tableDao.save(new Table(hallDao.findById(3), TableType.POOL, "P06"));
        tableDao.save(new Table(hallDao.findById(3), TableType.POOL, "P07"));
        tableDao.save(new Table(hallDao.findById(3), TableType.POOL, "P08"));
        tableDao.save(new Table(hallDao.findById(3), TableType.POOL, "P09"));
        tableDao.save(new Table(hallDao.findById(3), TableType.POOL, "P10"));
        tableDao.save(new Table(hallDao.findById(3), TableType.POOL, "P11"));
        tableDao.save(new Table(hallDao.findById(3), TableType.POOL, "P12"));
    }

    private void addOngoingGames(){

    }

    private void addHistory(){

    }

    private void addUpcomingReservations(){

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
