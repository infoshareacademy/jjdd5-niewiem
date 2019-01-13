package com.infoshareacademy.niewiem.reservations.services;

import com.infoshareacademy.niewiem.domain.Reservation;
import com.infoshareacademy.niewiem.domain.Table;
import com.infoshareacademy.niewiem.halls.dto.HallDTO;
import com.infoshareacademy.niewiem.reservations.dao.ReservationDao;
import com.infoshareacademy.niewiem.reservations.mappers.ReservationRequestMapper;
import com.infoshareacademy.niewiem.tables.dao.TableDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class ReservationSaveService {
    private static final Logger LOG = LoggerFactory.getLogger(ReservationSaveService.class);

    @Inject
    private ReservationDao reservationDao;

    @Inject
    private TableDao tableDao;

    @Inject
    private ReservationRequestMapper reservationMapper;

    public Long save(Reservation reservation, List<String> erorrs) {
        if (reservationDao.isInConflict(reservation)) {
            LOG.info("Reservation was in conflict with one already in database. Did not save.");
            erorrs.add("Reservation was in conflict with one already in database. Did not save.");
            return -1L;
        }

        return reservationDao.save(reservation);
    }

    public void createNewReservation(HttpServletRequest req, List<String> errors, HallDTO hallDTO) {
        Reservation reservation = reservationMapper.getReqReservationWithoutId(req, errors, hallDTO);
        if(reservation == null){
            return;
        }
        save(reservation, errors);
    }

    public void startGame(String tidParam, String timeSpan, List<String> errors, HallDTO hallDTO) {
        Reservation reservation = reservationMapper.getStartReservation(tidParam, timeSpan, errors, hallDTO);
        if(reservation == null){
            return;
        }
        save(reservation, errors);
    }

    public void createReservationWithoutValidation(Integer tableId, LocalDateTime start, Integer timeSpanMinutes, String customer) {
        Reservation reservation = getReservationWithoutId(tableId, start, timeSpanMinutes, customer);
        save(reservation, new ArrayList<>());
    }

    private Reservation getReservationWithoutId(Integer tableId, LocalDateTime start, Integer timeSpanMinutes, String customer) {
        Reservation reservation = new Reservation();
        Table table = tableDao.findById(tableId);

        reservation.setTable(table);
        reservation.setStartTime(start);
        reservation.setEndTime(start.plusMinutes(timeSpanMinutes));
        reservation.setCustomer(customer);

        return reservation;
    }

}