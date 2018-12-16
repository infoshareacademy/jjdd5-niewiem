package com.infoshareacademy.niewiem.services.validators;

import com.infoshareacademy.niewiem.pojo.Table;

public class TableValidator {

    public boolean isTableIdNotNull(Table table){
        return table.getId() != null;
    }

    public boolean isIdNotNull(Integer id){
        return id != null;
    }

    public boolean isNameNotNullOrEmpty(Table table) {
        return table.getName() != null
                && !table.getName().isEmpty();
    }

    public boolean isTypeNotNull(Table table) {
        return table.getType() != null;
    }

    public boolean isHallIdNotNull(Table table) {
        return table.getHall().getId() != null;
    }
}
