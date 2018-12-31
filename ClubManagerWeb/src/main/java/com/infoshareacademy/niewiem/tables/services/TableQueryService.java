package com.infoshareacademy.niewiem.tables.services;

import com.infoshareacademy.niewiem.domain.Table;
import com.infoshareacademy.niewiem.halls.dto.HallDTO;
import com.infoshareacademy.niewiem.reservations.dto.ReservationInMillisDTO;
import com.infoshareacademy.niewiem.reservations.services.ReservationQueryService;
import com.infoshareacademy.niewiem.tables.dao.TableDao;
import com.infoshareacademy.niewiem.tables.dto.TableDTO;
import com.infoshareacademy.niewiem.tables.mappers.TableDTOMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class TableQueryService {
    private static final Logger LOG = LoggerFactory.getLogger(TableQueryService.class);

    @Inject
    private TableDao tableDao;

    @Inject
    private ReservationQueryService reservationQueryService;

    @Inject
    private TableDTOMapper tableDTOMapper;

    public Table findById(Integer id) {
        return tableDao.findById(id);
    }

    public List<Table> findAll() {
        return tableDao.findAll();
    }

    public List<TableDTO> findAllByHall(HallDTO hallDTO) {
        List<Table> tables = tableDao.findAllByHallId(hallDTO.getId());
        return tables.stream()
                .map(t -> tableDTOMapper.convertTblToDTO(t))
                .collect(Collectors.toList());
    }

    public boolean doesExist(Integer tid) {
        boolean doesExist = tableDao.doesExist(tid);
        LOG.info("Checking for existence of the table ID. Result: {} for table ID: {}.", doesExist, tid);
        return doesExist;
    }


    public List<ReservationInMillisDTO> findAllTablesInHallWithEndTimeInMillis(HallDTO hallDTO) {
        List<TableDTO> tables = findAllByHall(hallDTO);
        List<ReservationInMillisDTO> reservations = reservationQueryService.findAllActiveByHall(hallDTO);

        return tables.stream()
                .map(t ->
                        reservations.stream()
                                .filter(r -> r.getTable().getId() == t.getId())
                                .findFirst()
                                .orElse(new ReservationInMillisDTO(t, 0L, "Temp for view"))
                )
                .collect(Collectors.toList());
    }

    public boolean isActive(Table table) {
        return reservationQueryService.findActiveForTable(table) != null;
    }
}