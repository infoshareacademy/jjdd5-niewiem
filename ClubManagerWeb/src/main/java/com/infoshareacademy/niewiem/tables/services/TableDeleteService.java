package com.infoshareacademy.niewiem.tables.services;

import com.infoshareacademy.niewiem.halls.dto.HallDTO;
import com.infoshareacademy.niewiem.tables.dao.TableDao;
import com.infoshareacademy.niewiem.tables.validators.TableValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class TableDeleteService {
    private static final Logger LOG = LoggerFactory.getLogger(TableDeleteService.class);

    @Inject
    private TableDao tableDao;

    @Inject
    private TableValidator tableValidator;

    public void delete(String tidString, List<String> errors, HallDTO hallDTO) {
        // todo: Does deleting table also delete all table's reservation?
        // todo: When reservation is being deleted, maybe it should send an email to customer?
        if(tableValidator.validateTidParam(tidString, errors, hallDTO)){
            Integer tid = Integer.parseInt(tidString);

            LOG.info("Got table id of: " + tid + "sending to delete.");

            tableDao.delete(tid);
        }
    }
}
