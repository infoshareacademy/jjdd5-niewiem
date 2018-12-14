package com.infoshareacademy.niewiem.services;

import com.infoshareacademy.niewiem.dao.TableDao;
import com.infoshareacademy.niewiem.pojo.Table;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class TableUpdateService {
    private static final Logger LOG = LoggerFactory.getLogger(TableUpdateService.class);

    @Inject
    private TableDao tableDao;

    public Table update(Table table) {
        // todo: validate me like you validate your French girls!
        // id should NOT be null not empty!
        // check if table with that id already exist, it should since it's an update
        // name should not be null or empty
        // make sure only updated fields get changed! Maybe if null don't abort, just don't change?
        return tableDao.update(table);
    }


}
