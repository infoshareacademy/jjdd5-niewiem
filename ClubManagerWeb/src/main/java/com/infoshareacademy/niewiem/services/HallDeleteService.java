package com.infoshareacademy.niewiem.services;

import com.infoshareacademy.niewiem.dao.HallDao;
import com.infoshareacademy.niewiem.services.validators.HallValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class HallDeleteService {
    private static final Logger LOG = LoggerFactory.getLogger(HallDeleteService.class);

    @Inject
    private HallDao hallDao;

    @Inject
    private HallValidator hallValidator;

    public void deleteById(Integer id) {

        if(hallValidator.isIdNotNull(id)){
            hallDao.delete(id);
        }
        LOG.warn("Hall didn't delete because id is null");
        throw new IllegalArgumentException("Hall with id '" + id + "' doesn't exist");
    }
}
