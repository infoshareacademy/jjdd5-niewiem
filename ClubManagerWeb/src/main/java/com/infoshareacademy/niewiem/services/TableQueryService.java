package com.infoshareacademy.niewiem.services;

import com.infoshareacademy.niewiem.dao.ReservationDao;
import com.infoshareacademy.niewiem.dao.TableDao;
import com.infoshareacademy.niewiem.dto.TableEndTimeInMillis;
import com.infoshareacademy.niewiem.pojo.Hall;
import com.infoshareacademy.niewiem.pojo.Reservation;
import com.infoshareacademy.niewiem.pojo.Table;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class TableQueryService {
    private static final Logger LOG = LoggerFactory.getLogger(HallSaveService.class);

    @Inject
    private TableDao tableDao;

    @Inject
    private ReservationDao reservationDao;

    public Table findById(Integer id) {
        return tableDao.findById(id);
    }

    public List<Table> findAll() {
        return tableDao.findAll();
    }

    public List<Table> findAllInHall(Hall hall) {
        return tableDao.findAllInHall(hall);

    }

    public List<TableEndTimeInMillis> findAllTablesInHallWithEndTimeInMillis(Hall hall) {
        List<Table> tables = findAllInHall(hall);
        List<Reservation> reservations = reservationDao.findAllActiveByHall(hall);

        return tables.stream()
                .map(t -> {
                    TableEndTimeInMillis tetim = new TableEndTimeInMillis();
                    tetim.setTable(t);
                    Long endTimeInMillis = reservations.stream()
                            .filter(r -> r.getTable() == t)
                            .map(r -> r.getEndTime().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli())
                            .findFirst()
                            .orElse(0L);
                    tetim.setEndTimeInMillis(endTimeInMillis);
                    return tetim;
                })
                .collect(Collectors.toList());
    }

    // todo: isActive
    // todo: findActiveReservation - maybe should be in ReservationQueryService?
}
