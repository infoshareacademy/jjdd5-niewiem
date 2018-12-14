package com.infoshareacademy.niewiem.services;

import com.infoshareacademy.niewiem.dao.HallDao;
import com.infoshareacademy.niewiem.pojo.Hall;
import com.infoshareacademy.niewiem.services.validators.HallValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class HallSaveService {
    private static final Logger LOG = LoggerFactory.getLogger(HallSaveService.class);

    @Inject
    private HallDao hallDao;

    @Inject
    private HallValidator hallValidator;

    public Integer save(Hall hall){

        if(hallValidator.isIdNotNull(hall)){
            LOG.warn("Hall didn't save because id is null");
            return -1;
        }

        if(hallValidator.isNameNotNullOrEmpty(hall)){
            LOG.warn("Hall didn't save because name is null or empty");
            return -1;
        }
        // todo: validate me like you validate your French girls!
        // if image null or empty- put in the default image
        // check if image actually exist on disk
        return hallDao.save(hall);
    }
}