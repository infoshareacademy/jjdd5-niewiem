package com.infoshareacademy.niewiem.halls.services;

import com.infoshareacademy.niewiem.halls.dao.HallDao;
import com.infoshareacademy.niewiem.domain.Hall;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class HallQueryService {
    private static final Logger LOG = LoggerFactory.getLogger(HallQueryService.class);

    @Inject
    private HallDao hallDao;

    public Hall findById(Integer id) {
        return hallDao.findById(id);
    }

    public List<Hall> findAll() {
        return hallDao.findAll();
    }

    public boolean doesExist(Hall hall) {
        boolean doesExist = hallDao.contains(hall);
        LOG.info("Checking for existence of the hall. Result: {} for hall: {}.", doesExist, hall);
        return doesExist;
    }

    public boolean doesNotExist(Hall hall) {
        return !doesExist(hall);
    }
}
