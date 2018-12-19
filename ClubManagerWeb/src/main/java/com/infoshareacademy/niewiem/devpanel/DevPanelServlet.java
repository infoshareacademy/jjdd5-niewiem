package com.infoshareacademy.niewiem.devpanel;

import com.infoshareacademy.niewiem.halls.services.HallSaveService;
import com.infoshareacademy.niewiem.pojo.Hall;
import com.infoshareacademy.niewiem.reservations.services.ReservationSaveService;
import com.infoshareacademy.niewiem.services.*;
import com.infoshareacademy.niewiem.tables.TableSaveService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.servlet.ServletContext;
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
    private static final String VIEW_NAME = "/dev-panel";
    public static final String DEV_PANEL = "added automagically";
    private static final Logger LOG = LoggerFactory.getLogger(DevPanelServlet.class);

    @Inject
    private ServletService servletService;

    @Inject
    private HallSaveService hallSaveService;

    @Inject
    private TableSaveService tableSaveService;

    @Inject
    ReservationSaveService reservationSaveService;


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.addHeader("Content-Type", "text/html; charset=utf-8");
        ServletContext context = getServletContext();
        Map<String, Object> model = new HashMap<>();

        model.put("bodyTemplate", VIEW_NAME + ".ftlh");

        addThreeNewClubs();
        addTablesToClubs();
        addActiveReservations();

        servletService.sendModelToTemplate(resp, context, model);
    }

    private void addThreeNewClubs() {
        hallSaveService.save(new Hall("DEMO 1"));
        hallSaveService.save(new Hall("DEMO 2"));
        hallSaveService.save(new Hall("DEMO 3"));
    }

    private void addTablesToClubs() {
        addNTablesToHall(1, 4);
        addNTablesToHall(2, 8);
        addNTablesToHall(3, 12);
    }

    private void addActiveReservations() {
//      Tables in Hall 1 -----------------------------------------------------------------------------------------------
        reservationSaveService.addReservation(1, LocalDateTime.now().minusMinutes(20), 60, DEV_PANEL);
        reservationSaveService.addReservation(2, LocalDateTime.now().minusMinutes(10), 60, DEV_PANEL);
        reservationSaveService.addReservation(3, LocalDateTime.now().minusMinutes(0), 60, DEV_PANEL);
//      Tables in Hall 2 -----------------------------------------------------------------------------------------------
        reservationSaveService.addReservation(5, LocalDateTime.now().minusMinutes(25), 60, DEV_PANEL);
        reservationSaveService.addReservation(6, LocalDateTime.now().minusMinutes(20), 60, DEV_PANEL);
        reservationSaveService.addReservation(7, LocalDateTime.now().minusMinutes(15), 60, DEV_PANEL);
        reservationSaveService.addReservation(8, LocalDateTime.now().minusMinutes(10), 60, DEV_PANEL);
        reservationSaveService.addReservation(9, LocalDateTime.now().minusMinutes(5), 60, DEV_PANEL);
        reservationSaveService.addReservation(10, LocalDateTime.now().minusMinutes(0), 60, DEV_PANEL);
//      Tables in Hall 3 -----------------------------------------------------------------------------------------------
        reservationSaveService.addReservation(13, LocalDateTime.now().minusMinutes(40), 60, DEV_PANEL);
        reservationSaveService.addReservation(14, LocalDateTime.now().minusMinutes(35), 60, DEV_PANEL);
        reservationSaveService.addReservation(15, LocalDateTime.now().minusMinutes(30), 60, DEV_PANEL);
        reservationSaveService.addReservation(16, LocalDateTime.now().minusMinutes(25), 60, DEV_PANEL);
        reservationSaveService.addReservation(17, LocalDateTime.now().minusMinutes(20), 60, DEV_PANEL);
        reservationSaveService.addReservation(18, LocalDateTime.now().minusMinutes(15), 60, DEV_PANEL);
        reservationSaveService.addReservation(19, LocalDateTime.now().minusMinutes(10), 60, DEV_PANEL);
        reservationSaveService.addReservation(20, LocalDateTime.now().minusMinutes(5), 60, DEV_PANEL);
    }

    private void addNTablesToHall(Integer hallId, Integer numberOfTables) {
        for (int i = 1; i <= numberOfTables; i++) {
            tableSaveService.addTablePoolToHallAutoName(hallId, i);
        }
    }
}
