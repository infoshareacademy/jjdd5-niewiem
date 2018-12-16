package com.infoshareacademy.niewiem.services;

import com.infoshareacademy.niewiem.dao.TableDao;
import com.infoshareacademy.niewiem.pojo.Table;
import com.infoshareacademy.niewiem.services.validators.HallValidator;
import com.infoshareacademy.niewiem.services.validators.TableValidator;
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
}
