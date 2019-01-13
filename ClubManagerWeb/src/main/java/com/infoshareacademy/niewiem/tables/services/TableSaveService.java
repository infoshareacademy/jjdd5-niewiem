package com.infoshareacademy.niewiem.tables.services;

import com.infoshareacademy.niewiem.domain.Hall;
import com.infoshareacademy.niewiem.domain.Table;
import com.infoshareacademy.niewiem.enums.TableType;
import com.infoshareacademy.niewiem.halls.dao.HallDao;
import com.infoshareacademy.niewiem.halls.dto.HallDTO;
import com.infoshareacademy.niewiem.halls.services.HallQueryService;
import com.infoshareacademy.niewiem.services.validators.InputValidator;
import com.infoshareacademy.niewiem.tables.dao.TableDao;
import com.infoshareacademy.niewiem.tables.mappers.TableRequestMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Stateless
public class TableSaveService {
    private static final Logger LOG = LoggerFactory.getLogger(TableSaveService.class);

    @Inject
    private TableDao tableDao;

    @Inject
    private HallQueryService hallQueryService;

    @Inject
    private HallDao hallDao;

    @Inject
    private TableRequestMapper tableRequestMapper;

    @Inject
    private InputValidator inputValidator;

    public Integer save(Table table) {
        return tableDao.save(table);
    }

    public void createNewTable(HttpServletRequest req, List<String> errors, HallDTO activeHall) {
        Table table = tableRequestMapper.getTableWithoutId(req, errors, activeHall);
        if(table == null){
            return;
        }
        tableDao.save(table);
    }

    public void addTablePoolToHallAutoNameNoValidation(Integer hallId, int i) {
        Table table = new Table();
        Hall hall = hallQueryService.findById(hallId);

        table.setHall(hall);
        table.setType(TableType.POOL);
        table.setName("P" + String.format("%02d", i));

        save(table);
    }

    public void addTableToHall(HallDTO hallDTO, String name, String type) {
        TableType tableType = inputValidator.reqTableTypeValidator(type);
        Hall hall = hallDao.findById(hallDTO.getId());

        Table table = new Table();

        table.setHall(hall);
        table.setType(tableType);
        table.setName(name);

        save(table);
    }
}
