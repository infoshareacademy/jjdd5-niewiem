package com.infoshareacademy.niewiem.services;

import com.infoshareacademy.niewiem.dao.TableDao;
import com.infoshareacademy.niewiem.enums.TableType;
import com.infoshareacademy.niewiem.pojo.Hall;
import com.infoshareacademy.niewiem.pojo.Table;
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
    private HallQueryService hallQueryService;

    public Integer save(Table table) {
        // todo: validate me like you validate your French girls!
        // id should be null, otherwise it's not save but update!
        // name should not be null or empty
        // type should not be null nor empty
        // enum type should exist
        // hall_id should not be null nor empty
        // hall_id should actually exist
        return tableDao.save(table);
    }

    public void addTableToHall(Integer hallId, int i) {
        Table table = new Table();
        Hall hall = hallQueryService.findById(hallId);

        table.setHall(hall);
        table.setType(TableType.POOL);
        table.setName("P" + String.format("%02d", i));

        save(table);
    }
}
