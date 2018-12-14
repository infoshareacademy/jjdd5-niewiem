package com.infoshareacademy.niewiem.services.validators;

import com.infoshareacademy.niewiem.enums.TableType;

import javax.ejb.Stateless;

@Stateless
public class InputValidator {
    public Integer reqIntegerValidator(String integer){
        // todo: validate me!
        return Integer.valueOf(integer);
    }

    public TableType reqTableTypeValidator(String type) {
        // todo: validate me!
        return TableType.valueOf(type);
    }
}
