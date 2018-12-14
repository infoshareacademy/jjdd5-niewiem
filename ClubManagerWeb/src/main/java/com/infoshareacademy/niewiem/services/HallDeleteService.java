package com.infoshareacademy.niewiem.services;

import com.infoshareacademy.niewiem.dao.HallDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class HallDeleteService {
    private static final Logger LOG = LoggerFactory.getLogger(HallDeleteService.class);

    @Inject
    private HallDao hallDao;

    public void deleteById(Integer id) {
        // todo: validate me like you validate your French girls!
        hallDao.delete(id);
    }
}
