package com.infoshareacademy.niewiem.services;

import com.infoshareacademy.niewiem.dao.HallDao;
import com.infoshareacademy.niewiem.pojo.Hall;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class HallUpdateService {
    private static final Logger LOG = LoggerFactory.getLogger(HallSaveService.class);

    @Inject
    private HallDao hallDao;

    public Hall update(Hall hall) {
        // todo: validate me like you validate your French girls!
        // id should NOT be null not empty!
        // check if hall with that id already exist, it should since it's an update
        // name should not be null or empty
        // if image null or empty- put in the default image
        // check if image actually exist on disk
        // make sure only updated fields get changed! Maybe if null don't abort, just don't change?
        return hallDao.update(hall);
    }
}
