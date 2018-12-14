package com.infoshareacademy.niewiem.services;

import com.infoshareacademy.niewiem.dao.HallDao;
import com.infoshareacademy.niewiem.pojo.Hall;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class HallSaveService {
    private static final Logger LOG = LoggerFactory.getLogger(HallSaveService.class);

    @Inject
    private HallDao hallDao;

    public Integer save(Hall hall){
        // todo: validate me like you validate your French girls!
        // id should be null, otherwise it's not save but update!
        // name should not be null or empty
        // if image null or empty- put in the default image
        // check if image actually exist on disk
        return hallDao.save(hall);
    }
}