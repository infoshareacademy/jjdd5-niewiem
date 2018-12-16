package com.infoshareacademy.niewiem.services;

import com.infoshareacademy.niewiem.dao.HallDao;
import com.infoshareacademy.niewiem.pojo.Hall;
import com.infoshareacademy.niewiem.services.validators.HallValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class HallQueryService {
    private static final Logger LOG = LoggerFactory.getLogger(HallSaveService.class);

    @Inject
    private HallDao hallDao;

    @Inject
    private HallValidator hallValidator;

    public Hall findById(Integer id) {

        if(hallValidator.isIdNotNull(id)){
            return hallDao.findById(id);
        }
        LOG.warn("Hall id didn't find because is null");
        throw new IllegalArgumentException("Hall with id '" + id + "' doesn't exist");
    }

    public List<Hall> findAll() {

        return hallDao.findAll();
    }
}
