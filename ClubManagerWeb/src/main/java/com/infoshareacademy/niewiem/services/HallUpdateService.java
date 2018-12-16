package com.infoshareacademy.niewiem.services;

import com.infoshareacademy.niewiem.dao.HallDao;
import com.infoshareacademy.niewiem.pojo.Hall;
import com.infoshareacademy.niewiem.services.validators.HallValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class HallUpdateService {
    private static final Logger LOG = LoggerFactory.getLogger(HallSaveService.class);

    @Inject
    private HallDao hallDao;

    @Inject
    private HallValidator hallValidator;

    public Hall update(Hall hall) {

        if(!hallValidator.isHallNotNull(hall)){
            LOG.warn("Hall can't be update because id is null");
        }

        if (!hallValidator.isNameNotNullOrEmpty(hall)) {
            LOG.warn("Hall can't be update because name is null or empty");
        }
        // todo: validate me like you validate your French girls!
        // if image null or empty- put in the default image
        // check if image actually exist on disk
        // make sure only updated fields get changed! Maybe if null don't abort, just don't change?
        return hallDao.update(hall);
    }
}
