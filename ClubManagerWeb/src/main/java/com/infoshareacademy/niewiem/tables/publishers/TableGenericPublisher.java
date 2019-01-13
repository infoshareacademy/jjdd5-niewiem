package com.infoshareacademy.niewiem.tables.publishers;

import com.infoshareacademy.niewiem.enums.TableType;

import java.util.EnumSet;
import java.util.Map;

public abstract class TableGenericPublisher {

    public void publishTableTypes(Map<String, Object> model){
        EnumSet<TableType> types = EnumSet.allOf(TableType.class);
        model.put("types", types);
    }
}
