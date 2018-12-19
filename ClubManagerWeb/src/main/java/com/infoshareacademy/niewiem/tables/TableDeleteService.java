package com.infoshareacademy.niewiem.tables;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class TableDeleteService {
    private static final Logger LOG = LoggerFactory.getLogger(TableDeleteService.class);

    @Inject
    private TableDao tableDao;

    public void delete(Integer id) {
        // todo: validate me like you validate your French girls!
        // Should deleting table cancel all future reservations?
        // Maybe it should send an email to admin with reservations and customer contact to inform about cancelation.
        tableDao.delete(id);
    }
}
