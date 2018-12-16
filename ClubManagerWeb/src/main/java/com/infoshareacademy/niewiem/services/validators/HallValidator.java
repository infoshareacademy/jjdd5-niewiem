package com.infoshareacademy.niewiem.services.validators;

import com.infoshareacademy.niewiem.pojo.Hall;
import com.infoshareacademy.niewiem.services.HallSaveService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HallValidator {

    public boolean isHallNotNull(Hall hall){
        return hall.getId() != null;
    }

    public boolean isIdNotNull(Integer id){
        return id != null;
    }

    public boolean isNameNotNullOrEmpty(Hall hall) {
        return hall.getName() != null
                && !hall.getName().isEmpty();
    }

}
