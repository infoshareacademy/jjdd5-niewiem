package com.infoshareacademy.niewiem.services;

import com.infoshareacademy.niewiem.dao.TableDao;
import com.infoshareacademy.niewiem.services.validators.TableValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class TableDeleteService {
    private static final Logger LOG = LoggerFactory.getLogger(TableDeleteService.class);

    @Inject
    private TableDao tableDao;

    @Inject
    private TableValidator tableValidator;

    public void delete(Integer id) {

        if(tableValidator.isIdNotNull(id)){
            tableDao.delete(id);
        }
        // todo: validate me like you validate your French girls!
        // Should deleting table cancel all future reservations?
        // Maybe it should send an email to admin with reservations and customer contact to inform about cancelation.
        LOG.warn("Table didn't delete because id is null");
        throw new IllegalArgumentException("Table with id '" + id + "' doesn't exist");
    }
}
