package com.infoshareacademy.niewiem.reservations.services;

import com.infoshareacademy.niewiem.domain.Hall;
import com.infoshareacademy.niewiem.domain.Reservation;
import com.infoshareacademy.niewiem.domain.Table;
import com.infoshareacademy.niewiem.halls.dao.HallDao;
import com.infoshareacademy.niewiem.halls.dto.HallDTO;
import com.infoshareacademy.niewiem.reservations.dao.ReservationDao;
import com.infoshareacademy.niewiem.services.validators.InputValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class ReservationQueryService {
    private static final Logger LOG = LoggerFactory.getLogger(ReservationQueryService.class);

    @Inject
    private ReservationDao reservationDao;

    @Inject
    private HallDao hallDao;

    @Inject
    private InputValidator inputValidator;

    public Reservation findById(String idString){
        Long rid = inputValidator.reqLongValidator(idString);
        return findById(rid);
    }

    public Reservation findById(Long id) {
        return reservationDao.findById(id);
    }

    public List<Reservation> findAll() {
        return reservationDao.findAll();
    }

    public List<Reservation> findAllByHall(HallDTO hallDTO) {
        Hall hall = hallDao.findById(hallDTO.getId());
        return reservationDao.findAllByHall(hall);
    }

    public List<Reservation> findAllByTable(Table table) {
        return reservationDao.findAllByTable(table);
    }

    public List<Reservation> findAllByTableAndHall(Hall hall, Table table) {
        // todo: check if table exists in hall

        return reservationDao.findAllByTable(table);
    }

    public List<Reservation> findAllActiveByHall(HallDTO hallDTO) {
        Hall hall = hallDao.findById(hallDTO.getId());
        return reservationDao.findAllActiveByHall(hall);
    }

    public Reservation findActiveForTable(Table table) {
        return reservationDao.findActiveForTable(table);
    }

    public List<Reservation> findAllByTableId(Integer tid) {
        return reservationDao.findAllByTableId(tid);
    }
}
