package com.infoshareacademy.niewiem.services.validators;

import com.infoshareacademy.niewiem.pojo.Hall;
import com.infoshareacademy.niewiem.services.HallSaveService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HallValidator {

    private static final Logger LOG = LoggerFactory.getLogger(HallSaveService.class);

    public boolean isIdNotNull(Hall hall){
        LOG.debug("");
        return hall.getId() != null;
    }

    public boolean isNameNotNullOrEmpty(Hall hall) {
        LOG.debug("");
        return hall.getName() != null
                && !hall.getName().isEmpty();
    }
}
