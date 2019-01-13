package com.infoshareacademy.niewiem.devpanel;

import com.infoshareacademy.niewiem.halls.services.HallSaveService;
import com.infoshareacademy.niewiem.domain.Hall;
import com.infoshareacademy.niewiem.reservations.services.ReservationSaveService;
import com.infoshareacademy.niewiem.services.ServletService;
import com.infoshareacademy.niewiem.tables.services.TableSaveService;
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
        Map<String, Object> model = new HashMap<>();

        addThreeNewClubs();
        addTablesToClubs();
        addActiveReservations();

        servletService.sendModelToTemplate(req, resp, getServletContext(), model, VIEW_NAME);
    }

    private void addThreeNewClubs() {
        hallSaveService.saveWithoutValidation(new Hall("DEMO 1"));
        hallSaveService.saveWithoutValidation(new Hall("DEMO 2"));
        hallSaveService.saveWithoutValidation(new Hall("DEMO 3"));
    }

    private void addTablesToClubs() {
        addNTablesToHall(1, 4);
        addNTablesToHall(2, 8);
        addNTablesToHall(3, 12);
    }

    private void addActiveReservations() {
//      Tables in Hall 1 -----------------------------------------------------------------------------------------------
        reservationSaveService.createReservationWithoutValidation(1, LocalDateTime.now().minusMinutes(20), 60, DEV_PANEL);
        reservationSaveService.createReservationWithoutValidation(2, LocalDateTime.now().minusMinutes(10), 60, DEV_PANEL);
        reservationSaveService.createReservationWithoutValidation(3, LocalDateTime.now().minusMinutes(0), 60, DEV_PANEL);
//      Tables in Hall 2 -----------------------------------------------------------------------------------------------
        reservationSaveService.createReservationWithoutValidation(5, LocalDateTime.now().minusMinutes(25), 60, DEV_PANEL);
        reservationSaveService.createReservationWithoutValidation(6, LocalDateTime.now().minusMinutes(20), 60, DEV_PANEL);
        reservationSaveService.createReservationWithoutValidation(7, LocalDateTime.now().minusMinutes(15), 60, DEV_PANEL);
        reservationSaveService.createReservationWithoutValidation(8, LocalDateTime.now().minusMinutes(10), 60, DEV_PANEL);
        reservationSaveService.createReservationWithoutValidation(9, LocalDateTime.now().minusMinutes(5), 60, DEV_PANEL);
        reservationSaveService.createReservationWithoutValidation(10, LocalDateTime.now().minusMinutes(0), 60, DEV_PANEL);
//      Tables in Hall 3 -----------------------------------------------------------------------------------------------
        reservationSaveService.createReservationWithoutValidation(13, LocalDateTime.now().minusMinutes(40), 60, DEV_PANEL);
        reservationSaveService.createReservationWithoutValidation(14, LocalDateTime.now().minusMinutes(35), 60, DEV_PANEL);
        reservationSaveService.createReservationWithoutValidation(15, LocalDateTime.now().minusMinutes(30), 60, DEV_PANEL);
        reservationSaveService.createReservationWithoutValidation(16, LocalDateTime.now().minusMinutes(25), 60, DEV_PANEL);
        reservationSaveService.createReservationWithoutValidation(17, LocalDateTime.now().minusMinutes(20), 60, DEV_PANEL);
        reservationSaveService.createReservationWithoutValidation(18, LocalDateTime.now().minusMinutes(15), 60, DEV_PANEL);
        reservationSaveService.createReservationWithoutValidation(19, LocalDateTime.now().minusMinutes(10), 60, DEV_PANEL);
        reservationSaveService.createReservationWithoutValidation(20, LocalDateTime.now().minusMinutes(5), 60, DEV_PANEL);
    }

    private void addNTablesToHall(Integer hallId, Integer numberOfTables) {
        for (int i = 1; i <= numberOfTables; i++) {
            tableSaveService.addTablePoolToHallAutoNameNoValidation(hallId, i);
        }
    }
}
