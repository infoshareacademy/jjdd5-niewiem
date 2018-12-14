package com.infoshareacademy.niewiem.web;

import com.infoshareacademy.niewiem.enums.TableType;
import com.infoshareacademy.niewiem.pojo.Hall;
import com.infoshareacademy.niewiem.pojo.Reservation;
import com.infoshareacademy.niewiem.pojo.Table;
import com.infoshareacademy.niewiem.services.*;
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
    private static final Logger LOG = LoggerFactory.getLogger(DevPanelServlet.class);

    @Inject
    private ServletService servletService;

    @Inject
    private HallSaveService hallSaveService;

    @Inject
    private HallQueryService hallQueryService;

    @Inject
    private TableSaveService tableSaveService;

    @Inject
    private TableQueryService tableQueryService;

    @Inject ReservationSaveService reservationSaveService;


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

    private void addNTablesToHall(Integer hallId, Integer numberOfTables) {
        for (int i = 1; i <= numberOfTables; i++) {
            tableSaveService.addTableToHall(hallId, i);
        }
    }

    private void addReservation(Integer tableId, Integer minutesBeforeNow, Integer duration){
        Table table = tableQueryService.findById(tableId);
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime start = now.minusMinutes(minutesBeforeNow);
        LocalDateTime end = start.plusMinutes(duration);
        Reservation reservation = new Reservation();

        reservation.setTable(table);
        reservation.setStartTime(start);
        reservation.setEndTime(end);

        reservationSaveService.save(reservation);
    }
}
