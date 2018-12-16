package com.infoshareacademy.niewiem.services;

import com.infoshareacademy.niewiem.dao.TableDao;
import com.infoshareacademy.niewiem.pojo.Table;
import com.infoshareacademy.niewiem.services.validators.TableValidator;
import com.infoshareacademy.niewiem.enums.TableType;
import com.infoshareacademy.niewiem.pojo.Hall;
import com.infoshareacademy.niewiem.services.validators.InputValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class TableSaveService {
    private static final Logger LOG = LoggerFactory.getLogger(TableSaveService.class);

    @Inject
    private TableDao tableDao;

    @Inject
    private TableValidator tableValidator;

    @Inject
    private HallQueryService hallQueryService;

    @Inject
    private InputValidator inputValidator;

    public Integer save(Table table) {

        if (tableValidator.isTableIdNotNull(table)) {
            LOG.warn("Table didn't save because id is not null");
            return -1;
        }

        if(tableValidator.isNameNotNullOrEmpty(table)){
            LOG.warn("Table didn't save because name is not null or empty");
            return -1;
        }

        if(tableValidator.isTypeNotNull(table)){
            LOG.warn("Table didn't save because type is not null");
            return -1;
        }

        if(!tableValidator.isHallIdNotNull(table)){
            LOG.warn("Table didn't save because hall id is null");
            return -1;
        }
        return tableDao.save(table);
    }

    public void addTablePoolToHallAutoName(Integer hallId, int i) {
        Table table = new Table();
        Hall hall = hallQueryService.findById(hallId);

        table.setHall(hall);
        table.setType(TableType.POOL);
        table.setName("P" + String.format("%02d", i));

        save(table);
    }

    public void addTableToHall(String hid, String name, String type) {
        Integer hallId = inputValidator.reqIntegerValidator(hid);
        TableType tableType = inputValidator.reqTableTypeValidator(type);

        Table table = new Table();
        // todo: validate if hall is the active one
        Hall hall = hallQueryService.findById(hallId);

        table.setHall(hall);
        table.setType(tableType);
        table.setName(name);

        save(table);
    }

}
