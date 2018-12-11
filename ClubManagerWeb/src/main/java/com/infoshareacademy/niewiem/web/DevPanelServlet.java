package com.infoshareacademy.niewiem.web;

import com.infoshareacademy.niewiem.dao.HallDao;
import com.infoshareacademy.niewiem.dao.ReservationDao;
import com.infoshareacademy.niewiem.dao.TableDao;
import com.infoshareacademy.niewiem.enums.TableType;
import com.infoshareacademy.niewiem.freemarker.TemplateProvider;
import com.infoshareacademy.niewiem.pojo.Hall;
import com.infoshareacademy.niewiem.pojo.Reservation;
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
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@WebServlet("dev-panel")
public class DevPanelServlet extends HttpServlet {
    private static final String TEMPLATE_NAME = "dev-panel";
    private static final Logger LOG = LoggerFactory.getLogger(DevPanelServlet.class);

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
        resp.addHeader("Content-Type", "text/html; charset=utf-8");
        Map<String, Object> model = new HashMap<>();

        addThreeNewClubs();
        addTablesToClubs();
        addActiveReservations();

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

    private void addActiveReservations(){
//      Tables in Hall 1 -----------------------------------------------------------------------------------------------
        addReservation(1, 20, 60);
        addReservation(2, 10, 60);
        addReservation(3, 0, 60);
//      Tables in Hall 2 -----------------------------------------------------------------------------------------------
        addReservation(5, 30, 60);
        addReservation(6, 25, 60);
        addReservation(7, 20, 60);
        addReservation(8, 15, 60);
        addReservation(9, 10, 60);
        addReservation(10, 5, 60);
//      Tables in Hall 3 -----------------------------------------------------------------------------------------------
        addReservation(13, 50, 60);
        addReservation(14, 45, 60);
        addReservation(15, 40, 60);
        addReservation(16, 30, 60);
        addReservation(17, 20, 60);
        addReservation(18, 10, 60);
        addReservation(19, 5, 60);
        addReservation(20, 0, 60);
    }

    private void addReservation(Integer tableId, Integer minutesBeforeNow, Integer duration){
        Table table = tableDao.findById(tableId);
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime start = now.minusMinutes(minutesBeforeNow);
        LocalDateTime end = start.plusMinutes(duration);
        Reservation reservation = new Reservation();

        reservation.setTable(table);
        reservation.setStartTime(start);
        reservation.setEndTime(end);

        reservationDao.save(reservation);
    }

    private void addNTablesToHall(Integer hallId, Integer numberOfTables) {
        for (int i = 1; i <= numberOfTables; i++) {
            tableDao.save(new Table(hallDao.findById(hallId), TableType.POOL, "P" + String.format("%02d", i)));
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


}
