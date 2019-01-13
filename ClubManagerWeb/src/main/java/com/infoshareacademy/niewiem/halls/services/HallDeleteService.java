package com.infoshareacademy.niewiem.halls.services;

import com.infoshareacademy.niewiem.halls.dao.HallDao;
import com.infoshareacademy.niewiem.halls.validators.HallValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class HallDeleteService {
    private static final Logger LOG = LoggerFactory.getLogger(HallDeleteService.class);

    @Inject
    private HallDao hallDao;

    @Inject
    private HallValidator hallValidator;

    public void delete(String hidParam, List<String> errors) {
        // todo: Should deleting hall also delete all hall's tables?
        if(hallValidator.validateHidParam(hidParam, errors)){
            Integer hid = Integer.parseInt(hidParam);

            LOG.info("Got hall id of: {} sending to delete.", hid);

            hallDao.delete(hid);
        }
    }
}