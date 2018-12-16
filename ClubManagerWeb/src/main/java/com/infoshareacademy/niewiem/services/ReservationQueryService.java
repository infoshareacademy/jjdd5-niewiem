package com.infoshareacademy.niewiem.services;

import com.infoshareacademy.niewiem.dao.ReservationDao;
import com.infoshareacademy.niewiem.pojo.Hall;
import com.infoshareacademy.niewiem.pojo.Reservation;
import com.infoshareacademy.niewiem.pojo.Table;
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

    public Reservation findById(Long id) {
        return reservationDao.findById(id);
    }

    public List<Reservation> findAll() {
        return reservationDao.findAll();
    }

    public List<Reservation> finaAllByHall(Hall hall) {
        return reservationDao.findAllByHall(hall);
    }

    public List<Reservation> findAllByTable(Table table) {
        return reservationDao.findAllByTable(table);
    }

    public List<Reservation> findAllByTableAndHall(Hall hall, Table table) {
        // todo: check if table exists in hall

        return reservationDao.findAllByTable(table);
    }

    public List<Reservation> findAllActiveByHall(Hall hall) {
        return reservationDao.findAllActiveByHall(hall);
    }

    public Reservation findActiveForTable(Table table) {
        return reservationDao.findActiveForTable(table);
    }
}
