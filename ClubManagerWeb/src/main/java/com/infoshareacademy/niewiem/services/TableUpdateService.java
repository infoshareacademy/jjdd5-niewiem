package com.infoshareacademy.niewiem.services;

import com.infoshareacademy.niewiem.dao.TableDao;
import com.infoshareacademy.niewiem.pojo.Table;
import com.infoshareacademy.niewiem.services.validators.TableValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class TableUpdateService {
    private static final Logger LOG = LoggerFactory.getLogger(TableUpdateService.class);

    @Inject
    private TableDao tableDao;

    @Inject
    private TableValidator tableValidator;

    public Table update(Table table) {

        if (!tableValidator.isTableIdNotNull(table)) {
            throwException("Table didn't update because id is null");
        }

        if(!tableValidator.isNameNotNullOrEmpty(table)){
            throwException("Table didn't update because name is null or empty");
        }

        if(!tableValidator.isTypeNotNull(table)){
            throwException("Table didn't update because type is null");
        }
        // todo: validate me like you validate your French girls!
        // check if table with that id already exist, it should since it's an update
        // make sure only updated fields get changed! Maybe if null don't abort, just don't change?
        return tableDao.update(table);
    }

    private void throwException(String message) {
        LOG.warn(message);
        throw new IllegalArgumentException(message);
    }

}
