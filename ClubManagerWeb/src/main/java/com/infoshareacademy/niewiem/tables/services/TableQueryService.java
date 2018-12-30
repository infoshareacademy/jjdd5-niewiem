package com.infoshareacademy.niewiem.tables.services;

import com.infoshareacademy.niewiem.domain.Hall;
import com.infoshareacademy.niewiem.domain.Reservation;
import com.infoshareacademy.niewiem.domain.Table;
import com.infoshareacademy.niewiem.halls.dao.HallDao;
import com.infoshareacademy.niewiem.halls.dto.HallDTO;
import com.infoshareacademy.niewiem.reservations.services.ReservationQueryService;
import com.infoshareacademy.niewiem.tables.dao.TableDao;
import com.infoshareacademy.niewiem.tables.dto.TableEndTimeInMillis;
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
    private HallDao hallDao;

    @Inject
    private ReservationQueryService reservationQueryService;

    public Table findById(Integer id) {
        return tableDao.findById(id);
    }

    public List<Table> findAll() {
        return tableDao.findAll();
    }

    public List<Table> findAllInHall(HallDTO hallDTO) {
        Hall hall = hallDao.findById(hallDTO.getId());
        return tableDao.findAllInHall(hall);

    }

    public boolean doesExist(Integer tid) {
        boolean doesExist = tableDao.doesExist(tid);
        LOG.info("Checking for existence of the table ID. Result: {} for table ID: {}.", doesExist, tid);
        return doesExist;
    }


    public List<TableEndTimeInMillis> findAllTablesInHallWithEndTimeInMillis(HallDTO hallDTO) {
        List<Table> tables = findAllInHall(hallDTO);
        List<Reservation> reservations = reservationQueryService.findAllActiveByHall(hallDTO);

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
