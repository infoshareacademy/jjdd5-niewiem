package com.infoshareacademy.niewiem.tables.services;

import com.infoshareacademy.niewiem.domain.Hall;
import com.infoshareacademy.niewiem.domain.Reservation;
import com.infoshareacademy.niewiem.domain.Table;
import com.infoshareacademy.niewiem.reservations.services.ReservationQueryService;
import com.infoshareacademy.niewiem.tables.dto.TableEndTimeInMillis;
import com.infoshareacademy.niewiem.tables.dao.TableDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class TableQueryService {
    private static final Logger LOG = LoggerFactory.getLogger(TableQueryService.class);

    @Inject
    private TableDao tableDao;

    @Inject
    private ReservationQueryService reservationQueryService;

    public Table findById(Integer id) {
        return tableDao.findById(id);
    }

    public List<Table> findAll() {
        return tableDao.findAll();
    }

    public List<Table> findAllInHall(Hall hall) {
        return tableDao.findAllInHall(hall);

    }

    public boolean doesExist(Table table) {
        boolean doesExist = tableDao.contains(table);
        LOG.info("Checking for existence of the table. Result: {} for table: {}.", doesExist, table);
        return doesExist;
    }

    public boolean doesExist(Integer tid) {
        boolean doesExist = tableDao.contains(tid);
        LOG.info("Checking for existence of the table ID. Result: {} for table ID: {}.", doesExist, tid);
        return doesExist;
    }

    public boolean doesNotExist(Table table) {
        return !doesExist(table);
    }

    public boolean doesNotExist(Integer tid) {
        return !doesExist(tid);
    }



    public List<TableEndTimeInMillis> findAllTablesInHallWithEndTimeInMillis(Hall hall) {
        List<Table> tables = findAllInHall(hall);
        List<Reservation> reservations = reservationQueryService.findAllActiveByHall(hall);

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

    public boolean isActive(Table table) {
        return reservationQueryService.findActiveForTable(table) != null;
    }
}
