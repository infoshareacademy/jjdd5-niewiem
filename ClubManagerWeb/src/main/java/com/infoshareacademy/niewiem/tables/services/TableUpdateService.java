package com.infoshareacademy.niewiem.tables.services;

import com.infoshareacademy.niewiem.domain.Table;
import com.infoshareacademy.niewiem.halls.dto.HallDTO;
import com.infoshareacademy.niewiem.tables.dao.TableDao;
import com.infoshareacademy.niewiem.tables.validators.TableValidator;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class TableUpdateService {
    private static final Logger LOG = LoggerFactory.getLogger(TableUpdateService.class);

    @Inject
    private TableDao tableDao;

    @Inject
    private TableValidator tableValidator;

    public void updateName(String tidParam, String name, List<String> errors, HallDTO activeHall) {
        if(!validateName(name, errors)){
            return;
        }
        if(tableValidator.validateTidParam(tidParam, errors, activeHall)){
            int tid = Integer.parseInt(tidParam);
            Table table = tableDao.findById(tid);
            table.setName(name);
            tableDao.update(table);
        }
    }

    private boolean validateName(String name, List<String> errors){
        if(StringUtils.isEmpty(name)){
            errors.add("Invalid request. Name cannot be empty.");
            LOG.warn("Invalid request. Name cannot be empty.");
            return false;
        }
        return true;
    }
}
